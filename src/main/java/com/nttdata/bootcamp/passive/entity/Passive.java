package com.nttdata.bootcamp.passive.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "passive")
public class Passive {

    @Id
    private String id;

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String typeCustomer;

    @NotNull
    private Boolean flagVip;

    @NotNull
    private Boolean flagPyme;


    @NotBlank
    @Size(min = 8, max = 8)
    private String accountNumber;


    @NotNull
    private Boolean saving;

    @NotNull
    private Boolean currentAccount;

    @NotNull
    private Boolean fixedTerm;


    @NotNull
    private Boolean freeCommission;

    @NotNull
    @DecimalMin(value = "0.00")
    private Number commissionMaintenance;

    @NotNull
    private Boolean movementsMonthly;

    @NotNull
    @DecimalMin(value = "0.00")
    private Number limitMovementsMonthly;

    @NotNull
    @DecimalMin(value = "0.00")
    private Number commissionTransaction;


    @NotBlank
    private String status;


    @NotNull
    private Boolean dailyAverage;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date modificationDate;

}
