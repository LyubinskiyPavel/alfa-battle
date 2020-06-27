package com.alfabattle.kafka.payment;

import com.alfabattle.kafka.rest.client.model.UserPaymentAnalytic;
import com.alfabattle.kafka.rest.client.model.UserPaymentStats;
import com.alfabattle.kafka.rest.client.model.UserTemplate;

import java.util.List;

public interface PaymentService {

    void store(Payment payment);

    List<UserPaymentAnalytic> getAllAnalytic();

    UserPaymentAnalytic getUserAnalytic(String userId);

    UserPaymentStats getPaymentStatsByUser(String userId);

    List<UserTemplate> getUserTemplates(String userId);
}
