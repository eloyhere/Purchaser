package team.item.purchaser.forum.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Controller
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "success")
    public Object success(){
        return "success";
    }

    @RequestMapping(value = "fail")
    public Object fail(){
        return "fail";
    }

    @GetMapping(value = "login")
    public Object login(){
        return "login";
    }

    @GetMapping(value = "register")
    public Object register(){
        HttpSession session = request.getSession();
        return "register";
    }
}
