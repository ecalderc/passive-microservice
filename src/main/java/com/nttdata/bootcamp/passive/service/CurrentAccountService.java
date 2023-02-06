package com.nttdata.bootcamp.passive.service;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.CurrentAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface CurrentAccountService {

    Flux<Passive> findAllCurrentAccounts();

    Mono<Passive> findCurrentAccountByCustomer(String dni);

    Mono<Passive> findCurrentAccountByAccountNumber(String accountNumber);

    Mono<Passive> savePersonalCurrentAccount(CurrentAccountDto dataAccount);

    Mono<Passive> saveBusinessCurrentAccount(CurrentAccountDto dataAccount, Boolean creditCard);

    Mono<Passive> updateCurrentAccount(CurrentAccountDto dataAccount);

    Mono<Passive> deleteCurrentAccount(String accountNumber);

}
