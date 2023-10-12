package com.jetbrains.easybank_v3.interfaces;



import com.jetbrains.easybank_v3.dto.AffectationA;

import java.util.Optional;

public interface iAffectationA {

    Optional<AffectationA> Add(AffectationA affectationA);
    int Delete(AffectationA affectationA);
}
