package com.nttdata.bootcamp.passive.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixedTermDto {

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String typeCustomer;

    @NotBlank
    private String accountNumber;

}
