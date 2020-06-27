package com.alfabattle.kafka.payment;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
public class PaymentRepositoryInMemoryImpl implements PaymentRepository {

    private ConcurrentMap<String, Payment> payments = new ConcurrentHashMap<>();

    @Override
    public void store(Payment payment) {
        payments.put(payment.getRef(), payment);
    }

    @Override
    public List<Payment> findByUser(String userId) {
        return payments.values().stream()
                .filter(payment -> userId.equals(payment.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }
}
