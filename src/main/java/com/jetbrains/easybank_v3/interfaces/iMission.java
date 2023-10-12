package com.jetbrains.easybank_v3.interfaces;


import com.jetbrains.easybank_v3.dto.Mission;

import java.util.Map;
import java.util.Optional;

public interface iMission {

    Optional<Mission> AddMission(Mission mission);
    int DeleteMission(String code);

    Map<String,Object> GetAll();
    Optional<Mission> FindOne(String code);
}
