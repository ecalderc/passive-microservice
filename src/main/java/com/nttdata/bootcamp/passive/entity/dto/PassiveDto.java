package com.nttdata.bootcamp.passive.entity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassiveDto {

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String typeCustomer;

    @NotBlank
    @Size(min = 8, max = 8)
    private String accountNumber;

    @NotBlank
    private String typeAccount;

}
