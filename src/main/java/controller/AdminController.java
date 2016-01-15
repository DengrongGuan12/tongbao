package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by I322233 on 1/14/2016.
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
