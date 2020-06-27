package com.alfabattle.kafka.payment;

import com.alfabattle.kafka.rest.client.model.PaymentCategoryInfo;
import com.alfabattle.kafka.rest.client.model.UserPaymentAnalytic;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private UserPaymentAnalytic buildUserPaymentAnalytic(String userId, List<Payment> payments) {
        final Map<String, List<Payment>> userPaymentsByCategoryId = payments.stream().collect(Collectors.groupingBy(Payment::getCategoryId));

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
}
