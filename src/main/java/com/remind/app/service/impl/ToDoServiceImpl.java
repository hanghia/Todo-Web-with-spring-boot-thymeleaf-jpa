package com.remind.app.service.impl;

import com.remind.app.model.ToDo;
import com.remind.app.repository.ClientRepository;
import com.remind.app.repository.ToDoRepository;
import com.remind.app.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ToDoServiceImpl implements ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    ClientRepository clientRepository;
    @Override
    public List<ToDo> getByClientId(int id) {
        return toDoRepository.findByClient_Id(id);
    }

    @Override
    public void deleteTodo(long id, int clientId) {
        toDoRepository.deleteTodo(id, clientId);
    }

    @Override
    public void addTodoByClientId(String title, String detail, int clientId) {
         toDoRepository.addTodoByClientId(title, detail, clientId);
    }

}
