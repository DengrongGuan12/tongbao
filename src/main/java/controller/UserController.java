package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

/**
 * Created by cg on 2015/12/29.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("register")
    public String userRegister(Model model){

        User user = new User("158518131313","fsdf",new Byte("1"));
        boolean result=userService.registerUser(user);
        if(result){
            model.addAttribute("message","register success");
        }else{
            model.addAttribute("message","register fail");
        }
        return "register";
    }
}
