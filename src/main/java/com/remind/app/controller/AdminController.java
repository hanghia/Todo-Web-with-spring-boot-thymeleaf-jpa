package com.remind.app.controller;


import com.remind.app.dto.AdminDto;
import com.remind.app.dto.ClientDto;
import com.remind.app.exception.ClientException;
import com.remind.app.model.Admin;
import com.remind.app.model.Client;
import com.remind.app.model.ToDo;
import com.remind.app.request.LoginRequest;
import com.remind.app.request.RegisterRequest;
import com.remind.app.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;


    @GetMapping("/addUser")
    public String addUser(Model model, RedirectAttributes reD, HttpSession session){
        AdminDto adminDto = (AdminDto) session.getAttribute("admin");
        if(adminDto != null){
           /* adminService.addClient(client);
            *//*reD.addFlashAttribute("successmessage", "Add to successfully!");*/
            model.addAttribute("addRequest", new RegisterRequest("","",""));
            return "/admin/AddUser";
        }
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable long id, RedirectAttributes reD, HttpSession session){
        AdminDto adminDto = (AdminDto) session.getAttribute("admin");
        if(adminDto != null){
            adminService.deleteClient(id);
            reD.addFlashAttribute("successmessage", "Deleted successfully!");
        }
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/viewDetail/{id}")
    public String viewDetail(@PathVariable int id, Model model, HttpSession session){
        AdminDto adminDto = (AdminDto) session.getAttribute("admin");
        if(adminDto != null) {
            List<ToDo> toDoList = adminService.viewDetailClient(id);
            model.addAttribute("todoList", toDoList);
            return "admin/ViewDetail";
        }
        return "redirect:/dashboard";
    }

}
