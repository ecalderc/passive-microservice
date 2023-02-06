package com.nttdata.bootcamp.passive.repository;

import com.nttdata.bootcamp.passive.entity.Passive;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

//Mongodb Repository
@Repository
public interface PassiveRepository extends ReactiveMongoRepository<Passive, String> {
}
