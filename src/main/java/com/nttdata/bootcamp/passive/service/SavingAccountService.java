package com.nttdata.bootcamp.passive.service;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.SavingAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface SavingAccountService {

    Flux<Passive> findAllSavingAccount();

    Flux<Passive> findAllSavingAccountByCustomer(String dni);

    Mono<Passive> findSavingAccountByAccountNumber(String accountNumber);

    Mono<Passive> saveSavingAccount(SavingAccountDto dataAccount, Boolean creditCard);

    Mono<Passive> updateSavingAccount(SavingAccountDto dataAccount);

    Mono<Passive> deleteSavingAccount(String accountNumber);

}
