package com.eleks.academy.pharmagator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DataProvidersConfig {

    @Value("${pharmagator.data-providers.apteka-ants.url}")
    private String pharmacyANTSBaseUrl;

    @Value("${pharmagator.data-providers.apteka-ds.url}")
    private String pharmacyDSBaseUrl;

    @Bean(name = "pharmacyANTSWebClient")
    public WebClient pharmacyANTSWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(pharmacyANTSBaseUrl)
                .build();
    }

    @Bean(name = "pharmacyDSWebClient")
    public WebClient pharmacyDSWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(pharmacyDSBaseUrl)
                .build();
    }

}
