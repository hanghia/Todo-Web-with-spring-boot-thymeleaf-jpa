package com.remind.app.controller;

import com.remind.app.dto.AdminDto;
import com.remind.app.exception.ClientException;
import com.remind.app.model.Admin;
import com.remind.app.model.Client;
import com.remind.app.request.LoginRequest;
import com.remind.app.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginAdminController {
    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/dashboard")
    public String home(Model model, HttpSession session){
        List<Client> clientList = adminService.getAllClient();
        model.addAttribute("clientList", clientList);
        return "admin/dashbroad";
    }
    @GetMapping("/admin")
    public String showLogin(Model model){
        model.addAttribute("loginrequest", new LoginRequest("", ""));
        return "/admin/login";
    }

    @PostMapping("/admin")
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors())
            return "/admin/login";
        Admin admin;
        try {
            admin = adminService.loginAD(loginRequest.username(), loginRequest.password());
            session.setAttribute("admin", new AdminDto(admin.getAdminId(), admin.getUsername(), admin.getPassword()));
            return "redirect:/dashboard";
        }catch(ClientException ex){
            switch (ex.getMessage()){
                case "U NOT FOUND":
                    bindingResult.addError(new FieldError("loginrequest", "username", "Username is invalid"));
                    break;
                case "P INVALID":
                    bindingResult.addError(new FieldError("loginrequest", "password","Password is invalid"));
                    break;
            }
            System.out.println(ex.getMessage());
            return "/admin/login";
        }


    }

}
