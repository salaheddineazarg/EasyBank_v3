package com.jetbrains.easybank_v3.dao;



import com.jetbrains.easybank_v3.connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.Mission;
import com.jetbrains.easybank_v3.interfaces.iMission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MissionD implements iMission {
    Connection conn = ConnectionDB.getInstance().getConnection();
    @Override
    public Optional<Mission> AddMission(Mission mission) {

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO mission(code,name,description) VALUES(?,?,?) RETURNING * ");

            statement.setString(1,mission.getCode());
            statement.setString(2, mission.getName());
            statement.setString(3, mission.getDescription());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Mission missionreturn = new Mission();
                missionreturn.setCode(resultSet.getString("code"));
                missionreturn.setName(resultSet.getString("name"));
                missionreturn.setDescription(resultSet.getString("description"));

                return Optional.of(missionreturn);
            }


        }
        catch (SQLException e){
            e.printStackTrace();


        }
        return Optional.empty();
        }




    @Override
    public int DeleteMission(String code) {
        try {
            PreparedStatement statementDelete = conn.prepareStatement("DELETE FROM mission WHERE code = ?");
            statementDelete.setString(1,code);
            int MissionDelete = statementDelete.executeUpdate();
            return MissionDelete;
        }catch (SQLException e){
          e.printStackTrace();
        }


        return 0;
    }

    @Override
    public Map<String, Object> GetAll() {

        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM misssion");
           ResultSet resultSet = statement.executeQuery();
            Map<String,Object> SetMission = new HashMap<>();
           while (resultSet.next()){

               SetMission.put("Code",resultSet.getString("code"));
               SetMission.put("Name",resultSet.getString("name"));
               SetMission.put("Description",resultSet.getString("description"));

           }
            return  SetMission;

        }catch (SQLException e){

        }

        return null;
    }


    @Override
    public Optional<Mission> FindOne(String code) {
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM mission WHERE code = ? ");
            statement.setString(1,code);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Mission missionreturn = new Mission();
                missionreturn.setCode(resultSet.getString("code"));
                missionreturn.setName(resultSet.getString("name"));
                missionreturn.setDescription(resultSet.getString("description"));

                return Optional.of(missionreturn);
            }

        }catch (SQLException e){

        }

        return Optional.empty();
    }
}
