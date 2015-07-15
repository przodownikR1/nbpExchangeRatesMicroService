package pl.java.scalatech.spring_camel.service.nbp.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import pl.java.scalatech.spring_camel.nbp.Tabela_kursow;
import pl.java.scalatech.spring_camel.service.nbp.NBPService;

@Component
@Slf4j
public class NBPServiceImpl implements NBPService {

    @Override
    public void showTabelaKursow(Tabela_kursow tk) {
        log.info("{}", tk);

    }

}
