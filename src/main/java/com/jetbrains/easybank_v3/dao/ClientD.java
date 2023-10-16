package com.jetbrains.easybank_v3.dao;



import com.jetbrains.easybank_v3.dto.Client;
import com.jetbrains.easybank_v3.interfaces.iClient;
import connection.ConnectionDB;

import java.sql.*;

import java.sql.Date;
import java.util.*;

public class ClientD implements iClient {
    Connection conn = ConnectionDB.getInstance().getConnection();
    Client client = new Client();
    @Override
    public Optional<Client> AddPerson(Client person) {
     try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO client(firstname,lastname,dateofbirth,phone,code,adresse) VALUES(?,?,?,?,?,?) RETURNING *");
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setDate(3, Date.valueOf(person.getDateBirth()));
            statement.setString(4, person.getNbPhone());
            statement.setString(5, person.getCode());
            statement.setString(6, person.getAdresse());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                client.setCode(resultSet.getString("code"));
                client.setFirstName(resultSet.getString("firstname"));
                client.setLastName(resultSet.getString("lastname"));
                client.setDateBirth(resultSet.getDate("dateofbirth").toLocalDate());
                client.setNbPhone(resultSet.getString("phone"));
                client.setAdresse(resultSet.getString("adresse"));
            }
              return  Optional.of(client);
     }catch (Exception e){
         e.printStackTrace();

     }
        return Optional.empty();
    }

    @Override
    public int DeletePerson(String keyword ) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM client WHERE code =?");
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

        try {
            PreparedStatement statementUpdate = conn.prepareStatement("UPDATE client SET firstname = ? ,lastname = ?, dateofbirth =?,phone = ?,adresse = ? WHERE code = ? RETURNING *");
            statementUpdate.setString(1, person.getFirstName());
            statementUpdate.setString(2, person.getLastName());
            statementUpdate.setDate(3, Date.valueOf(person.getDateBirth())); // Assuming dateBirth is of type LocalDate
            statementUpdate.setString(4, person.getNbPhone());
            statementUpdate.setString(5, person.getAdresse());
            statementUpdate.setString(6, person.getCode());
            ResultSet resultSet = statementUpdate.executeQuery();

            if (resultSet.next()) {
                client.setFirstName(resultSet.getString("firstname"));
                client.setLastName(resultSet.getString("lastname"));
                client.setDateBirth(resultSet.getDate("dateofbirth").toLocalDate());
                client.setNbPhone(resultSet.getString("phone"));
                client.setCode(resultSet.getString("code"));
                client.setAdresse(resultSet.getString("adresse"));
            }

            return Optional.of(client);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();

        }


    }

    @Override
    public Optional<Client> SearchByCode(String code) {


        try {
            PreparedStatement statementSearch = conn.prepareStatement("SELECT * FROM client WHERE code = ?");
            statementSearch.setString(1, code);
            ResultSet resultSet = statementSearch.executeQuery();

            if (resultSet.next()){
               Client clientSearchCode = new Client();
                clientSearchCode.setFirstName(resultSet.getString("firstname"));
                clientSearchCode.setLastName( resultSet.getString("lastname"));
                clientSearchCode.setDateBirth( resultSet.getDate("dateofbirth").toLocalDate());
                clientSearchCode.setNbPhone(resultSet.getString("phone"));
                clientSearchCode.setCode( resultSet.getString("code"));
                clientSearchCode.setAdresse(resultSet.getString("adresse"));

                return Optional.of(clientSearchCode) ;
            }
        } catch (SQLException e){
            e.printStackTrace();


        }

        return Optional.empty();


    }

    public List<Client> GetAll() {
        List<Client> clientList = new ArrayList<>();

        try {
            PreparedStatement statementGetAll = conn.prepareStatement("SELECT DISTINCT * FROM client");

            ResultSet resultSet = statementGetAll.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setFirstName(resultSet.getString("firstname"));
                client.setLastName(resultSet.getString("lastname"));
                client.setDateBirth(resultSet.getDate("dateofbirth").toLocalDate());
                client.setNbPhone(resultSet.getString("phone"));
                client.setCode(resultSet.getString("code"));
                client.setAdresse(resultSet.getString("adresse"));

                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientList;
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
