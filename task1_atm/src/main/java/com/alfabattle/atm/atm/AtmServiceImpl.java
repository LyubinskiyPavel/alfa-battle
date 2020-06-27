package com.alfabattle.atm.atm;

import com.alfabattle.atm.alfaclient.client.AlfaRestClient;
import com.alfabattle.atm.alfaclient.client.model.ATMDetails;
import com.alfabattle.atm.ws.Response;
import com.alfabattle.atm.ws.StompProxy;
import com.google.common.base.Suppliers;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Service
public class AtmServiceImpl implements AtmService {

    private final AlfaRestClient alfaRestClient;

    private final Supplier<List<ATMDetails>> atms;

    private final StompProxy stompProxy;

    public AtmServiceImpl(AlfaRestClient alfaRestClient, StompProxy stompProxy) {
        this.alfaRestClient = alfaRestClient;
        this.atms = Suppliers.memoize(() -> alfaRestClient.getAtmDetails().getData().getAtms());
        this.stompProxy = stompProxy;
    }

    @Override
    public List<ATMDetails> findAll() {
        return atms.get();
    }

    @Override
    public ATMDetails findById(Integer deviceId) {
        return atms.get().stream()
                .filter(atm -> atm.getDeviceId().equals(deviceId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ATMDetails> getNearestAtmListOrdered(double latitude, double longitude) {
        TreeMap<Double, ATMDetails> sortedMap = new TreeMap<>();
        atms.get().stream()
                .filter(atm -> atm.getCoordinates() != null
                        && !StringUtils.isEmpty(atm.getCoordinates().getLatitude())
                        && !StringUtils.isEmpty(atm.getCoordinates().getLongitude()))
                .forEach(atm -> {
                    double distance = Math.sqrt(
                            Math.pow(latitude - Double.parseDouble(atm.getCoordinates().getLatitude()), 2)
                                    + Math.pow(longitude - Double.parseDouble(atm.getCoordinates().getLongitude()), 2)
                    );
                    sortedMap.put(distance, atm);
                });
        return new ArrayList<>(sortedMap.values());
    }

    @Override
    public List<ATMDetails> getNearestAtmListOrdered(double latitude, double longitude, int alfik) {


        List<ATMDetails> atmList = getNearestAtmListOrdered(latitude, longitude);


        List<ATMDetails> result = new ArrayList<>();
        int enoughBalance = alfik;
        Map<Integer, Response> balanceCache = new HashMap<>();

        for (ATMDetails atmDetails : atmList) {
            final Response response = balanceCache.computeIfAbsent(atmDetails.getDeviceId(), stompProxy::executeRequest);
            if (response.getAlfik() != null && response.getAlfik() > 0) {
                enoughBalance = enoughBalance - response.getAlfik();
                result.add(atmDetails);

                if (enoughBalance <= 0) {
                    break;
                }
            }
        }

        return result;
    }
}
