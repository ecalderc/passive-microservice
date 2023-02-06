package com.nttdata.bootcamp.passive;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Passive API", version = "1.0", description = "Documentation Passive API v1.0"))
public class PassiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassiveApplication.class, args);
    }

}
