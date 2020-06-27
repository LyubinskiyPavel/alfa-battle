/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.alfabattle.kafka.rest.client.model;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentCategoryInfo {

    private BigDecimal max = null;

    private BigDecimal min = null;

    private BigDecimal sum = null;

    public PaymentCategoryInfo max(BigDecimal max) {
        this.max = max;
        return this;
    }

    /**
     * Get max
     *
     * @return max
     **/
    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public PaymentCategoryInfo min(BigDecimal min) {
        this.min = min;
        return this;
    }

    /**
     * Get min
     *
     * @return min
     **/
    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public PaymentCategoryInfo sum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    /**
     * Get sum
     *
     * @return sum
     **/
    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentCategoryInfo paymentCategoryInfo = (PaymentCategoryInfo) o;
        return Objects.equals(this.max, paymentCategoryInfo.max) &&
                Objects.equals(this.min, paymentCategoryInfo.min) &&
                Objects.equals(this.sum, paymentCategoryInfo.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(max, min, sum);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PaymentCategoryInfo {\n");

        sb.append("    max: ").append(toIndentedString(max)).append("\n");
        sb.append("    min: ").append(toIndentedString(min)).append("\n");
        sb.append("    sum: ").append(toIndentedString(sum)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
