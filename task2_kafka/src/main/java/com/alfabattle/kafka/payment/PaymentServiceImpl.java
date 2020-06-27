package com.alfabattle.kafka.payment;

import com.alfabattle.kafka.rest.client.model.PaymentCategoryInfo;
import com.alfabattle.kafka.rest.client.model.UserPaymentAnalytic;
import com.alfabattle.kafka.rest.client.model.UserPaymentStats;
import com.alfabattle.kafka.rest.client.model.UserTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void store(Payment payment) {
        paymentRepository.store(payment);
    }

    @Override
    public List<UserPaymentAnalytic> getAllAnalytic() {
        final Map<String, List<Payment>> paymentsByUserId = paymentRepository.findAll().stream().collect(Collectors.groupingBy(Payment::getUserId));
        return paymentsByUserId.entrySet().stream()
                .map(entry -> buildUserPaymentAnalytic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public UserPaymentAnalytic getUserAnalytic(String userId) {
        final List<Payment> userPaymentList = paymentRepository.findByUser(userId);
        return !userPaymentList.isEmpty() ? buildUserPaymentAnalytic(userId, userPaymentList) : null;
    }

    private UserPaymentAnalytic buildUserPaymentAnalytic(String userId, List<Payment> payments) {
        final Map<Integer, List<Payment>> userPaymentsByCategoryId = payments.stream().collect(Collectors.groupingBy(Payment::getCategoryId));

        Map<String, PaymentCategoryInfo> categoryAnalytics = new HashMap<>();
        userPaymentsByCategoryId.forEach((categoryId, categoryPayments) -> {
            PaymentCategoryInfo paymentCategoryInfo = new PaymentCategoryInfo();
            paymentCategoryInfo.setMin(categoryPayments.stream().map(Payment::getAmount).min(BigDecimal::compareTo).get());
            paymentCategoryInfo.setMax(categoryPayments.stream().map(Payment::getAmount).max(BigDecimal::compareTo).get());
            paymentCategoryInfo.setSum(categoryPayments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));

            categoryAnalytics.put(categoryId.toString(), paymentCategoryInfo);
        });

        UserPaymentAnalytic analytic = new UserPaymentAnalytic();
        analytic.setUserId(userId);
        analytic.setAnalyticInfo(categoryAnalytics);
        analytic.setTotalSum(categoryAnalytics.values().stream().map(PaymentCategoryInfo::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        return analytic;
    }

    @Override
    public UserPaymentStats getPaymentStatsByUser(String userId) {
        final List<Payment> userPayments = paymentRepository.findByUser(userId);
        if (userPayments.isEmpty()) {
            return null;
        }

        final Function<List<Payment>, BigDecimal> summator = payments -> payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        final Map<Integer, List<Payment>> paymentsByCategory = userPayments.stream().collect(Collectors.groupingBy(Payment::getCategoryId));

        final TreeMap<BigDecimal, Integer> categoryByAmount = paymentsByCategory.entrySet().stream().collect(Collectors.toMap(
                entry -> summator.apply(entry.getValue()),
                entry -> entry.getKey(),
                (left, right) -> left,
                () -> new TreeMap<BigDecimal, Integer>(BigDecimal::compareTo)));

        final TreeMap<Integer, Integer> categoryByUsageCount = paymentsByCategory.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getValue().size(),
                entry -> entry.getKey(),
                (left, right) -> left,
                () -> new TreeMap<Integer, Integer>(Integer::compareTo)));

        return new UserPaymentStats()
                .minAmountCategoryId(categoryByAmount.firstEntry().getValue())
                .maxAmountCategoryId(categoryByAmount.lastEntry().getValue())
                .rareCategoryId(categoryByUsageCount.firstEntry().getValue())
                .oftenCategoryId(categoryByUsageCount.lastEntry().getValue());
    }

    @Override
    public List<UserTemplate> getUserTemplates(String userId) {
        final List<Payment> userPayments = paymentRepository.findByUser(userId);
        if (userPayments.isEmpty()) {
            return null;
        }
        final Map<PaymentTemplateKey, List<Payment>> paymentsByTemplate = userPayments.stream().collect(Collectors.groupingBy(PaymentTemplateKey::new));
        return paymentsByTemplate.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 3)
                .map(entry -> entry.getKey())
                .map(template -> new UserTemplate()
                        .categoryId(template.getCategoryId())
                        .recipientId(template.getRecipientId())
                        .amount(template.getAmount()))
                .collect(Collectors.toList());
    }
}
