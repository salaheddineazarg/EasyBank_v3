package com.jetbrains.easybank_v3.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class Employe extends  Person{

    private String matricule;
    private LocalDate dateRecrutement;
    private  String email;

    private List<AffectationA> affectationAList;
    private  List<AffectationM> affectationMList;
}
