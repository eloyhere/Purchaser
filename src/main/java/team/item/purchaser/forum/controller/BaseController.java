package team.item.purchaser.forum.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.exception.CaptchaCreateException;
import team.item.purchaser.forum.provider.CaptchaProvider;
import team.item.purchaser.forum.service.ConsumerService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Controller
public class BaseController {

    @Autowired
    CaptchaProvider captchaProvider;

    @Autowired
    ConsumerService consumerService;

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
    public ModelAndView preRegister() throws IOException {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()){
            HttpSession session = request.getSession();
            ModelAndView modelAndView = new ModelAndView();
            CaptchaProvider.Captcha captcha = captchaProvider.captcha();
            modelAndView.addObject("captcha", captcha.image());
            session.setAttribute("captcha", captcha.text());
            modelAndView.setViewName("register.html");
            return modelAndView;
        }catch (IOException exception){
            throw new CaptchaCreateException(exception);
        }
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
        return new ResponseEntity<>("Invalid captcha", HttpStatus.BAD_REQUEST);
    }
}
