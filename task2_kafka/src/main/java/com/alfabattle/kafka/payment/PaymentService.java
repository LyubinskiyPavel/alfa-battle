package com.alfabattle.kafka.payment;

import com.alfabattle.kafka.rest.client.model.UserPaymentAnalytic;

import java.util.List;

public interface PaymentService {

    void store(Payment payment);

    List<UserPaymentAnalytic> getAllAnalytic();
}
