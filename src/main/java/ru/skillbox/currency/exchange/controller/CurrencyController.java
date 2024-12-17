package ru.skillbox.currency.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.repository.projection.CurrencyProjectionForAllCurrencyRequest;
import ru.skillbox.currency.exchange.service.CurrencyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/currency")
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping
    public List<CurrencyProjectionForAllCurrencyRequest> findAllCurrencies() {
        return service.findAllCurrencies();
    }

    @GetMapping(value = "/{id}")
    public CurrencyDto getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @GetMapping(value = "/convert")
    public Double convertValue(@RequestParam("value") Long value, @RequestParam("numCode") Long numCode) {
        return service.convertValue(value, numCode);
    }

    @PostMapping("/create")
    public CurrencyDto create(@RequestBody CurrencyDto dto) {
        return service.create(dto);
    }
}
