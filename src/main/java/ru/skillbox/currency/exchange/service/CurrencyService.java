package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.currency.exchange.api_currence_cb.dto.ValutesApiCurrencyCb;
import ru.skillbox.currency.exchange.api_currence_cb.mapper.ApiCurrencyCbMapper;
import ru.skillbox.currency.exchange.api_currence_cb.service.ApiCurrencyCbService;
import ru.skillbox.currency.exchange.api_currence_cb.util.ParserXmlToObject;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
import ru.skillbox.currency.exchange.repository.projection.CurrencyProjectionForAllCurrencyRequest;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyMapper currencyMapper;
    private final ApiCurrencyCbMapper apiCurrencyCbMapper;
    private final CurrencyRepository currencyRepository;
    private final ApiCurrencyCbService apiCurrencyCbService;

    @Transactional(readOnly = true)
    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return currencyMapper.convertToDto(currency);
    }

    @Transactional(readOnly = true)
    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = currencyRepository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    @Transactional
    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return currencyMapper.convertToDto(currencyRepository.save(currencyMapper.convertToEntity(dto)));
    }

    @Transactional(readOnly = true)
    public List<CurrencyProjectionForAllCurrencyRequest> findAllCurrencies() {
        log.info("CurrencyService method findAllCurrencies executed");
        return currencyRepository.findAllCurrencies();
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void updateCurrencyFromCb() {
        log.info("CurrencyService method updateCurrencyFromCb executed");
        List<Currency> currenciesFromCb = getCurrenciesFromCb();

        for (Currency currencyFromCb : currenciesFromCb) {
            Optional.ofNullable(currencyRepository.findByIsoLetterCode(currencyFromCb.getIsoLetterCode()))
                    .ifPresentOrElse(
                            currencyFromBd -> updateCurrency(currencyFromBd, currencyFromCb),
                            () -> currencyRepository.save(Currency.from(currencyFromCb)));
        }
    }

    private void updateCurrency(Currency currencyFromBd, Currency currenciesFromCb) {
        currencyFromBd.setIsoNumCode(currenciesFromCb.getIsoNumCode());
        currencyFromBd.setName(currenciesFromCb.getName());
        currencyFromBd.setNominal(currenciesFromCb.getNominal());
        currencyFromBd.setValue(currenciesFromCb.getValue());
        currencyFromBd.setIsoLetterCode(currenciesFromCb.getIsoLetterCode());

        currencyRepository.save(currencyFromBd);
    }

    private List<Currency> getCurrenciesFromCb() {
        String allActualCurrenciesFromCbXml = apiCurrencyCbService.findAllActualCurrenciesFromCb();
        ParserXmlToObject<ValutesApiCurrencyCb> parserXmlToObject = new ParserXmlToObject<>(ValutesApiCurrencyCb.class);
        ValutesApiCurrencyCb valutesApiCurrencyCb = parserXmlToObject.parseXmlToObject(allActualCurrenciesFromCbXml);

        return apiCurrencyCbMapper.valutesApiCurrencyCbToCurrencyEntityList(valutesApiCurrencyCb);
    }
}
