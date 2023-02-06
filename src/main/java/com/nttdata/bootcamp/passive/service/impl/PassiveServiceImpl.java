package com.nttdata.bootcamp.passive.service.impl;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.repository.PassiveRepository;
import com.nttdata.bootcamp.passive.service.PassiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PassiveServiceImpl implements PassiveService {

    @Autowired
    private PassiveRepository passiveRepository;

    public Mono<Passive> searchBySavingCustomer(Passive dataPersonalCustomer) {
        return passiveRepository.findAll()
                .filter(x -> (x.getAccountNumber().equals(dataPersonalCustomer.getAccountNumber()))
                        || (x.getDni().equals(dataPersonalCustomer.getDni())
                        && x.getTypeCustomer().equals(dataPersonalCustomer.getTypeCustomer())
                        && x.getSaving().equals(true))
                )
                .doOnNext(x -> log.info("A match was found: " + x))
                .next();
    }

    public Mono<Passive> searchByCurrentCustomer(Passive dataPersonalCustomer) {
        return passiveRepository.findAll()
                .filter(x -> (x.getAccountNumber().equals(dataPersonalCustomer.getAccountNumber()))
                        || (x.getDni().equals(dataPersonalCustomer.getDni())
                        && x.getTypeCustomer().equals(dataPersonalCustomer.getTypeCustomer())
                        && x.getCurrentAccount().equals(true))
                )
                .doOnNext(x -> log.info("A match was found: " + x))
                .next();
    }

    public Mono<Passive> searchByFixedTermCustomer(Passive dataPersonalCustomer) {
        return passiveRepository.findAll()
                .filter(x -> (x.getAccountNumber().equals(dataPersonalCustomer.getAccountNumber()))
                        || (x.getDni().equals(dataPersonalCustomer.getDni())
                        && x.getTypeCustomer().equals(dataPersonalCustomer.getTypeCustomer())
                        && x.getFixedTerm().equals(true))
                )
                .doOnNext(x -> log.info("A match was found: " + x))
                .next();
    }
}
