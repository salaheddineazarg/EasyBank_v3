package com.jetbrains.easybank_v3.services;

import com.jetbrains.easybank_v3.dao.ClientD;
import com.jetbrains.easybank_v3.dto.Client;

public class ClientService {
    private static ClientD clientD ;
    public void CreationClient(Client client){
        clientD.AddPerson(client);
    }
}
