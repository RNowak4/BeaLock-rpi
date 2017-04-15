package com.wpam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class BealockRpiApplication {
    private final static String KEYSTORE_LOCATION = "/home/pi/keys/client.jks";
    private final static String KEYSTORE_PASSWORD = "s3cr3t";

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

    public static void main(String[] args) {
        SpringApplication.run(BealockRpiApplication.class, args);
    }
}
