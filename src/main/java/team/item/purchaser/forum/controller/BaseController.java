package team.item.purchaser.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Controller
public class BaseController {

    @RequestMapping(value = "success")
    public Object success(){
        return "success";
    }

    @RequestMapping(value = "fail")
    public Object fail(){
        return "fail";
    }
}
