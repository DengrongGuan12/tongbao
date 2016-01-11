package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pojo.*;
import service.OrderService;
import service.UserService;
import vo.ContactDetail;
import vo.RestResult;
import java.util.List;

/**
 * Created by cg on 2015/12/29.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    public static int count = 0;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @RequestMapping("/userRegister")
    public String userRegister(Model model){
        boolean result=userService.register("15851813131214", "fsdf", new Byte("1"));
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

    @RequestMapping(value = "/register",method = RequestMethod.POST)
        @ResponseBody
    public RestResult register(UserAuthInfo userAuthInfo){
//        try {
//            userAuthInfo.setPhoneNumber(new String(userAuthInfo.getPhoneNumber().getBytes("ISO-8859-1"),"UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return RestResult.CreateResult(1,userAuthInfo);
        if(userService.register(userAuthInfo.getPhoneNumber(),userAuthInfo.getPassword(),userAuthInfo.getType())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"注册失败");
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
        @ResponseBody
    public RestResult login(@ModelAttribute("UserAuthInfo")UserAuthInfo userAuthInfo){
        vo.User user = userService.login(userAuthInfo.getPhoneNumber(),userAuthInfo.getPassword());

        if(user == null){
            return RestResult.CreateResult(0,"登录失败");
        }else{
            return RestResult.CreateResult(1,user);
        }
    }

    @RequestMapping(value = "/modifyNickName", method = RequestMethod.POST)
        @ResponseBody
    public RestResult modifyNickName(@ModelAttribute("ModifiedNickName")ModifiedNickName modifiedNickName){
        int id = userService.hasLogin(modifiedNickName.getToken());
        if(id == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(userService.changeNickName(id,modifiedNickName.getNickName())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
        @ResponseBody
    public RestResult modifyPassword(@ModelAttribute("ModifiedPassword")ModifiedPassword modifiedPassword){
        int id = userService.hasLogin(modifiedPassword.getToken());
        if(id == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(userService.changePassword(id,modifiedPassword.getOldPassword(),modifiedPassword.getNewPassword())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "/modifyIcon", method = RequestMethod.POST)
        @ResponseBody
    public RestResult modifyIcon(@ModelAttribute("ModifiedIcon")ModifiedIcon modifiedIcon){
        int id = userService.hasLogin(modifiedIcon.getToken());
        if(id == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(userService.changeIconUrl(id,modifiedIcon.getIconUrl())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }
    @RequestMapping(value = "showAccount",method = RequestMethod.POST)
    @ResponseBody
    public RestResult showAccount(@ModelAttribute("TokenAuthInfo") TokenAuthInfo tokenAuthInfo){
//        List list = userService.getUserAccount(1);
//        return RestResult.CreateResult(1,list);
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else {
            List list = userService.getUserAccount(userId);
            return RestResult.CreateResult(1,list);
        }
    }

    @RequestMapping(value = "getContacts",method = RequestMethod.POST)
        @ResponseBody
    public RestResult getContacts(@ModelAttribute("TokenAuthInfo") TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = userService.getContacts(userId);
            return RestResult.CreateResult(1,list);
        }
    }
    @RequestMapping(value = "getContactDetail",method = RequestMethod.POST)
        @ResponseBody
    public RestResult getContactDetail(@ModelAttribute("UserIdInfoWithAuth")UserIdInfoWithAuth userIdInfoWithAuth){
        int userId = userService.hasLogin(userIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            ContactDetail contactDetail = userService.getContactDetail(userIdInfoWithAuth.getId());
            return RestResult.CreateResult(1,contactDetail);

        }
    }
    @RequestMapping(value = "getMyMessages",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getMyMessages(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = userService.getMessagesByUserId(userId);
            return RestResult.CreateResult(1,list);
        }

    }
    @RequestMapping(value = "getAllTruckTypes",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getAllTruckTypes(){
        List list = userService.getTruckTypes();
        return RestResult.CreateResult(1,list);

    }
    @RequestMapping(value = "recharge",method = RequestMethod.POST)
    @ResponseBody
    public RestResult recharge(@ModelAttribute("RechargeInfo")RechargeInfo rechargeInfo){
        int userId = userService.hasLogin(rechargeInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(userService.recharge(userId,rechargeInfo.getMoney())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"充值失败!");
            }
        }
    }



}
