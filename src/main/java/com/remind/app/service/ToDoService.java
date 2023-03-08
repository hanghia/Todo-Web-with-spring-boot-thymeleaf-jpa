package com.remind.app.service;

import com.remind.app.model.ToDo;

import java.util.List;

public interface ToDoService {
    List<ToDo> getByClientId(int id);
    void deleteTodo(long id, int clientId);
    void addTodoByClientId(String title, String detail, int clientId);
}
