package com.jetbrains.easybank_v3.dto;

import lombok.Data;

import java.util.List;

@Data
public class Mission {
    private String code;
    private String name;
    private String description;
    private List<AffectationM> affectationList ;
}
