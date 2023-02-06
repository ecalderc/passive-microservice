package com.nttdata.bootcamp.passive.controller;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.CurrentAccountDto;
import com.nttdata.bootcamp.passive.service.CurrentAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/currentAccount")
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService currentAccountService;

    //All currents account products registered
    @GetMapping("/findAllAccounts")
    public Flux<Passive> findAllCurrentAccounts() {
        return currentAccountService.findAllCurrentAccounts();
    }

    //Search for current account by customer
    @GetMapping("/findByCustomer/{dni}")
    public Mono<Passive> findCurrentAccountByCustomer(@PathVariable("dni") String dni) {
        return currentAccountService.findCurrentAccountByCustomer(dni);
    }

    //Search for current account by account number
    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<Passive> findCurrentAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return currentAccountService.findCurrentAccountByAccountNumber(accountNumber);
    }

    //Save personal current account
    @PostMapping(value = "/savePersonalAccount")
    public Mono<Passive> savePersonalCurrentAccount(@Valid @RequestBody CurrentAccountDto account) {
        return currentAccountService.savePersonalCurrentAccount(account);
    }

    //Save business current account
    @PostMapping(value = "/saveBusinessAccount/{flagCreditCard}")
    public Mono<Passive> saveBusinessCurrentAccount(@Valid @RequestBody CurrentAccountDto account, @PathVariable("flagCreditCard") Boolean flagCreditCard) {
        return currentAccountService.saveBusinessCurrentAccount(account, flagCreditCard);
    }

    //Update current account
    @PutMapping("/updateAccount")
    public Mono<Passive> updateCurrentAccount(@RequestBody CurrentAccountDto account) {
        return currentAccountService.updateCurrentAccount(account);
    }

    //Delete current account
    @DeleteMapping("/delete/{accountNumber}")
    public Mono<Passive> deleteCurrentAccount(@PathVariable("accountNumber") String accountNumber) {
        return currentAccountService.deleteCurrentAccount(accountNumber);
    }

}
