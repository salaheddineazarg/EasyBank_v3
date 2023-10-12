package com.jetbrains.easybank_v3.dao;



import com.jetbrains.easybank_v3.connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.AffectationM;
import com.jetbrains.easybank_v3.interfaces.iAffectationM;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class AffectationD implements iAffectationM {
    Connection conn = ConnectionDB.getInstance().getConnection();
    @Override
    public Optional<AffectationM> AddAffictation(AffectationM affictation) {
      try {
          PreparedStatement statement = conn.prepareStatement("INSERT INTO affectation(startDate,mission_code,employee_registrationnumber) VALUES(?,?,?) RETURNING *");
          statement.setDate(1, Date.valueOf(affictation.getStartdate()));
          statement.setString(2,affictation.getMission().getCode());
          statement.setString(3,affictation.getEmploye().getMatricule());

          ResultSet resultSet = statement.executeQuery();
          if (resultSet.next()){
              AffectationM affectation = new AffectationM();

              affectation.setStartdate(resultSet.getDate("startDate").toLocalDate());
              if(resultSet.getDate("startDate").toLocalDate() == null){
                affectation.setEndDate(null);
              }else {
                  affectation.setEndDate(resultSet.getDate("endDate").toLocalDate());
              }

              affectation.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employee_registrationnumber")).get());
              affectation.setMission(new MissionD().FindOne(resultSet.getString("mission_code")).get());
              return Optional.of(affectation);
          }

      }catch (SQLException e){
          e.printStackTrace();
      }

        return Optional.empty();
    }

    @Override
    public int DeleteAffictation(int id) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM affectataion WHERE id= ?");
            statement.setInt(1,id);
            int DeleteAffectation = statement.executeUpdate();
            return DeleteAffectation;
        }catch (SQLException e){

        }
        return 0;
    }

    @Override
    public  Map<String,Object> HistoricAffictation() {

         try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM affectation ");
            ResultSet resultSet = statement.executeQuery();
            Map<String,Object> affectationMap = new HashMap<>();
            while (resultSet.next()){

                AffectationM affectation = new AffectationM();

                //  affectation.setMission(new  MissionD().FindOne(resultSet.getString("mission_code")).get());

                affectation.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employee_registrationnumber")).get());


             affectationMap.put("Employe Matricule:",affectation.getEmploye().getMatricule());
                String fullName = affectation.getEmploye().getFirstName() + " " + affectation.getEmploye().getLastName();
                affectationMap.put("Employe Name", fullName);
             affectationMap.put("Mission Name",affectation.getMission().getName());
             affectationMap.put("Mission Description",affectation.getMission().getDescription());
             affectationMap.put("Start Date",resultSet.getDate("startDate").toLocalDate());
             if (resultSet.getDate("EndDate")==null){
                 affectationMap.put("End Date","Present");
             }else {
                 affectationMap.put("End Date",resultSet.getDate("endDate").toLocalDate());
             }



            }
        return  affectationMap;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AffectationM StatisticAffictation() {


        return null;
    }

    @Override
    public Optional<AffectationM> FindOne(String code) {

        try {
            PreparedStatement statement  = conn.prepareStatement("SELECT * FROM affectation WHERE employee_registrationnumber = ?");
            statement.setString(1,code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                AffectationM affectationFind = new AffectationM();
              //  affectationFind.setId(resultSet.getInt("id"));
                affectationFind.setStartdate(resultSet.getDate("startDate").toLocalDate());
                affectationFind.setEndDate(resultSet.getDate("endDate").toLocalDate());
                affectationFind.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employee_registrationnumber")).get());
              //  affectationFind.setMission(new MissionD().FindOne(resultSet.getString("mission_code")).get());
                return Optional.of(affectationFind);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
