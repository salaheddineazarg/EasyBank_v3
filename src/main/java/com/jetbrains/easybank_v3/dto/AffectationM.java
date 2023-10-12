package com.jetbrains.easybank_v3.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AffectationM {


    private LocalDate Startdate;
    private  LocalDate EndDate;

    private  Employe Employe;
    private  Mission mission;
}
