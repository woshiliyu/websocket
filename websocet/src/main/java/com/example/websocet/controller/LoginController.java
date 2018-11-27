package com.example.websocet.controller;

import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    @PostMapping(value = "/login")
     public String login (User user, HttpServletRequest request)
     {
        HttpSession session= request.getSession();
        if(session.getAttribute("loginUser") != null ){
            session.removeAttribute("loginUser");
        }
        String id= UUID.randomUUID().toString();
         user.setUserId(id);
         session.setAttribute("loginUser",user);

         return "/chat";
     }
}
