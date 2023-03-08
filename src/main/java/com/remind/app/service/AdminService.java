package com.remind.app.service;

import com.remind.app.model.Admin;
import com.remind.app.model.Client;
import com.remind.app.model.ToDo;

import java.util.List;

public interface AdminService {
    void addClient(Client client);
    Client updateClient(long id, Client client);
    void deleteClient(long id);
    List<Client> getAllClient();
    List<ToDo> viewDetailClient(int id);
    Admin loginAD(String username, String password);
}
