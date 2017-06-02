package com.wpam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableScheduling
public class BealockRpiApplication {
    private final static String KEYSTORE_LOCATION = "C:/Osobiste/keys/client.jks";
    private final static String KEYSTORE_PASSWORD = "s3cr3t";

    @Value("${main.server.ip}")
    private String mainServerIp;

    @Value("${main.server.port}")
    private String mainServerPort;

    @Value("${raspberry.ip}")
    private String raspberryIp;

    @Value("${server.port}")
    private String raspberryPort;

    static {
        System.setProperty("javax.net.ssl.trustStore", KEYSTORE_LOCATION);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
        System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("localhost"));
    }

    @Bean
    public RestTemplate template() throws Exception {
        return new RestTemplate();
    }

    @PostConstruct
    public void registerServer() throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        final String URL = "https://" + mainServerIp + ":" + mainServerPort + "/childServer";
        map.add("ip", raspberryIp);
        map.add("port", raspberryPort);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        template().postForEntity(URL, request, Void.class);
    }

    @PreDestroy
    public void deregisterServer() throws Exception {
        final String URL = "https://" + mainServerIp + ":" + mainServerPort + "/childServer/"
                + raspberryIp + "/" + raspberryPort;

        template().delete(URL);
    }

    public static void main(String[] args) {
        SpringApplication.run(BealockRpiApplication.class, args);
    }
}
