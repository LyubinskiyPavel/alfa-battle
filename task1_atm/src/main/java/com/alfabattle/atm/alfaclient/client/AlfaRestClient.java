package com.alfabattle.atm.alfaclient.client;

import com.alfabattle.atm.alfaclient.client.model.JSONResponseBankATMDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class AlfaRestClient {

    private static final Gson GSON = new GsonBuilder().create();

    private static final String URL = "https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service";

    private static final String CLIENT_ID = "da31c68a-5e52-4af0-9d2f-f0b700ad1698";

    private final RestTemplate alfaRestTemplate;


    public AlfaRestClient(RestTemplate alfaRestTemplate) {
        this.alfaRestTemplate = alfaRestTemplate;
    }

    public JSONResponseBankATMDetails getAtmDetails() {
        HttpEntity httpEntity = new HttpEntity<>(prepareHeaders());

        ResponseEntity<String> response = alfaRestTemplate.exchange(URL + "/atms", HttpMethod.GET, httpEntity, String.class);

        return GSON.fromJson(response.getBody(), JSONResponseBankATMDetails.class);
    }

    private HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("x-ibm-client-id", CLIENT_ID);
        return headers;
    }
}
