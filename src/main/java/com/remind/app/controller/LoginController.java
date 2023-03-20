package com.remind.app.controller;


import com.remind.app.dto.ClientDto;
import com.remind.app.exception.ClientException;
import com.remind.app.model.Client;
import com.remind.app.request.LoginRequest;
import com.remind.app.request.RegisterRequest;
import com.remind.app.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired private ClientServiceImpl clientServiceImpl;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/")
    public String index(){
        return "client/index";
    }
    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("loginrequest", new LoginRequest("",""));
        return "client/login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest,
                              BindingResult result, HttpSession session){
        if(result.hasErrors()){
            System.out.println(result);
            return "client/login";
        }

        Client client;
        try{

            client = clientServiceImpl.login(loginRequest.username(), loginRequest.password());

            session.setAttribute("client", new ClientDto(client.getClientId(), client.getClientName(), client.getFullName()));
            return "redirect:/todo";
        }catch (ClientException ex){
            /*System.out.println(bCryptPasswordEncoder.matches(loginRequest.password(), client.getPassword()));*/
            switch (ex.getMessage()){
                case "Username is not found":
                    result.addError(new FieldError("loginrequest", "username", "Username is not exists. Please register!"));
                    break;
                case "Password is invalid":
                    result.addError(new FieldError("loginrequest","password","Password is incorrect"));
                    break;
            }

            return "client/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("client");
        return "redirect:/";
    }

    /*
        Register
     */

    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("registerrequest", new RegisterRequest("","",""));
        return "client/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest, BindingResult result){
        if(result.hasErrors())
            return "client/register";
        try{
            clientServiceImpl.SaveClient(registerRequest.clientName(), registerRequest.password(), registerRequest.fullName());
            return "redirect:/login";
        }catch (ClientException ex){
            switch (ex.getMessage()) {
                case "Username is exists":
                    result.addError(new FieldError("registerrequest", "clientName", "Username is exists.Please choose the other name!"));
                    break;
                case "U < 6":
                    result.addError(new FieldError("registerrequest", "clientName", "Username must be larger 6 character"));
                    break;
                case "U > 32":
                    result.addError(new FieldError("registerrequest", "clientName", "Username must be smaller 32 character"));
                    break;
                case "P > 8":
                    result.addError(new FieldError("registerrequest", "password", "Password must be larger 8 character"));
                    break;
            }
            return "client/register";
        }
    }

}
