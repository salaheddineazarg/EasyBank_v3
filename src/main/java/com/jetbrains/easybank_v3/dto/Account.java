package com.jetbrains.easybank_v3.dto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public abstract class  Account {
    protected String nrAccount;
    protected double solde;
    protected LocalDate deteCreation;
    protected  Etat etat;
    protected  Employe employe;
    protected Client client;

}
