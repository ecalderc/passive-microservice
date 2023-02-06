package com.nttdata.bootcamp.passive.service.impl;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.SavingAccountDto;
import com.nttdata.bootcamp.passive.repository.PassiveRepository;
import com.nttdata.bootcamp.passive.service.PassiveService;
import com.nttdata.bootcamp.passive.service.SavingAccountService;
import com.nttdata.bootcamp.passive.util.Constant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class SavingAccountServiceImpl implements SavingAccountService {
    @Autowired
    private PassiveRepository passiveRepository;
    @Autowired
    private PassiveService passiveService;

    @Override
    public Flux<Passive> findAllSavingAccount() {
        log.info("Searching for all saving accounts");
        return passiveRepository.findAll()
                .filter(Passive::getSaving);
    }

    @Override
    public Flux<Passive> findAllSavingAccountByCustomer(String dni) {
        log.info("Searching for saving account with DNI: " + dni);
        return passiveRepository.findAll()
                .filter(x -> x.getSaving() && x.getDni().equals(dni));
    }

    @Override
    public Mono<Passive> findSavingAccountByAccountNumber(String accountNumber) {
        log.info("Searching for saving account with account number: " + accountNumber);
        return passiveRepository.findAll()
                .filter(x -> x.getSaving() && x.getAccountNumber().equals(accountNumber))
                .next();
    }

    @Override
    public Mono<Passive> saveSavingAccount(SavingAccountDto dataAccount, Boolean creditCard) {

        Passive s = new Passive();
        s.setDni(dataAccount.getDni());
        s.setTypeCustomer(dataAccount.getTypeCustomer());
        s.setAccountNumber(dataAccount.getAccountNumber());
        s.setSaving(true);

        if (creditCard) {
            s.setDailyAverage(true);
            s.setFlagVip(true);
        } else {
            s.setDailyAverage(false);
            s.setFlagVip(false);
        }

        return passiveService.searchBySavingCustomer(s)
                .hasElement()
                .flatMap(savingAccountExists -> {
                    if (savingAccountExists) {
                        log.info("There is already a saving account");
                        return Mono.empty();
                    } else {
                        log.info("Saving for saving account with DNI: " + dataAccount.getDni());

                        //s.setDni(dataAccount.getDni());
                        //s.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
                        //s.setFlagVip(false);
                        s.setFlagPyme(false);
                        //s.setAccountNumber(dataAccount.getAccountNumber());
                        //s.setSaving(true);
                        s.setCurrentAccount(false);
                        s.setFixedTerm(false);
                        s.setFreeCommission(true);
                        s.setCommissionMaintenance(0);
                        s.setMovementsMonthly(true);
                        s.setLimitMovementsMonthly(dataAccount.getLimitMovementsMonthly());
                        s.setCommissionTransaction(dataAccount.getCommissionTransaction());
                        s.setStatus(Constant.PASSIVE_ACTIVE);
                        s.setCreationDate(new Date());
                        s.setModificationDate(new Date());

                        return passiveRepository.save(s);
                    }
                });
    }

    @Override
    public Mono<Passive> updateSavingAccount(SavingAccountDto dataAccount) {
        return findSavingAccountByAccountNumber(dataAccount.getAccountNumber())
                .flatMap(updAcc -> {
                    log.info("Updating limit movements monthly '{}' to '{}'", updAcc.getLimitMovementsMonthly(), dataAccount.getLimitMovementsMonthly());

                    updAcc.setLimitMovementsMonthly(dataAccount.getLimitMovementsMonthly());
                    updAcc.setModificationDate(new Date());
                    return passiveRepository.save(updAcc);
                });
    }

    @Override
    public Mono<Passive> deleteSavingAccount(String accountNumber) {
        log.info("Deleting saving account by account number: " + accountNumber);
        return findSavingAccountByAccountNumber(accountNumber)
                .flatMap(passive -> passiveRepository.delete(passive).then(Mono.just(passive)));
    }

}
