package com.remind.app.controller;


import com.remind.app.dto.ClientDto;
import com.remind.app.model.ToDo;
import com.remind.app.service.impl.ToDoServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ToDoController {

    @Autowired private ToDoServiceImpl toDoService;
    @GetMapping("/todo")
    public String listTodo(Model model, HttpSession session){
        ClientDto clientDto = (ClientDto) session.getAttribute("client");
        if(clientDto != null){
            List<ToDo>toDoList = toDoService.getByClientId(clientDto.clientId());
            model.addAttribute("client", clientDto);
            model.addAttribute("todoList", toDoList);
            model.addAttribute("isAdd", true);
            model.addAttribute("todo", new ToDo());
            return "client/listTodo";
        }
        return "client/listTodo";
    }

    @PostMapping(value = "/addTodo")
    public String addToDo(@ModelAttribute ToDo toDo, RedirectAttributes redirectAttributes,
                          Model model, HttpSession session){
        ClientDto clientDto = (ClientDto) session.getAttribute("client");
        if(toDo.getTitle() != null && toDo.getDetail() != null) {
            toDoService.addTodoByClientId(toDo.getTitle(), toDo.getDetail(), clientDto.clientId());
            redirectAttributes.addFlashAttribute("successmessage", "Add to successfully!");
            return "redirect:/todo";
        }
        else{
            model.addAttribute("errormessage", "Can't add, Try again!");
            model.addAttribute("todo", toDo);
            return "redirect:/todo";
        }

    }

    @GetMapping(value = "/delete/{id}")
    public String deleteTodo(@PathVariable long id, RedirectAttributes redirectAttributes, HttpSession session){
        ClientDto clientDto = (ClientDto) session.getAttribute("client");
        toDoService.deleteTodo(id, clientDto.clientId());
        redirectAttributes.addFlashAttribute("successmessage","You deleted successfully!");
        return "redirect:/todo";
    }
}
