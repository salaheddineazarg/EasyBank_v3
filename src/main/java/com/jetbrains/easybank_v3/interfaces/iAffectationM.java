package com.jetbrains.easybank_v3.interfaces;



import com.jetbrains.easybank_v3.dto.AffectationM;

import java.util.Map;
import java.util.Optional;

public interface iAffectationM {

    Optional<AffectationM> AddAffictation(AffectationM affictation);
    int DeleteAffictation(int id);

    Map<String,Object> HistoricAffictation();
    AffectationM StatisticAffictation();

    Optional<AffectationM> FindOne(String code);
}
