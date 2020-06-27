package com.alfabattle.atm.alfaclient.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URL;

@Configuration
public class ClientConfiguration {

    @Bean
    public RestTemplate alfaRestTemplate() throws Exception {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(alfaHttpClient());

        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    public HttpClient alfaHttpClient() throws Exception {
        return HttpClientBuilder.create()
                .setSSLContext(sslContext())
                .build();
    }

    private SSLContext sslContext() throws Exception {
        final URL cacertURL = getClass().getClassLoader().getResource("cacerts");
        return SSLContextBuilder.create()
                .loadKeyMaterial(cacertURL, "changeIt".toCharArray(), "".toCharArray())
                .build();
    }
}
