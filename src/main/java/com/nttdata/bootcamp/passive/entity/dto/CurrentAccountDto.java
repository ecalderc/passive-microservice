package com.nttdata.bootcamp.passive.entity.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountDto {

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    @Size(min = 8, max = 8)
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.00")
    private Number commissionMaintenance;

    @NotNull
    @DecimalMin(value = "0.00")
    private Number commissionTransaction;

}
