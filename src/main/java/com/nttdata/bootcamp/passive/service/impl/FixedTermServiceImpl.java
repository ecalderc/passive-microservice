package com.nttdata.bootcamp.passive.service.impl;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.FixedTermDto;
import com.nttdata.bootcamp.passive.repository.PassiveRepository;
import com.nttdata.bootcamp.passive.service.FixedTermService;
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
public class FixedTermServiceImpl implements FixedTermService {
    @Autowired
    private PassiveRepository passiveRepository;
    @Autowired
    private PassiveService passiveService;

    @Override
    public Flux<Passive> findAllFixedTerm() {
        log.info("Searching for all the fixed terms");
        return passiveRepository.findAll()
                .filter(Passive::getFixedTerm);
    }

    @Override
    public Flux<Passive> findFixedTermByCustomer(String dni) {
        log.info("Searching for fixed term with DNI: " + dni);
        return passiveRepository.findAll()
                .filter(x -> x.getFixedTerm() && x.getDni().equals(dni));
    }

    @Override
    public Mono<Passive> findFixedTermByAccountNumber(String accountNumber) {
        log.info("Searching for fixed term with account number: " + accountNumber);
        return passiveRepository.findAll()
                .filter(x -> x.getFixedTerm() && x.getAccountNumber().equals(accountNumber))
                .next();
    }

    @Override
    public Mono<Passive> saveFixedTerm(FixedTermDto dataAccount) {

        Passive f = new Passive();
        f.setDni(dataAccount.getDni());
        f.setTypeCustomer(dataAccount.getTypeCustomer());
        f.setAccountNumber(dataAccount.getAccountNumber());
        f.setFixedTerm(true);

        return passiveService.searchByFixedTermCustomer(f)
                .hasElement()
                .flatMap(savingAccountExists -> {
                    if (savingAccountExists) {
                        log.info("There is already a fixed term");
                        return Mono.empty();
                    } else {
                        log.info("Saving for fixed term with DNI: " + dataAccount.getDni());

                        //f.setDni(dataAccount.getDni());
                        //f.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
                        f.setFlagVip(false);
                        f.setFlagPyme(false);
                        //f.setAccountNumber(dataAccount.getAccountNumber());
                        f.setSaving(false);
                        f.setCurrentAccount(false);
                        f.setFixedTerm(true);
                        f.setFreeCommission(true);
                        f.setCommissionMaintenance(0);
                        f.setMovementsMonthly(true);
                        f.setLimitMovementsMonthly(1);
                        f.setCommissionTransaction(0.00);
                        f.setStatus(Constant.PASSIVE_ACTIVE);
                        f.setCreationDate(new Date());
                        f.setModificationDate(new Date());

                        return passiveRepository.save(f);
                    }
                });
    }

    @Override
    public Mono<Passive> updateFixedTerm(FixedTermDto dataAccount) {
        return findFixedTermByAccountNumber(dataAccount.getAccountNumber())
                .flatMap(updAcc -> {
                    log.info("update fixed term");

                    updAcc.setModificationDate(new Date());
                    return passiveRepository.save(updAcc);
                });
    }

    @Override
    public Mono<Passive> deleteFixedTerm(String accountNumber) {
        log.info("Deleting fixed term by account number: " + accountNumber);
        return findFixedTermByAccountNumber(accountNumber)
                .flatMap(passive -> passiveRepository.delete(passive).then(Mono.just(passive)));
    }

}
