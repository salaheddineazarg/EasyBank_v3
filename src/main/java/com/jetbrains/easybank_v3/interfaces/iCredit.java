package com.jetbrains.easybank_v3.interfaces;



import com.jetbrains.easybank_v3.dto.Credit;
import com.jetbrains.easybank_v3.dto.CreditStatus;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface iCredit {
    Optional<Credit> Create(Credit credit);
    Optional<Credit> ShowByCode(String code);
    Map<String, Object> Show();
    Optional<Credit> ShowByClient(String code);
     Optional<Credit>  ShowByAgence(String code);
    Optional<Credit> ShowByStatut(CreditStatus creditStatus);
     Optional<Credit>   ShowByDate(LocalDate date);
    boolean ValidateSimulation();
    boolean ChangeStatut();
    
}
