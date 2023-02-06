package com.nttdata.bootcamp.passive.controller;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.SavingAccountDto;
import com.nttdata.bootcamp.passive.service.SavingAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/savingAccount")
public class SavingAccountController {

    @Autowired
    private SavingAccountService savingAccountService;

    //All savings account products registered
    @GetMapping("/findAllAccounts")
    public Flux<Passive> findAllSavingAccounts() {
        return savingAccountService.findAllSavingAccount();
    }

    //Search for saving account by customer
    @GetMapping("/findByCustomer/{dni}")
    public Flux<Passive> findAllSavingAccountByCustomer(@PathVariable("dni") String dni) {
        return savingAccountService.findAllSavingAccountByCustomer(dni);
    }

    //Search for saving account by account number
    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<Passive> findSavingAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return savingAccountService.findSavingAccountByAccountNumber(accountNumber);
    }

    //Save saving account
    @PostMapping(value = "/saveAccount/{flagCreditCard}")
    public Mono<Passive> saveSavingAccount(@Valid @RequestBody SavingAccountDto account, @PathVariable("flagCreditCard") Boolean flagCreditCard) {
        return savingAccountService.saveSavingAccount(account, flagCreditCard);
    }

    //Update saving account
    @PutMapping("/updateAccount")
    public Mono<Passive> updateSavingAccount(@RequestBody SavingAccountDto account) {
        return savingAccountService.updateSavingAccount(account);
    }

    //Delete saving account
    @DeleteMapping("/delete/{accountNumber}")
    public Mono<Passive> deleteSavingAccount(@PathVariable("accountNumber") String accountNumber) {
        return savingAccountService.deleteSavingAccount(accountNumber);
    }

}
