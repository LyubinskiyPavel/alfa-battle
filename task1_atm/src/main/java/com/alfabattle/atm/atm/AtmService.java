package com.alfabattle.atm.atm;

import com.alfabattle.atm.alfaclient.client.model.ATMDetails;

import java.util.List;

public interface AtmService {

    List<ATMDetails> findAll();

    ATMDetails findById(Integer deviceId);

    List<ATMDetails> getNearestAtmListOrdered(double latitude, double longitude);

    List<ATMDetails> getNearestAtmListOrdered(double latitude, double longitude, int alfik);
}
