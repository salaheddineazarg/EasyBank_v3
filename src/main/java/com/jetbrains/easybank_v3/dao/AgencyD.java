package com.jetbrains.easybank_v3.dao;


import com.jetbrains.easybank_v3.connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.Agency;
import com.jetbrains.easybank_v3.interfaces.iAgency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Optional;

public class AgencyD  implements iAgency {
    Connection conn = ConnectionDB.getInstance().getConnection();
    @Override
    public Optional<Agency> Add(Agency agency) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO agnce(code,name,adresse,phone) VALUES(?,?,?,?)");
            statement.setString(1, agency.getCode());
            statement.setString(2, agency.getName());
            statement.setString(3, agency.getAdresse());
            statement.setString(4, agency.getPhone());


        }catch (Exception e){

        }
        return Optional.empty();
    }

    @Override
    public int Delete(String code) {
        return 0;
    }

    @Override
    public Optional<Agency> Update(Agency agency) {
        return Optional.empty();
    }

    @Override
    public Optional<Agency> SearchByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Agency> SearchByAdresse(String adresse) {
        return Optional.empty();
    }

    @Override
    public Map<String, Object> ShowContact() {
        return null;
    }
}
