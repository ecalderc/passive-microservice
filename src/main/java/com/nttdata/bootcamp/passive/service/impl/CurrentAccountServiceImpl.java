package com.nttdata.bootcamp.passive.service.impl;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.CurrentAccountDto;
import com.nttdata.bootcamp.passive.repository.PassiveRepository;
import com.nttdata.bootcamp.passive.service.CurrentAccountService;
import com.nttdata.bootcamp.passive.service.PassiveService;
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
public class CurrentAccountServiceImpl implements CurrentAccountService {
    @Autowired
    private PassiveRepository passiveRepository;
    @Autowired
    private PassiveService passiveService;

    @Override
    public Flux<Passive> findAllCurrentAccounts() {
        log.info("Searching for all current accounts");
        return passiveRepository.findAll()
                .filter(Passive::getCurrentAccount);
    }

    @Override
    public Mono<Passive> findCurrentAccountByCustomer(String dni) {
        log.info("Searching for current account with DNI: " + dni);
        return passiveRepository.findAll()
                .filter(x -> x.getCurrentAccount() && x.getDni().equals(dni))
                .next();
    }

    @Override
    public Mono<Passive> findCurrentAccountByAccountNumber(String accountNumber) {
        log.info("Searching for current account with account number: " + accountNumber);
        return passiveRepository.findAll()
                .filter(x -> x.getCurrentAccount() && x.getAccountNumber().equals(accountNumber))
                .next();
    }

    @Override
    public Mono<Passive> savePersonalCurrentAccount(CurrentAccountDto dataAccount) {

        Passive p = new Passive();
        p.setDni(dataAccount.getDni());
        p.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
        p.setAccountNumber(dataAccount.getAccountNumber());
        p.setCurrentAccount(true);

        return passiveService.searchByCurrentCustomer(p)
                .hasElement()
                .flatMap(currentAccountExists -> {
                    if (currentAccountExists) {
                        log.info("There is already a current account | Personal");
                        return Mono.empty();
                    } else {
                        log.info("Saving for personal current account with DNI: " + dataAccount.getDni());

                        //p.setDni(dataAccount.getDni());
                        //p.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
                        p.setFlagVip(false);
                        p.setFlagPyme(false);
                        //p.setAccountNumber(dataAccount.getAccountNumber());
                        p.setSaving(false);
                        //p.setCurrentAccount(true);
                        p.setFixedTerm(false);
                        p.setFreeCommission(false);
                        p.setCommissionMaintenance(dataAccount.getCommissionMaintenance());
                        p.setMovementsMonthly(false);
                        p.setLimitMovementsMonthly(0);
                        p.setCommissionTransaction(dataAccount.getCommissionTransaction());
                        p.setStatus(Constant.PASSIVE_ACTIVE);
                        p.setCreationDate(new Date());
                        p.setModificationDate(new Date());

                        return passiveRepository.save(p);
                    }
                });
    }

    @Override
    public Mono<Passive> saveBusinessCurrentAccount(CurrentAccountDto dataAccount, Boolean creditCard) {

        Passive b = new Passive();
        b.setDni(dataAccount.getDni());
        b.setTypeCustomer(Constant.BUSINESS_CUSTOMER);
        b.setAccountNumber(dataAccount.getAccountNumber());
        b.setCurrentAccount(true);

        if (creditCard) {
            b.setFreeCommission(true);
            b.setCommissionMaintenance(0);
            b.setFlagPyme(true);
        } else {
            b.setFreeCommission(false);
            b.setCommissionMaintenance(1);
            b.setFlagPyme(false);
        }

        return passiveService.searchByCurrentCustomer(b)
                .hasElement()
                .flatMap(currentAccountExists -> {
                    if (currentAccountExists) {
                        log.info("There is already a current account | Business");
                        return Mono.empty();
                    } else {
                        log.info("Saving for business current account with DNI: " + dataAccount.getDni());

                        //p.setDni(dataAccount.getDni());
                        //p.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
                        b.setFlagVip(false);
                        //b.setFlagPyme(false);
                        //b.setAccountNumber(dataAccount.getAccountNumber());
                        b.setSaving(false);
                        //p.setCurrentAccount(true);
                        b.setFixedTerm(false);
                        //b.setFreeCommission(false);
                        //b.setCommissionMaintenance(dataAccount.getCommissionMaintenance());
                        b.setMovementsMonthly(false);
                        b.setLimitMovementsMonthly(0);
                        b.setCommissionTransaction(dataAccount.getCommissionTransaction());
                        b.setStatus(Constant.PASSIVE_ACTIVE);
                        b.setCreationDate(new Date());
                        b.setModificationDate(new Date());

                        return passiveRepository.save(b);
                    }
                });
    }

    @Override
    public Mono<Passive> updateCurrentAccount(CurrentAccountDto dataAccount) {
        return findCurrentAccountByAccountNumber(dataAccount.getAccountNumber())
                .flatMap(updAcc -> {
                    log.info("Updating commission maintenance '{}' to '{}'", updAcc.getCommissionMaintenance(), dataAccount.getCommissionMaintenance());

                    updAcc.setCommissionMaintenance(dataAccount.getCommissionMaintenance());
                    updAcc.setModificationDate(new Date());
                    return passiveRepository.save(updAcc);
                });
    }

    @Override
    public Mono<Passive> deleteCurrentAccount(String accountNumber) {
        log.info("Deleting current account by account number: " + accountNumber);
        return findCurrentAccountByAccountNumber(accountNumber)
                .flatMap(passive -> passiveRepository.delete(passive).then(Mono.just(passive)));
    }

}
