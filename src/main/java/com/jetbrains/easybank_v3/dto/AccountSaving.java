package com.jetbrains.easybank_v3.dto;
import lombok.Data;

@Data
public class AccountSaving extends Account{
    private  double interestRate;
}
