package com.alfabattle.kafka.rest.contoller;

import com.alfabattle.kafka.payment.PaymentService;
import com.alfabattle.kafka.rest.client.model.UserPaymentAnalytic;
import com.alfabattle.kafka.rest.client.model.UserPaymentStats;
import com.alfabattle.kafka.rest.client.model.UserTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getUserAnalytic(@PathVariable("userId") String userId) {
        UserPaymentAnalytic userAnalytic = paymentService.getUserAnalytic(userId);
        if (userAnalytic == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedStatus(FailedStatus.USER_NOT_FOUND));
        }
        return ResponseEntity.ok(userAnalytic);
    }

    @GetMapping(path = "/analytic/{userId}/stats")
    public ResponseEntity<?> getPaymentStatsByUser(@PathVariable("userId") String userId) {
        UserPaymentStats userPaymentStats = paymentService.getPaymentStatsByUser(userId);
        if (userPaymentStats == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedStatus(FailedStatus.USER_NOT_FOUND));
        }
        return ResponseEntity.ok(userPaymentStats);
    }

    @GetMapping(path = "/analytic/{userId}/templates")
    public ResponseEntity<?> getUserTemplates(@PathVariable("userId") String userId) {
        List<UserTemplate> userTemplates = paymentService.getUserTemplates(userId);
        if (userTemplates == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedStatus(FailedStatus.USER_NOT_FOUND));
        }

        return ResponseEntity.ok(userTemplates);
    }

}
