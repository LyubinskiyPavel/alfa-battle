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

import java.util.Objects;

/**
 * UserPaymentStats
 */
public class UserPaymentStats {

    private Integer maxAmountCategoryId = null;

    private Integer minAmountCategoryId = null;

    private Integer oftenCategoryId = null;

    private Integer rareCategoryId = null;

    public UserPaymentStats maxAmountCategoryId(Integer maxAmountCategoryId) {
        this.maxAmountCategoryId = maxAmountCategoryId;
        return this;
    }

    /**
     * Get maxAmountCategoryId
     *
     * @return maxAmountCategoryId
     **/
    public Integer getMaxAmountCategoryId() {
        return maxAmountCategoryId;
    }

    public void setMaxAmountCategoryId(Integer maxAmountCategoryId) {
        this.maxAmountCategoryId = maxAmountCategoryId;
    }

    public UserPaymentStats minAmountCategoryId(Integer minAmountCategoryId) {
        this.minAmountCategoryId = minAmountCategoryId;
        return this;
    }

    /**
     * Get minAmountCategoryId
     *
     * @return minAmountCategoryId
     **/
    public Integer getMinAmountCategoryId() {
        return minAmountCategoryId;
    }

    public void setMinAmountCategoryId(Integer minAmountCategoryId) {
        this.minAmountCategoryId = minAmountCategoryId;
    }

    public UserPaymentStats oftenCategoryId(Integer oftenCategoryId) {
        this.oftenCategoryId = oftenCategoryId;
        return this;
    }

    /**
     * Get oftenCategoryId
     *
     * @return oftenCategoryId
     **/
    public Integer getOftenCategoryId() {
        return oftenCategoryId;
    }

    public void setOftenCategoryId(Integer oftenCategoryId) {
        this.oftenCategoryId = oftenCategoryId;
    }

    public UserPaymentStats rareCategoryId(Integer rareCategoryId) {
        this.rareCategoryId = rareCategoryId;
        return this;
    }

    /**
     * Get rareCategoryId
     *
     * @return rareCategoryId
     **/
    public Integer getRareCategoryId() {
        return rareCategoryId;
    }

    public void setRareCategoryId(Integer rareCategoryId) {
        this.rareCategoryId = rareCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPaymentStats userPaymentStats = (UserPaymentStats) o;
        return Objects.equals(this.maxAmountCategoryId, userPaymentStats.maxAmountCategoryId) &&
                Objects.equals(this.minAmountCategoryId, userPaymentStats.minAmountCategoryId) &&
                Objects.equals(this.oftenCategoryId, userPaymentStats.oftenCategoryId) &&
                Objects.equals(this.rareCategoryId, userPaymentStats.rareCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxAmountCategoryId, minAmountCategoryId, oftenCategoryId, rareCategoryId);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserPaymentStats {\n");

        sb.append("    maxAmountCategoryId: ").append(toIndentedString(maxAmountCategoryId)).append("\n");
        sb.append("    minAmountCategoryId: ").append(toIndentedString(minAmountCategoryId)).append("\n");
        sb.append("    oftenCategoryId: ").append(toIndentedString(oftenCategoryId)).append("\n");
        sb.append("    rareCategoryId: ").append(toIndentedString(rareCategoryId)).append("\n");
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

