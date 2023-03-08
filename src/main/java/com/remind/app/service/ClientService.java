package com.remind.app.service;

import com.remind.app.model.Client;

public interface ClientService {

    void SaveClient(String clientName, String password, String fullName);
    Client findByClientName(String clientName);
    Client login(String clientName, String password);


}
