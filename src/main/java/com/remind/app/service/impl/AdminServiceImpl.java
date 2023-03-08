package com.remind.app.service.impl;

import com.remind.app.exception.ClientException;
import com.remind.app.model.Admin;
import com.remind.app.model.Client;
import com.remind.app.model.ToDo;
import com.remind.app.repository.AdminRepository;
import com.remind.app.repository.ClientRepository;
import com.remind.app.repository.ToDoRepository;
import com.remind.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired private AdminRepository adminRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ToDoRepository toDoRepository;
    @Autowired private ClientServiceImpl clientService;

    @Override
    public void addClient(Client client) {
        clientService.SaveClient(client.getClientName(), client.getFullName(), client.getPassword());
    }

    @Override
    public Client updateClient(long id, Client client) {
        Client existClient = clientRepository.findById(id).orElseThrow(() -> new ClientException("Client is not exists"));
        existClient.setFullName(client.getFullName());
        existClient.setPassword(client.getPassword());
        clientRepository.save(existClient);
        return existClient;
    }

    @Override
    public void deleteClient(long id) {
        toDoRepository.deleteByClientId(id);
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public List<ToDo> viewDetailClient(int id) {
        return adminRepository.viewById(id);
    }

    @Override
    public Admin loginAD(String username, String password) {
        Optional<Admin> o_ad = Optional.ofNullable(adminRepository.findByAdName(username));
        if(!o_ad.isPresent()){
            throw new ClientException("U NOT FOUND");
        }
        Admin ad = o_ad.get();
        if(ad.getPassword().contains(password)){
            return ad;
        }
        else
            throw new ClientException("P INVALID");
    }
}
