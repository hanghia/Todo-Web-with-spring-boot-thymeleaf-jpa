package com.remind.app.service.impl;

import com.remind.app.exception.ClientException;
import com.remind.app.model.Client;
import com.remind.app.repository.ClientRepository;
import com.remind.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void SaveClient(String clientName, String password, String fullName) {
        // tạo optional kiểm tra xem tên có trong danh sách chưa
        Optional<Client> o_client = Optional.ofNullable(clientRepository.findByClientName(clientName));
        // nếu trả về false thì lưu
        if(!o_client.isPresent())
            clientRepository.saveUser(clientName, fullName, bCryptPasswordEncoder.encode(password));
        else
            throw new ClientException("Username is exists");

        if (clientName.length() < 6 && clientName != null)  throw new ClientException("U < 6");
        if (clientName.length() > 32 && clientName != null)  throw new ClientException("U > 32");
        if (password.length() < 8 && password != null)  throw new ClientException("P > 8");

    }

    @Override
    public Client findByClientName(String clientName) {

        return clientRepository.findByClientName(clientName);
    }

    @Override
    public Client login(String clientName, String password) {
        Optional<Client> o_client = Optional.ofNullable(clientRepository.findByClientName(clientName));
        if(!o_client.isPresent()){
            throw new ClientException("Username is not found");
        }
        Client client = o_client.get();
        if(bCryptPasswordEncoder.matches(password, client.getPassword())){
            return client;
        }
        else
            throw new ClientException("Password is invalid");
    }
}
