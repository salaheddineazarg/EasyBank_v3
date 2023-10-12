package com.jetbrains.easybank_v3.dao;



import com.jetbrains.easybank_v3.connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.Client;
import com.jetbrains.easybank_v3.interfaces.iClient;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClientD implements iClient {


    Connection conn = ConnectionDB.getInstance().getConnection();





    @Override
    public Optional<Client> AddPerson(Client person) {

        Client client = new Client();

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

                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setDateBirth(resultSet.getDate("dateOfBirth").toLocalDate());
                client.setNbPhone(resultSet.getString("phoneNumber"));


            }

            PreparedStatement statement1 = conn.prepareStatement("INSERT INTO client(code,adresse) VALUES(?,?,?) RETURNING * ");

            statement1.setString(3, person.getAdresse());

            ResultSet resultSet1 = statement1.executeQuery();


            if(resultSet1.next())
            {

                client.setCode(resultSet1.getString("code"));

                client.setAdresse(resultSet1.getString("adresse"));


            }

            conn.commit();

            conn.setAutoCommit(true);


            return Optional.of(client);

        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of an error
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            // Changed to printStackTrace() for better error handling
            return Optional.empty() ;
        }


    }

    @Override
    public int DeletePerson(String keyword ) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM person WHERE id = (SELECT id FROM client WHERE code = ?)");
            statement.setString(1,keyword);

            int DeleteClient = statement.executeUpdate();

            return DeleteClient;
        }catch (SQLException e){

            e.printStackTrace();

            return 0;
        }


    }

    @Override
    public Optional<Client> UpdatePerson(Client person) {
        Client client = new Client();
        try {
            conn.setAutoCommit(false);
            PreparedStatement statementUpdate = conn.prepareStatement("UPDATE person SET firstName = ? ,LastName = ?, dateOfBirth =?,phoneNumber = ? WHERE id= (SELECT id FROM client WHERE code = ?)  RETURNING *");

            // Set values for the parameters
            statementUpdate.setString(1, person.getFirstName());
            statementUpdate.setString(2, person.getLastName());
            statementUpdate.setDate(3, Date.valueOf(person.getDateBirth())); // Assuming dateBirth is of type LocalDate
            statementUpdate.setString(4, person.getNbPhone());

            ResultSet resultSet = statementUpdate.executeQuery();

            if (resultSet.next()) {
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setDateBirth(resultSet.getDate("dateOfBirth").toLocalDate());
                client.setNbPhone(resultSet.getString("phoneNumber"));


            }

            PreparedStatement statementUpdate1 = conn.prepareStatement("UPDATE client SET code =?,adresse = ? WHERE code = client.getId  RETURNING * ");

            statementUpdate1.setString(1, person.getCode());
            statementUpdate1.setString(3, person.getAdresse());

            ResultSet resultSet1 = statementUpdate1.executeQuery();


            if (resultSet1.next()) {

                client.setCode(resultSet1.getString("code"));

                client.setAdresse(resultSet1.getString("adresse"));


            }

            conn.commit();

            conn.setAutoCommit(true);


            return Optional.of(client);

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
    public Optional<Client> SearchByCode(String code) {


        try {
            PreparedStatement statementSearch = conn.prepareStatement("SELECT * FROM person INNER JOIN client ON client.id = person.id WHERE client.code = ?");
            statementSearch.setString(1, code);
            ResultSet resultSet = statementSearch.executeQuery();

            if (resultSet.next()){
               Client clientSearchCode = new Client();
                clientSearchCode.setFirstName(resultSet.getString("firstName"));
                clientSearchCode.setLastName( resultSet.getString("lastName"));
                clientSearchCode.setDateBirth( resultSet.getDate("dateOfBirth").toLocalDate());
                clientSearchCode.setNbPhone(resultSet.getString("phoneNumber"));
                clientSearchCode.setCode( resultSet.getString("code"));
                clientSearchCode.setAdresse(resultSet.getString("adresse"));

                return Optional.of(clientSearchCode) ;
            }
        } catch (SQLException e){
            e.printStackTrace();


        }

        return Optional.empty();


    }

    @Override
    public Map<String, Object> GetAll() {

        try {
            PreparedStatement statementGetAll = conn.prepareStatement("SELECT * FROM person INNER JOIN client ON client.id = person.id ");

            ResultSet resultSet = statementGetAll.executeQuery();
            Map<String, Object> clientGetALLMap = new HashMap<>();
            while (resultSet.next()){

                clientGetALLMap.put("id", resultSet.getInt("id"));
                clientGetALLMap.put("firstName", resultSet.getString("firstName"));
                clientGetALLMap.put("lastName", resultSet.getString("lastName"));
                clientGetALLMap.put("dateBirth", resultSet.getDate("dateOfBirth").toLocalDate());
                clientGetALLMap.put("nbPhone", resultSet.getString("phoneNumber"));
                clientGetALLMap.put("code", resultSet.getString("code"));
                clientGetALLMap.put("adresse", resultSet.getString("adresse"));


            }
            return  clientGetALLMap;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, Object> Search(String attributeName, String value) {

        try {
            String sqlQuery = "SELECT * FROM person INNER JOIN client ON client.id = person.id WHERE " + attributeName + " LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, "%" + value + "%");

            ResultSet resultSet = statement.executeQuery();
            Map<String, Object> Clientresult = new HashMap<>();
            while (resultSet.next()) {


                Clientresult.put("Id", resultSet.getInt("id"));
                Clientresult.put("First Name", resultSet.getString("firstName"));
                Clientresult.put("Last Name", resultSet.getString("lastName"));
                Clientresult.put("Date of Birth", resultSet.getDate("dateOfBirth"));
                Clientresult.put("N°Phone", resultSet.getString("phoneNumber"));
                Clientresult.put("Code", resultSet.getString("code"));
                Clientresult.put("Adresse", resultSet.getString("adresse"));


            }
 return  Clientresult;
        } catch (SQLException e) {
            e.printStackTrace();
        }

      return null;
    }

}
