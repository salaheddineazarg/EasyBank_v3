package com.jetbrains.easybank_v3.dao;



import com.jetbrains.easybank_v3.connection.ConnectionDB;
import com.jetbrains.easybank_v3.dto.AccountCurrant;
import com.jetbrains.easybank_v3.dto.Etat;
import com.jetbrains.easybank_v3.interfaces.iAccountCurrant;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public  class AccountCurrantD implements iAccountCurrant {
    Connection conn = ConnectionDB.getInstance().getConnection();
    AccountCurrant accountCurrant = new AccountCurrant();
    @Override
    public Optional<AccountCurrant> AddAccount(AccountCurrant account) {

     try {
         conn.setAutoCommit(false);
         PreparedStatement statement = conn.prepareStatement("INSERT INTO account(accountNumber,balance,creationDate,client_code,employe_matricule,status) VALUES(?,?,?,?,?,?) RETURNING *");
         statement.setString(1,account.getNrAccount());
         statement.setDouble(2,account.getSolde());
         statement.setDate(3, Date.valueOf(account.getDeteCreation()));
         statement.setString(4,account.getClient().getCode());
         statement.setString(5,account.getEmploye().getMatricule());
         statement.setObject(6, account.getEtat(), Types.OTHER);
         ResultSet resultSet = statement.executeQuery();

         if (resultSet.next()){

             accountCurrant.setNrAccount(resultSet.getString("accountNumber"));
             accountCurrant.setSolde(resultSet.getDouble("balance"));
             accountCurrant.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
             accountCurrant.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
             accountCurrant.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
             accountCurrant.setEtat(Etat.valueOf(resultSet.getString("status")));
         }

         PreparedStatement statement1 = conn.prepareStatement("INSERT INTO currentaccount(id,maxprice) VALUES(?,?) RETURNING *");
         statement1.setString(1, accountCurrant.getNrAccount());
         statement1.setDouble(2,accountCurrant.getMaxPrice());
         ResultSet resultSet1 = statement1.executeQuery();

         if (resultSet1.next()){
             accountCurrant.setMaxPrice(resultSet1.getDouble("maxprice"));

         }
         conn.commit();
         conn.setAutoCommit(true);

       return Optional.of(accountCurrant);
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
    public Optional<AccountCurrant> FindById(String id) {
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN currentaccount ON account.accountNumber = currentAccount.id WHERE accountNumber = ?  ");
            statement.setString(1,id );

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                accountCurrant.setNrAccount(resultSet.getString("accountNumber"));
                accountCurrant.setSolde(resultSet.getDouble("balance"));
                accountCurrant.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountCurrant.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountCurrant.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountCurrant.setEtat(Etat.valueOf(resultSet.getString("status")));

            }
          return  Optional.of(accountCurrant);
        }catch (SQLException e){


        }



        return Optional.empty();
    }

    @Override
    public Optional<AccountCurrant> UpdateAccount(AccountCurrant account) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement("UPDATE account SET balance = ?,creationDate = ?,client_code = ? ,employe_matricule =?,status = ? WHERE accountNumber = ?  RETURNING *");

            statement.setDouble(1,account.getSolde());
            statement.setDate(2, Date.valueOf(account.getDeteCreation()));
            statement.setObject(3,account.getClient().getCode());
            statement.setObject(4,account.getEmploye().getMatricule());
            statement.setString(5, account.getEtat().name());
            statement.setString(6, account.getNrAccount());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                accountCurrant.setNrAccount(resultSet.getString("accountNumber"));
                accountCurrant.setSolde(resultSet.getDouble("balance"));
                accountCurrant.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountCurrant.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountCurrant.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountCurrant.setEtat(Etat.valueOf(resultSet.getString("status")));
            }

            PreparedStatement statement1 = conn.prepareStatement("UPDATE currentaccount SET maxprice= ? WHERE id= ? RETURNING *");

            statement1.setDouble(1,accountCurrant.getMaxPrice());
            statement1.setString(2, accountCurrant.getNrAccount());
            ResultSet resultSet1 = statement1.executeQuery();

            if (resultSet1.next()){
                accountCurrant.setMaxPrice(resultSet1.getDouble("maxprice"));
            }
            conn.commit();
            conn.setAutoCommit(true);

            return Optional.of(accountCurrant);
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
    public Optional<AccountCurrant> ShowByDateCreation(LocalDate DateCreation) {
        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN currentaccount ON account.accountNumber = currentaccount.id WHERE creationdate = ?  ");
            statement.setDate(1, Date.valueOf(DateCreation));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                accountCurrant.setNrAccount(resultSet.getString("accountNumber"));
                accountCurrant.setSolde(resultSet.getDouble("balance"));
                accountCurrant.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountCurrant.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountCurrant.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountCurrant.setEtat(Etat.valueOf(resultSet.getString("status")));

            }
           return Optional.of(accountCurrant);
        }catch (SQLException e){


        }

        return Optional.empty();
    }

    @Override
    public Map<String,Object> ShowByStatus(String status) {


        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN currentaccount ON account.accountNumber = currentaccount.id WHERE status = ?  ");
            statement.setObject(1,Etat.valueOf(status),Types.OTHER);

            ResultSet resultSet = statement.executeQuery();
            Map<String,Object> CurrantStatusMAp = new HashMap<>();
            while (resultSet.next()){

                CurrantStatusMAp.put("Account Number",resultSet.getString("accountNumber"));
                CurrantStatusMAp.put("Solde",resultSet.getDouble("balance"));
                CurrantStatusMAp.put("Date Of Creation",resultSet.getDate("creationDate").toLocalDate());
                CurrantStatusMAp.put("Client",new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                CurrantStatusMAp.put("Employe",new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                CurrantStatusMAp.put("Status",Etat.valueOf(resultSet.getString("status")));

            }
            return CurrantStatusMAp ;
        }catch (SQLException e){
             e.printStackTrace();
            return null;
        }


    }

    @Override
    public Map<String,Object> GetAll() {

        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN currentaccount ON account.accountNumber = currentaccount.id  ");


            ResultSet resultSet = statement.executeQuery();
            Map<String,Object> CurrantAllMAp = new HashMap<>();
             while (resultSet.next()){

                CurrantAllMAp.put("Account Number",resultSet.getString("accountNumber"));
                CurrantAllMAp.put("Solde",resultSet.getDouble("balance"));
                CurrantAllMAp.put("Date Of Creation",resultSet.getDate("creationDate").toLocalDate());
                CurrantAllMAp.put("Client",new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                CurrantAllMAp.put("Employe",new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                CurrantAllMAp.put("Status",Etat.valueOf(resultSet.getString("status")));

            }
             return  CurrantAllMAp;
        }catch (SQLException e){


        }

        return null;
    }

    @Override
    public boolean ChangeEtat(AccountCurrant accountCurrant) {
        try {

            PreparedStatement statement = conn.prepareStatement("UPDATE account SET status = ? WHERE accountNumber = ?  ");
            statement.setObject(1,accountCurrant.getEtat(),Types.OTHER);
            statement.setString(2,accountCurrant.getNrAccount());

                    int ChangeEtat = statement.executeUpdate();

            if (ChangeEtat >0){
              return true;
            }
        }catch (SQLException e){

        }

        return false;
    }



    @Override
    public Optional<AccountCurrant> SearchByClient(String code) {

        try{
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM  account INNER JOIN currentaccount ON account.accountNumber = currentaccount.id WHERE client_code = ?  ");
            statement.setString(1,code);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){

                accountCurrant.setNrAccount(resultSet.getString("accountNumber"));
                accountCurrant.setSolde(resultSet.getDouble("balance"));
                accountCurrant.setDeteCreation(resultSet.getDate("creationDate").toLocalDate());
                accountCurrant.setClient(new ClientD().SearchByCode(resultSet.getString("client_code")).get());
                accountCurrant.setEmploye(new EmployeD().SearchByMatricule(resultSet.getString("employe_matricule")).get());
                accountCurrant.setEtat(Etat.valueOf(resultSet.getString("status")));

            }
            return  Optional.of(accountCurrant);
        }catch (SQLException e){

             e.printStackTrace();
        }

        return Optional.empty();
    }
}
