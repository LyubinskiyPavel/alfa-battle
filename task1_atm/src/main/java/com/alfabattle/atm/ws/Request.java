package com.alfabattle.atm.ws;

public class Request {

    private final Integer deviceId;

    public Request(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }
}
