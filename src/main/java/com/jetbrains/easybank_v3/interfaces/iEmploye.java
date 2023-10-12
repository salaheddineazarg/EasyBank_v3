package com.jetbrains.easybank_v3.interfaces;


import com.jetbrains.easybank_v3.dto.Employe;

import java.util.Map;
import java.util.Optional;

public interface iEmploye extends iPerson<Employe> {

    Optional<Employe> SearchByMatricule(String matricule);
    Map<String, Object> Search(String attributeName, String value);

    Map<String,Object> GetAll();

}
