package com.alfabattle.kafka.rest.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(path = "/admin/health")
    public Status health() {
        return new Status();
    }

    public static class Status {

        private final String status = "UP";

        public String getStatus() {
            return status;
        }
    }
}
