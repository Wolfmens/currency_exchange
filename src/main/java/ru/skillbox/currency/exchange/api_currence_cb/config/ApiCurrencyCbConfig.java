package ru.skillbox.currency.exchange.api_currence_cb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class ApiCurrencyCbConfig {

    @Value(value = "${app.api.baseUrl.v1}")
    private String v1BaseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(v1BaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(5)) // Таймаут ожидания ответа
                ))
                .build();
    }
}
