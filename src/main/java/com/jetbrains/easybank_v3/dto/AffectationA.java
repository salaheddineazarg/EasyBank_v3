package com.jetbrains.easybank_v3.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AffectationA {
    private LocalDate CreationDate;
    private  Agency agency;
    private Employe employe;
}
