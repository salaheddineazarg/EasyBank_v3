package com.jetbrains.easybank_v3.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Credit {

    private String code;
    private Double balance;
    private  CreditStatus status;
    private LocalDate CreationDate;


}
