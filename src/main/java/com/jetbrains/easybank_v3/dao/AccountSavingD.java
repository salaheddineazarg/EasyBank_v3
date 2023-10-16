package com.jetbrains.easybank_v3.dao;



import connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.AccountSaving;
import com.jetbrains.easybank_v3.dto.Etat;
import com.jetbrains.easybank_v3.interfaces.iAccountSaving;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountSavingD implements iAccountSaving {
    Connection conn = ConnectionDB.getInstance().getConnection();


    @Override
    public Optional<AccountSaving> AddAccount(AccountSaving account) {

        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO account(accountNumber,balance,creationDate,client_code,employe_matricule,status) VALUES(?,?,?,?,?,?) RETURNING *");
            statement.setString(1,account.getNrAccount());
            statement.setDouble(2,account.getSolde());
            statement.setDate(3, Date.valueOf(account.getDeteCreation()));
            statement.setString(4,account.getClient().getCode());
            statement.setString(5,account.getEmploye().getMatricule());
            statement.setObject(6,account.getEtat(),Types.OTHER);
            ResultSet resultSet = statement.executeQuery();
            AccountSaving accountSaving = new AccountSaving();
            if (resultSet.next()){

                accountSaving.setNrAccount(resultSet.getString("accountNumber"));
                accountSaving.setSolde(resultSet.getDouble("balance"));
                accountSaving.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountSaving.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountSaving.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountSaving.setEtat(Etat.valueOf(resultSet.getString("status")));
            }

            PreparedStatement statement1 = conn.prepareStatement("INSERT INTO savingaccount(interestRate,id) VALUES(?,?) RETURNING *");

            statement1.setDouble(1,accountSaving.getInterestRate());
            statement1.setString(2, accountSaving.getNrAccount());
            ResultSet resultSet1 = statement1.executeQuery();

            if (resultSet1.next()){
                accountSaving.setInterestRate(resultSet1.getDouble("interestRate"));
            }
            conn.commit();
            conn.setAutoCommit(true);

            return Optional.of(accountSaving);
        }catch (SQLException e){
            e.printStackTrace();

            try {
                conn.rollback(); // Rollback the transaction in case of an error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }

        return Optional.empty();
    }

    @Override
    public int DeleteAccount(String id) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM account WHERE accountNumber = ?");
            statement.setString(1, id);

            int DeleteSaving = statement.executeUpdate();

            return DeleteSaving;
        } catch (SQLException e) {

            e.printStackTrace();

            return 0;
        }

    }

    @Override
    public Optional<AccountSaving> FindById(String id) {
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN savingaccount ON account.accountNumber = savingaccount.id WHERE accountNumber = ?  ");
            statement.setString(1,id );

            ResultSet resultSet = statement.executeQuery();
            AccountSaving accountSaving= new AccountSaving();
            if (resultSet.next()){

                accountSaving.setNrAccount(resultSet.getString("accountNumber"));
                accountSaving.setSolde(resultSet.getDouble("balance"));
                accountSaving.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountSaving.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountSaving.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountSaving.setEtat(Etat.valueOf(resultSet.getString("status")));

            }
          return   Optional.of(accountSaving);
        }catch (SQLException e){


        }



        return Optional.empty();
    }


    @Override
    public Optional<AccountSaving> UpdateAccount(AccountSaving account) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement("UPDATE account SET balance = ?,creationDate = ?,client_code = ? ,employe_matricule =? ,status = ? WHERE accountNumber = ?  RETURNING *");

            statement.setDouble(1,account.getSolde());
            statement.setDate(2, Date.valueOf(account.getDeteCreation()));
            statement.setObject(3,account.getClient().getCode());
            statement.setObject(4,account.getEmploye().getMatricule());
            statement.setObject(5, account.getEtat(), Types.OTHER);
            statement.setString(6, account.getNrAccount());
            ResultSet resultSet = statement.executeQuery();
            AccountSaving accountSaving = new AccountSaving();
            if (resultSet.next()){

                accountSaving.setNrAccount(resultSet.getString("accountNumber"));
                accountSaving.setSolde(resultSet.getDouble("balance"));
                accountSaving.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountSaving.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountSaving.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountSaving.setEtat(Etat.valueOf(resultSet.getString("status")));
            }

            PreparedStatement statement1 = conn.prepareStatement("UPDATE savingaccount SET interestRate= ? WHERE id= ? RETURNING *");

            statement1.setDouble(1,accountSaving.getInterestRate());
            statement1.setString(2, accountSaving.getNrAccount());
            ResultSet resultSet1 = statement1.executeQuery();

            if (resultSet1.next()){
                accountSaving.setInterestRate(resultSet1.getDouble("interestRate"));
            }
            conn.commit();
            conn.setAutoCommit(true);

            return Optional.of(accountSaving);
        }catch (SQLException e){
            e.printStackTrace();

            try {
                conn.rollback(); // Rollback the transaction in case of an error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }



        return Optional.empty();
    }

    @Override
    public Optional<AccountSaving> ShowByDateCreation(LocalDate DateCreation) {

        try{
          PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN savingAccount ON account.accountNumber = savingaccount.id WHERE creationDate = ?  ");
          statement.setDate(1, Date.valueOf(DateCreation));

          ResultSet resultSet = statement.executeQuery();
          AccountSaving accountSaving = new AccountSaving();
            if (resultSet.next()){

                accountSaving.setNrAccount(resultSet.getString("accountNumber"));
                accountSaving.setSolde(resultSet.getDouble("balance"));
                accountSaving.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountSaving.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountSaving.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountSaving.setEtat(Etat.valueOf(resultSet.getString("status")));

            }
           return Optional.of(accountSaving);
        }catch (SQLException e){


        }

        return Optional.empty();
    }

    @Override
    public Map<String, Object> ShowByStatus(String status) {


        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN SavingAccount ON account.accountNumber = SavingAccount.id WHERE status = ?  ");
            statement.setString(1,status);

            ResultSet resultSet = statement.executeQuery();
            Map<String,Object> SavingStatusMAp = new HashMap<>();
            while (resultSet.next()){

                SavingStatusMAp.put("Account Number",resultSet.getString("accountNumber"));
                SavingStatusMAp.put("Solde",resultSet.getDouble("balance"));
                SavingStatusMAp.put("Date Of Creation",resultSet.getDate("creationDate").toLocalDate());
                SavingStatusMAp.put("Client",new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                SavingStatusMAp.put("Employe",new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                SavingStatusMAp.put("Status",Etat.valueOf(resultSet.getString("status")));

            }
           return  SavingStatusMAp;
        }catch (SQLException e){
          e.printStackTrace();
            return null;
        }


    }

    @Override
    public Map<String, Object> GetAll() {

        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN SavingAccount ON account.accountNumber = SavingAccount.id  ");


            ResultSet resultSet = statement.executeQuery();
            Map<String,Object> SavingAllMAp = new HashMap<>();
            while (resultSet.next()){

                SavingAllMAp.put("Account Number",resultSet.getString("accountNumber"));
                SavingAllMAp.put("Solde",resultSet.getDouble("balance"));
                SavingAllMAp.put("Date Of Creation",resultSet.getDate("creationDate").toLocalDate());
                SavingAllMAp.put("Client",new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                SavingAllMAp.put("Employe",new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                SavingAllMAp.put("Status",Etat.valueOf(resultSet.getString("status")));

            }
            return  SavingAllMAp;
        }catch (SQLException e){
         e.printStackTrace();

        }

        return null;
    }

    @Override
    public boolean ChangeEtat(AccountSaving accountSaving) {

        try {

            PreparedStatement statement = conn.prepareStatement("UPDATE Account SET status = ? WHERE accountNumber = ?  ");
            statement.setObject(1,accountSaving.getEtat(),Types.OTHER);
            statement.setString(2,accountSaving.getNrAccount());
            int ChangeEtat = statement.executeUpdate();

            if (ChangeEtat >0){
                return true;
            }
        }catch (SQLException e){

        }

        return false;
    }

    @Override
    public Optional<AccountSaving> SearchByClient(String code) {
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN savingaccount ON account.accountNumber = savingaccount.id WHERE client_code = ?  ");
            statement.setString(1,code);

            ResultSet resultSet = statement.executeQuery();
            AccountSaving accountSaving = new AccountSaving();
            if (resultSet.next()){

                accountSaving.setNrAccount(resultSet.getString("accountNumber"));
                accountSaving.setSolde(resultSet.getDouble("balance"));
                accountSaving.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountSaving.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountSaving.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountSaving.setEtat(Etat.valueOf(resultSet.getString("status")));

            }
            Optional.of(accountSaving);
        }catch (SQLException e){


        }

        return Optional.empty();
    }
}
