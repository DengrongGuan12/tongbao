package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.TruckInfo;
import service.DriverService;
import service.UserService;
import vo.RestResult;
import vo.TruckType;

/**
 * Created by I322233 on 1/4/2016.
 */
@Controller
@RequestMapping("driver")
public class DriverController {
    @Autowired
    UserService userService;
    @Autowired
    DriverService driverService;
    @RequestMapping(value = "setTruckInfo",method = RequestMethod.POST)
    @ResponseBody
    public RestResult setTruckInfo(@ModelAttribute("TruckInfo")TruckInfo truckInfo){
        int userId = userService.hasLogin(truckInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(driverService.setTruckInfo(userId,truckInfo)){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"设置失败!");
            }
        }
    }

}
