package com.nttdata.bootcamp.passive.controller;

import com.nttdata.bootcamp.passive.entity.Passive;
import com.nttdata.bootcamp.passive.entity.dto.FixedTermDto;
import com.nttdata.bootcamp.passive.service.FixedTermService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/fixedTerm")
public class FixedTermController {

    @Autowired
    private FixedTermService fixedTermService;

    //All savings account products registered
    @GetMapping("/findAllAccounts")
    public Flux<Passive> findAllFixedTerms() {
        return fixedTermService.findAllFixedTerm();
    }

    //Search for fixed term by customer
    @GetMapping("/findByCustomer/{dni}")
    public Flux<Passive> findFixedTermByCustomer(@PathVariable("dni") String dni) {
        return fixedTermService.findFixedTermByCustomer(dni);
    }

    //Search for fixed term by account number
    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<Passive> findFixedTermByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return fixedTermService.findFixedTermByAccountNumber(accountNumber);
    }

    //Save fixed term
    @PostMapping(value = "/saveAccount")
    public Mono<Passive> saveFixedTerm(@Valid @RequestBody FixedTermDto account) {
        return fixedTermService.saveFixedTerm(account);
    }

    //Update fixed term
    @PutMapping("/updateAccount")
    public Mono<Passive> updateFixedTerm(@RequestBody FixedTermDto account) {
        return fixedTermService.updateFixedTerm(account);
    }

    //Delete fixed term
    @DeleteMapping("/delete/{accountNumber}")
    public Mono<Passive> deleteFixedTerm(@PathVariable("accountNumber") String accountNumber) {
        return fixedTermService.deleteFixedTerm(accountNumber);
    }

}
