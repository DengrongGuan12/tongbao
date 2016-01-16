package controller;

import manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.TokenAuthInfo;
import service.UserService;
import vo.RestResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by I322233 on 1/14/2016.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public String login(HttpSession session){
        Object o = session.getAttribute("type");
        if(o != null){
            Byte type = (Byte)o;
            if(type == 2){
                return "index";
            }else{
                return "login";
            }
        }else{
            return "login";
        }
    }

    @RequestMapping(value = "/hasLogin",method = RequestMethod.POST)
    @ResponseBody
    public RestResult hasLogin(TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0);
        }else{
            return RestResult.CreateResult(1);
        }
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
