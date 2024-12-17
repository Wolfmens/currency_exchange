package ru.skillbox.currency.exchange.v1_api_currence_cb.mapper;

import org.springframework.stereotype.Component;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.v1_api_currence_cb.dto.ValutesApiCurrencyCb;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiCurrencyCbMapper {

    public List<Currency> valutesApiCurrencyCbToCurrencyEntityList(ValutesApiCurrencyCb valutesApiCurrencyCb) {

        return valutesApiCurrencyCb.getValuteList().stream()
                .map(valuateFromCb -> {
                    Currency currency = new Currency();
                    currency.setName(valuateFromCb.getName());
                    currency.setValue(Double.parseDouble(valuateFromCb.getValue().replace(",", ".")));
                    currency.setNominal(Long.parseLong(valuateFromCb.getNominal()));
                    currency.setIsoLetterCode(valuateFromCb.getCharCode());
                    currency.setIsoNumCode(Long.parseLong(valuateFromCb.getNumCode()));

                    return currency;
                })
                .collect(Collectors.toList());
    }
}
