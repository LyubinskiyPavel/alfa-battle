package com.alfabattle.atm.controller;

public class FailedStatus {


    public static final String ATM_NOT_FOUND = "atm not found";

    private final String status;


    public FailedStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
