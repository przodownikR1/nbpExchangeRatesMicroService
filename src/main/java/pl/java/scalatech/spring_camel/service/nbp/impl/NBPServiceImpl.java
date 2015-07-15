package pl.java.scalatech.spring_camel.service.nbp.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import pl.java.scalatech.spring_camel.exception.Exceptions;
import pl.java.scalatech.spring_camel.nbp.Pozycja;
import pl.java.scalatech.spring_camel.nbp.Tabela_kursow;
import pl.java.scalatech.spring_camel.service.nbp.NBPService;

@Component
@Slf4j
public class NBPServiceImpl implements NBPService {

    private Tabela_kursow toDayExchangeRate;

    @Override
    public void showTabelaKursow(Tabela_kursow tk) {
        log.info("{}", tk);
    }

    @Override
    public void setToDateExchangeRates(Tabela_kursow tk) {
        this.toDayExchangeRate = tk;

    }

    @Override
    public Page<Pozycja> findAllItem(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pozycja findCurrencyByCode(String code) {
        Optional<Pozycja> result = toDayExchangeRate.getPozycje().stream().filter(pozycja -> code.equals(pozycja.getKod_waluty())).findFirst();
        return result.orElseThrow(Exceptions::itemNotFound);
    }

    @Override
    public List<Pozycja> getAll() {
        return toDayExchangeRate.getPozycje();
    }

    @Override
    public List<String> findCodeByName(String name) {
        return toDayExchangeRate.getPozycje().stream().map(t -> t.getKod_waluty()).sorted().filter(s -> s.startsWith(name)).collect(Collectors.toList());

    }

    @Override
    public List<String> findAllCode() {
        return toDayExchangeRate.getPozycje().stream().map(t -> t.getKod_waluty()).sorted().collect(Collectors.toList());
    }

}
