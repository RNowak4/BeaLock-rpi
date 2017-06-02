package com.wpam.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MainServerService {

    @Value("${main.server.port}")
    private String mainServerPort;

    public void sendStolenAlarm(final String beaconId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        final String URL = "https://localhost:" + mainServerPort + "/notify";
        map.add("beaconId", beaconId);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        new RestTemplate().postForEntity(URL, request, Void.class);
    }
}
