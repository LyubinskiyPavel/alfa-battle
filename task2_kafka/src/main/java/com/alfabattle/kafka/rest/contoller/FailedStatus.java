package com.alfabattle.kafka.rest.contoller;

public class FailedStatus {

    public static final String USER_NOT_FOUND = "user not found";

    private final String status;

    public FailedStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
