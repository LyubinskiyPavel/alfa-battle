package com.alfabattle.kafka.payment;

import java.util.List;

public interface PaymentRepository {

    void store(Payment payment);

    List<Payment> findByUser(String userId);

    List<Payment> findAll();
}
