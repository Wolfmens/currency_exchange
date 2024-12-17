package ru.skillbox.currency.exchange.v1_api_currence_cb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ApiCurrencyCbService {

    private final WebClient webClient;

    public String findAllActualCurrenciesFromCb() {

        return webClient
                .get()
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
