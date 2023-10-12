package com.jetbrains.easybank_v3.interfaces;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface iAccount<Type> {
    Optional<Type> AddAccount(Type account);
    int DeleteAccount(String id );
    Optional<Type> UpdateAccount(Type account);

    Optional<Type> ShowByDateCreation(LocalDate DateCreation);
    Map<String,Object> ShowByStatus(String status);
    Map<String,Object> GetAll();

    boolean ChangeEtat(Type accountSaving);

    Optional<Type> SearchByClient(String code);

    Optional<Type> FindById(String id);

}
