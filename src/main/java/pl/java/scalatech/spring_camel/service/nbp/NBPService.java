package pl.java.scalatech.spring_camel.service.nbp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.java.scalatech.spring_camel.nbp.Pozycja;
import pl.java.scalatech.spring_camel.nbp.Tabela_kursow;

public interface NBPService {

    void showTabelaKursow(Tabela_kursow tk);

    void setToDateExchangeRates(Tabela_kursow tk);

    List<Pozycja> getAll();

    List<String> findCodeByName(String name);

    List<String> findAllCode();

    Page<Pozycja> findAllItem(Pageable pageable);

    Pozycja findCurrencyByCode(String code);

}
