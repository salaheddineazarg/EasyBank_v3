package com.jetbrains.easybank_v3.services;

import com.jetbrains.easybank_v3.dao.ClientD;
import com.jetbrains.easybank_v3.dto.Client;
import com.jetbrains.easybank_v3.servlets.DashboardServlet;
import com.jetbrains.easybank_v3.servlets.DeleteServlet;
import com.jetbrains.easybank_v3.servlets.SaveServlet;
import com.jetbrains.easybank_v3.servlets.UpdateServlet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientService {
    public   ClientD clientD;
    private  Client client;

     public ClientService(ClientD clientD){
         this.clientD = clientD;
     }


    public boolean CreateClient(Client client){
        try{

            Optional<Client> clientSaveResult = clientD.AddPerson(client);

            if (clientSaveResult.isPresent()){
               return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return  false;
    }
    public int DeleteClient(String code){


            int DeleteResult = clientD.DeletePerson(code);
            return DeleteResult;


    }
    public Client GetByCodeService(String code) {

     return  clientD.SearchByCode(code).orElse(null);


    }

    public List<Client> GetClients() {
        List<Client> clientList = new ArrayList<>();
        try {
            client = new Client();
            clientList = clientD.GetAll();

               for(Client client1: clientList){
                client.setCode(client1.getCode());
                client.setFirstName(client1.getFirstName());
                client.setLastName(client1.getLastName());
                client.setNbPhone(client1.getNbPhone());
                client.setDateBirth(client1.getDateBirth());
                client.setAdresse(client1.getAdresse());

            }
               clientList.add(client);
        } catch (Exception e) {
            System.out.println("catch");
            e.printStackTrace();
        }

        return clientList;
    }


    public boolean UpdateClient(Client client) {

        try{
      ;

        Optional<Client> clientOptional = clientD.UpdatePerson(client);
        if (clientOptional.isPresent()) {

            return true;
        }

        }catch (Exception e){
            e.printStackTrace();
        }

       return false;
    }
}
