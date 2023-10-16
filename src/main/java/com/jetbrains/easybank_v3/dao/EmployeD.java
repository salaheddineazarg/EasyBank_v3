package com.jetbrains.easybank_v3.dao;



import connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.Employe;
import com.jetbrains.easybank_v3.interfaces.iEmploye;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class EmployeD implements iEmploye {

    Connection conn = ConnectionDB.getInstance().getConnection();
    Employe employe = new Employe();



    @Override
    public Optional<Employe> AddPerson(Employe person) {


        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO person(firstName,LastName,dateOfBirth,phoneNumber) VALUES(?,?,?,?) RETURNING *");

            // Set values for the parameters
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setDate(3,Date.valueOf(person.getDateBirth()));
            statement.setString(4, person.getNbPhone());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                employe.setMatricule(resultSet.getString("registrationNumber"));
                employe.setFirstName(resultSet.getString("firstName"));
                employe.setLastName(resultSet.getString("lastName"));
                employe.setDateBirth(resultSet.getDate("dateOfBirth").toLocalDate());
                employe.setNbPhone(resultSet.getString("phoneNumber"));


            }

            PreparedStatement statement1 = conn.prepareStatement("INSERT INTO employe(id,registrationNumber,recrutmentDate,email) VALUES(?,?,?,?) RETURNING * ");
            statement1.setString(1, employe.getMatricule());
            statement1.setString(2, person.getMatricule());
            statement1.setDate(3, Date.valueOf(person.getDateRecrutement()));
            statement1.setString(4, person.getEmail());

            ResultSet resultSet1 = statement1.executeQuery();


            if (resultSet1.next()) {

                employe.setMatricule(resultSet1.getString("registrationNumber"));

                employe.setDateRecrutement(resultSet1.getDate("recrutmentDate").toLocalDate());


            }

            conn.commit();

            conn.setAutoCommit(true);


            return Optional.of(employe);

        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of an error
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            // Changed to printStackTrace() for better error handling

            e.printStackTrace();
            return Optional.empty();
        }


    }


    @Override
    public int DeletePerson(String keyword) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM person WHERE id = (SELECT id FROM employe WHERE registrationNumber = ?)");
            statement.setString(1, keyword);

            int DeleteEmploye = statement.executeUpdate();

            return DeleteEmploye;
        } catch (SQLException e) {

            e.printStackTrace();

            return 0;
        }


    }

    @Override
    public Optional<Employe> UpdatePerson(Employe person) {
        Employe employe = new Employe();

        try {
            conn.setAutoCommit(false);
            PreparedStatement statementUpdate = conn.prepareStatement("UPDATE person SET firstName = ? ,LastName = ?, dateOfBirth =?,phoneNumber = ? WHERE id= (SELECT id FROM employe WHERE registrationNumber = ?) RETURNING *");

            // Set values for the parameters
            statementUpdate.setString(1, person.getFirstName());
            statementUpdate.setString(2, person.getLastName());
            statementUpdate.setDate(3,Date.valueOf(person.getDateBirth()));
            statementUpdate.setString(4, person.getNbPhone());

            ResultSet resultSet = statementUpdate.executeQuery();

            if (resultSet.next()) {
                employe.setMatricule(resultSet.getString("registrationNumber"));
                employe.setFirstName(resultSet.getString("firstName"));
                employe.setLastName(resultSet.getString("lastName"));
                employe.setDateBirth(resultSet.getDate("dateOfBirth").toLocalDate());
                employe.setNbPhone(resultSet.getString("phoneNumber"));


            }

            PreparedStatement statementUpdate1 = conn.prepareStatement("UPDATE employe SET registrationNumber = ?,recrutmentDate = ?,email = ? WHERE id = ?   RETURNING * ");

            statementUpdate1.setString(1, employe.getMatricule());
            statementUpdate1.setObject(2, employe.getDateRecrutement());
            statementUpdate1.setString(3, employe.getEmail());
            statementUpdate1.setString(4,employe.getMatricule());

            ResultSet resultSet1 = statementUpdate1.executeQuery();


            if (resultSet1.next()) {

                employe.setMatricule(resultSet1.getString("registrationNumber"));

                employe.setDateRecrutement(resultSet1.getDate("recrutmentDate").toLocalDate());


            }

            conn.commit();

            conn.setAutoCommit(true);


            return Optional.of(employe);

        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of an error
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            // Changed to printStackTrace() for better error handling

            e.printStackTrace();
            return Optional.empty();
        }




    }




    @Override
    public Optional<Employe> SearchByMatricule(String matricule) {


        try {
            PreparedStatement statementSearch = conn.prepareStatement("SELECT * FROM person INNER JOIN employe ON employe.id = person.id WHERE employe.registrationNumber = ?");
            statementSearch.setString(1, matricule);  // Set the matricule parameter
            ResultSet resultSet = statementSearch.executeQuery();

            if (resultSet.next()){
                Employe EmployeSearchMatricule = new Employe();

                EmployeSearchMatricule.setFirstName(resultSet.getString("firstName"));
                EmployeSearchMatricule.setLastName(resultSet.getString("lastName"));
                EmployeSearchMatricule.setDateBirth(resultSet.getDate("dateOfBirth").toLocalDate());
                EmployeSearchMatricule.setNbPhone(resultSet.getString("phoneNumber"));
                EmployeSearchMatricule.setMatricule(resultSet.getString("registrationNumber"));
                EmployeSearchMatricule.setDateRecrutement(resultSet.getDate("recrutmentDate").toLocalDate());
                EmployeSearchMatricule.setEmail(resultSet.getString("email"));

                return Optional.of(EmployeSearchMatricule);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.empty();

    }



    @Override
    public Map<String, Object> GetAll() {


        try {
            PreparedStatement statementSearch = conn.prepareStatement("SELECT * FROM person INNER JOIN employe ON employe.id = person.id ");
            // Set the matricule parameter
            ResultSet resultSet = statementSearch.executeQuery();
            Map<String, Object> GetAllMap = new HashMap<>();
            while(resultSet.next()){

                GetAllMap.put("First Name", resultSet.getString("firstName"));
                GetAllMap.put("Last Name", resultSet.getString("lastName"));
                GetAllMap.put("Date Of Birth", resultSet.getDate("dateOfBirth").toLocalDate());
                GetAllMap.put("N°Phone", resultSet.getString("phoneNumber"));
                GetAllMap.put("Matricule", resultSet.getString("registrationNumber"));
                GetAllMap.put("Recrutement Date", resultSet.getDate("recrutmentDate").toLocalDate());
                GetAllMap.put("Email", resultSet.getString("email"));

            }
            return GetAllMap;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, Object> Search(String attributeName, String value) {

        try {
            String sqlQuery = "SELECT * FROM person INNER JOIN employe ON employe.id = person.id WHERE " + attributeName + " LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, "%" + value + "%");

            ResultSet resultSet = statement.executeQuery();
            Map<String, Object> Employeresult = new HashMap<>();
            while (resultSet.next()) {

                Employeresult.put("FirstName", resultSet.getString("firstName"));
                Employeresult.put("LastName", resultSet.getString("lastName"));
                Employeresult.put("DateBirth", resultSet.getDate("dateOfBirth").toLocalDate());
                Employeresult.put("N°Phone", resultSet.getString("phoneNumber"));
                Employeresult.put("Matricule", resultSet.getString("registrationNumber"));
                Employeresult.put("DateRecrutement", resultSet.getDate("recrutmentDate").toLocalDate());
                Employeresult.put("Email", resultSet.getString("email"));


            }
             return Employeresult;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}