package com.jetbrains.easybank_v3.interfaces;


import com.jetbrains.easybank_v3.dto.Agency;

import java.util.Map;
import java.util.Optional;

public interface iAgency {
    Optional<Agency> Add(Agency agency);
    int Delete(String code);
    Optional<Agency> Update(Agency agency);
    Optional<Agency> SearchByCode(String code);

    Optional<Agency> SearchByAdresse(String adresse);

    Map<String,Object> ShowContact();
}
