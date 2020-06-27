package com.alfabattle.kafka.consumer;

import com.alfabattle.kafka.payment.Payment;
import com.alfabattle.kafka.payment.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.SeekUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ObjectMapper objectMapper;

    private final PaymentService paymentService;


    public KafkaConsumer(ObjectMapper objectMapper, PaymentService paymentService) {
        this.objectMapper = objectMapper;
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "RAW_PAYMENTS")
    public void consume(String recordRaw) {
        logger.debug(recordRaw);
        try {
            final Payment payment = objectMapper.readValue(recordRaw, Payment.class);
            paymentService.store(payment);
        } catch (JsonProcessingException e) {
            logger.warn("cant deserialize record {}", recordRaw, e);
        }

    }
}
