package com.nttdata.bootcamp.passive.service;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.FixedTermDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface FixedTermService {

    Flux<Passive> findAllFixedTerm();

    Flux<Passive> findFixedTermByCustomer(String dni);

    Mono<Passive> findFixedTermByAccountNumber(String accountNumber);

    Mono<Passive> saveFixedTerm(FixedTermDto dataFixedTerm);

    Mono<Passive> updateFixedTerm(FixedTermDto dataFixedTerm);

    Mono<Passive> deleteFixedTerm(String accountNumber);

}
