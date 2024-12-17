package ru.skillbox.currency.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.repository.projection.CurrencyProjectionForAllCurrencyRequest;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query(value = "SELECT c.name AS name, c.value AS value FROM Currency AS c")
    List<CurrencyProjectionForAllCurrencyRequest> findAllCurrencies();

    Currency findByIsoNumCode(Long isoNumCode);
}
