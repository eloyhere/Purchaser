package team.item.purchaser.forum.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.service.ConsumerService;

import java.util.List;
import java.util.Objects;

@Controller
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    ConsumerService consumerService;

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
    public ModelAndView preRegister(){
        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("captcha", "111");
        modelAndView.setViewName("register.html");
        return modelAndView;
    }

    @PostMapping(value = "register")
    public Object postRegister(Consumer consumer, @RequestParam String captcha){
        HttpSession session = request.getSession();
        if(Objects.equals(session.getAttribute("captcha"), captcha)){
            List<String> keys = consumerService.existsByKey(consumer);
            if(keys.isEmpty()){
                consumerService.insert(consumer);
                return "ok";
            }
            return keys;
        }
        return null;
    }
}
