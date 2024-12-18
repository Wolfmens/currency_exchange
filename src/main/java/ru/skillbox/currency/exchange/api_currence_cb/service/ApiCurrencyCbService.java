package ru.skillbox.currency.exchange.api_currence_cb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiCurrencyCbService {

    private final WebClient webClient;

    @Value(value = "${app.api.baseUrl.v2}")
    private String v2BaseUrl;

    public String findAllActualCurrenciesFromCb() {
        try {
          return requestAPi();

        } catch (WebClientRequestException ex) {
            log.error("Client request exception: {}", ex.getMessage());
            try {
                webClient.mutate().baseUrl(v2BaseUrl).build();
                return requestAPi();

            } catch (Exception e) {
                log.error("Client request by 2 URL exception: {}", e.getMessage());
                throw new RuntimeException("Client request by 2 URL exception: " + e.getMessage());
            }
        }
    }

    private String requestAPi() {
        log.info("Request API");
        return webClient
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .retry(3)
                .block();
    }
}
