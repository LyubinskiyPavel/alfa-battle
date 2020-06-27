package com.alfabattle.kafka.payment;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentTemplateKey {

    private final Integer categoryId;

    private final String userId;

    private final String recipientId;

    private final BigDecimal amount;

    public PaymentTemplateKey(Payment source) {
        this.categoryId = source.getCategoryId();
        this.userId = source.getUserId();
        this.recipientId = source.getRecipientId();
        this.amount = source.getAmount();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTemplateKey that = (PaymentTemplateKey) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(recipientId, that.recipientId) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, userId, recipientId, amount);
    }
}
