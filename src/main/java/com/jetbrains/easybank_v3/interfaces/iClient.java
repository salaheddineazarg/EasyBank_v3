package com.jetbrains.easybank_v3.interfaces;


import com.jetbrains.easybank_v3.dto.Client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface iClient extends iPerson<Client> {

    Optional<Client> SearchByCode(String code);
    List<Client> GetAll();
    Map<String,Object> Search(String attributeName,String value);

}
