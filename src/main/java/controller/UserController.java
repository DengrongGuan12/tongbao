package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import vo.RestResult;

/**
 * Created by cg on 2015/12/29.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    public static int count = 0;
    @Autowired
    private UserService userService;
    @RequestMapping("/register")
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
    @RequestMapping(value = "/id/{id}",method = RequestMethod.GET)
        @ResponseBody
    public RestResult getUser(@PathVariable int id){
//        String[] strings = {"a","b","c"};
//        String string = "sdsd";
        count++;
        User user = userService.getUserById(id);
        return RestResult.CreateResult(200,user);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
        @ResponseBody
    public int getCount(){
        count++;
        return count;
    }
}
