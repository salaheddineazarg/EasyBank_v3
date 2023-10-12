package com.jetbrains.easybank_v3.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Client extends Person{
       @NonNull
     private  String code;
       @NonNull
    private String adresse;

    private List<Account> accountList;

    private List<Credit> creditList;


}
