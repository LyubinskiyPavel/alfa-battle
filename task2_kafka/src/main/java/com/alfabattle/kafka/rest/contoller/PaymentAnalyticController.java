package com.alfabattle.kafka.rest.contoller;

import com.alfabattle.kafka.payment.PaymentService;
import com.alfabattle.kafka.rest.client.model.UserPaymentAnalytic;
import com.alfabattle.kafka.rest.client.model.UserPaymentStats;
import com.alfabattle.kafka.rest.client.model.UserTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentAnalyticController {

    private final PaymentService paymentService;

    public PaymentAnalyticController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/analytic")
    public List<UserPaymentAnalytic> getAllAnalytic() {
        return paymentService.getAllAnalytic();
    }

    @GetMapping(path = "/analytic/{userId}")
    public UserPaymentAnalytic getUserAnalytic(@PathVariable("userId") String userId) {
        return null;
    }

    @GetMapping(path = "/analytic/{userId}/stats")
    public UserPaymentStats getPaymentStatsByUser(@PathVariable("userId") String userId) {
        return null;
    }

    @GetMapping(path = "/analytic/{userId}/templates")
    public UserTemplate getUserTemplates(@PathVariable("userId") String userId) {
        return null;
    }
}
