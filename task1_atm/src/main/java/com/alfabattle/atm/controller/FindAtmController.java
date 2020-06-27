package com.alfabattle.atm.controller;

import com.alfabattle.atm.alfaclient.client.model.ATMDetails;
import com.alfabattle.atm.atm.AtmService;
import com.alfabattle.atm.controller.model.AtmResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FindAtmController {

    private final AtmService atmService;

    public FindAtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @GetMapping(path = "/atms/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer deviceId) {
        final ATMDetails atm = atmService.findById(deviceId);
        if (atm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedStatus(FailedStatus.ATM_NOT_FOUND));
        }

        return ResponseEntity.ok(create(atm));
    }

    @GetMapping(path = "/atms/nearest")
    public AtmResponse getNearestAtm(@RequestParam("latitude") double latitude,
                                     @RequestParam("longitude") double longitude,
                                     @RequestParam(value = "payments", defaultValue = "false") boolean payments) {
        final List<ATMDetails> atms = atmService.getNearestAtmListOrdered(latitude, longitude);
        return atms.stream()
                .map(this::create)
                .filter(atm -> payments ? atm.isPayments() : true)
                .findFirst()
                .orElse(null);
    }

    @GetMapping(path = "/atms/nearest-with-alfik")
    public List<AtmResponse> getNearestAtm(@RequestParam("latitude") double latitude,
                                     @RequestParam("longitude") double longitude,
                                     @RequestParam("alfik") int alfik) {
        final List<ATMDetails> atms = atmService.getNearestAtmListOrdered(latitude, longitude, alfik);
        return atms.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    private AtmResponse create(ATMDetails source) {
        return new AtmResponse()
                .city(source.getAddress().getCity())
                .deviceId(source.getDeviceId())
                .latitude(source.getCoordinates().getLatitude())
                .location(source.getAddress().getLocation())
                .longitude(source.getCoordinates().getLongitude())
                .payments("Y".equals(source.getServices().getPayments()));
    }


}
