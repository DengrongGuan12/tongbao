package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import manager.UserManager;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pojo.ModifiedPassword;
import pojo.TokenAuthInfo;
import service.AccountService;
import service.DriverService;
import service.OrderService;
import service.UserService;
import vo.LatestNumInfo;
import vo.RestResult;
import vo.TruckInfo;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by I322233 on 1/14/2016.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    DriverService driverService;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

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
    public String index(Model model,HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        int unSubmittedDriverNum = driverService.getUnSubmittedDriverNum();
        int waitingExamineDriverNum = driverService.getWaitingExamineDriverNum();
        int examinedDriverNum = driverService.getExaminedDriverNum();
        model.addAttribute("unSubmittedDriverNum",unSubmittedDriverNum);
        model.addAttribute("waitingExamineDriverNum",waitingExamineDriverNum);
        model.addAttribute("examinedDriverNum",examinedDriverNum);

        int shipperNum = userService.getTotalShipperNum();
        int driverNum = userService.getTotalDriverNum();
        int orderNum = orderService.getTotalOrderNum();
        int accountNum = userService.getTotalAccountNum();
        model.addAttribute("shipperNum",shipperNum);
        model.addAttribute("driverNum",driverNum);
        model.addAttribute("orderNum",orderNum);
        model.addAttribute("accountNum",accountNum);

        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "resetPassword";
    }

    @RequestMapping(value = "/modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    public RestResult modifyPassword(ModifiedPassword modifiedPassword,HttpSession session){
        String oldPassword = modifiedPassword.getOldPassword();
        if(oldPassword.equals(session.getAttribute("password"))){
            int id = (Integer) session.getAttribute("id");
            if(userService.changePassword(id,oldPassword,modifiedPassword.getNewPassword())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"未知的错误!");
            }
        }else {
            return RestResult.CreateResult(0,"原密码不正确!");
        }
    }
    @RequestMapping(value = "/getLatestNumInfo",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getLatestNumInfo(){
        int shipperNum = userService.getTotalShipperNum();
        int driverNum = userService.getTotalDriverNum();
        int accountNum = userService.getTotalAccountNum();
        int orderNum = orderService.getTotalOrderNum();
        LatestNumInfo latestNumInfo = new LatestNumInfo();
        latestNumInfo.setShipperNum(shipperNum);
        latestNumInfo.setDriverNum(driverNum);
        latestNumInfo.setAccountNum(accountNum);
        latestNumInfo.setOrderNum(orderNum);
        return RestResult.CreateResult(1,latestNumInfo);
    }

    @RequestMapping(value = "/getRecentRegisterUsers",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getRecentRegisterUsers(@RequestParam(value = "type")Byte type, @RequestParam(value = "num")int num){
        List list = userService.getRecentRegUsers(type,num);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/getRecentOrders", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getRecentOrders(@RequestParam(value = "num")int num){
        List list = orderService.getRecentOrders(num);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/getRecentAccounts", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getRecentAccounts(@RequestParam(value = "num")int num){
        List list = userService.getRecentAccounts(num);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/shipperManage")
    public String shipperManage(Model model,HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        return "shipperManage";
    }

    @RequestMapping(value = "/getAllUsersByType",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getAllUsersByType(@RequestParam(value = "type")Byte type){
        List list = userService.getAllUsersByType(type);
        return RestResult.CreateResult(1,list);
    }


    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteUser(@RequestParam(value = "id")int id){
        System.out.println(id);
        if(userService.deleteUser(id)){
            return RestResult.CreateResult(1);
        }else {
            return RestResult.CreateResult(0,"删除失败!");
        }
    }

    @RequestMapping(value = "/resetUserPassword",method = RequestMethod.POST)
    @ResponseBody
    public RestResult resetUserPassword(@RequestParam(value = "id")int id, @RequestParam(value = "password")String password){
        if(userService.resetPassword(id,password)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"重置失败");
        }
    }

    @RequestMapping(value = "/driverManage")
    public String driverManage(Model model, HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        return "driverManage";
    }
    @RequestMapping(value = "/driverDetail/{id}")
    public String driverDetail(@PathVariable int id, Model model, HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        model.addAttribute("id",id);
        User user = userService.getUserById(id);
        model.addAttribute("nickName",user.getNick_name());
        model.addAttribute("money",user.getMoney());
        model.addAttribute("phoneNum",user.getPhone_number());
        model.addAttribute("point",user.getPoint());
        model.addAttribute("registerTime",user.getRegister_time());
        model.addAttribute("url",user.getIcon());
        return "driverDetail";
    }

    @RequestMapping(value = "/getAllTruckInfo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getAllTruckInfo(@RequestParam(value = "userId") int userId){
        List list = driverService.getAllTruckInfoByUserId(userId);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/truckDetail/{id}")
    public String truckDetail(@PathVariable int id,Model model,HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        TruckInfo truckInfo = driverService.getTruckInfoById(id);
        model.addAttribute("id",id);
        model.addAttribute("userId",truckInfo.getUserId());
        model.addAttribute("authState",truckInfo.getAuthState());
        model.addAttribute("drivingLicense",truckInfo.getDrivingLicense());
        model.addAttribute("headPicture",truckInfo.getHeadPicture());
        model.addAttribute("licenseNum",truckInfo.getLicenseNum());
        model.addAttribute("phoneNum",truckInfo.getPhoneNum());
        model.addAttribute("realName",truckInfo.getRealName());
        model.addAttribute("truckLicense",truckInfo.getTruckLicense());
        model.addAttribute("truckNum",truckInfo.getTruckNum());
        model.addAttribute("truckPicture",truckInfo.getTruckPicture());
        model.addAttribute("typeName",truckInfo.getTypeName());
        model.addAttribute("capacity",truckInfo.getCapacity());
        model.addAttribute("length",truckInfo.getLength());
        model.addAttribute("width",truckInfo.getWidth());
        model.addAttribute("height",truckInfo.getHeight());
        return "truckDetail";
    }
    @RequestMapping(value = "/deleteTruck", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteTruck(@RequestParam(value = "id") int id){
        if(driverService.deleteTruckById(id)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }
    @RequestMapping(value = "/setAuthState",method = RequestMethod.POST)
    @ResponseBody
    public RestResult setAuthState(@RequestParam(value = "id") int id, @RequestParam(value = "state")byte state){
        if(driverService.setAuthState(id,state)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"设置失败!");
        }
    }
    @RequestMapping(value = "/orderManage")
    public String orderManage(Model model,HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        return "orderManage";
    }
    @RequestMapping(value = "/accountManage")
    public String accountManage(Model model,HttpSession session){
        model.addAttribute("name",session.getAttribute("name"));
        return "accountManage";
    }
    @RequestMapping(value = "/getAllOrders", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getAllOrders(){
        List list = orderService.getAllOrders();
        return RestResult.CreateResult(1,list);
    }
    @RequestMapping(value = "/deleteOrder",method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteOrder(@RequestParam(value = "id")int id){
        if(orderService.deleteOrder(id)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"删除失败!");
        }
    }
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
    @ResponseBody
    public RestResult cancelOrder(@RequestParam(value = "id")int id){
        if(orderService.cancelOrder(id)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"取消失败!");
        }
    }

    @RequestMapping(value = "/deleteAccount",method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteAccount(@RequestParam(value = "id")int id){
        if(accountService.deleteAccount(id)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"删除失败!");
        }
    }
    @RequestMapping(value = "/getAllAccounts",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getAllAccounts(){
        List list = accountService.getAllAccounts();
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/genTestData", method = RequestMethod.GET)
    @ResponseBody
    public RestResult genTestData(){

        return RestResult.CreateResult(1);
    }

}
