package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.DriverService;
import service.OrderService;
import service.UserService;
import vo.OrderDetail;
import vo.RestResult;
import vo.TruckType;

import java.util.List;

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
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "addTruck", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addTruck(@ModelAttribute("TruckInfo")TruckInfo truckInfo){
//        driverService.addTruck(1,truckInfo);
//        return RestResult.CreateResult(1,truckInfo);
        int userId = userService.hasLogin(truckInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(driverService.addTruck(userId, truckInfo)){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }
    @RequestMapping(value = "setRealNameAuthInfo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult setRealNameAuthInfo(@ModelAttribute("RealNameAuthInfo")RealNameAuthInfo realNameAuthInfo){
        int userId = userService.hasLogin(realNameAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(driverService.setRealNameInfo(userId,realNameAuthInfo)){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"设置失败!");
            }
        }
    }
    @RequestMapping(value = "setTruckAuthInfo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult setTruckAuthInfo(@ModelAttribute("TruckAuthInfo")TruckAuthInfo truckAuthInfo){
        int userId = userService.hasLogin(truckAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(driverService.setTruckAuthInfo(userId,truckAuthInfo)){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"设置失败!");
            }
        }
    }
    @RequestMapping(value = "showMyOrderList",method = RequestMethod.POST)
    @ResponseBody
    public RestResult showMyOrderList(@ModelAttribute("OrderListType")OrderListType orderListType){
        int userId = userService.hasLogin(orderListType.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = orderService.getMyOrderList(userId,orderListType.getType());
            return RestResult.CreateResult(1,list);
        }

    }
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult cancelOrder(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(orderService.cancelOrder(userId,orderIdInfoWithAuth.getId()) == 0){
                return RestResult.CreateResult(0,"取消失败!");
            }else{
                return RestResult.CreateResult(1);
            }
        }
    }
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteOrder(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(orderService.deleteOrder(userId,orderIdInfoWithAuth.getId())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"删除失败!");
            }
        }
    }
    @RequestMapping(value = "grabOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult grabOrder(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(orderService.grabOrder(userId,orderIdInfoWithAuth.getId())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"抢单失败!");
            }
        }
    }
    @RequestMapping(value = "showAllOrders",method = RequestMethod.POST)
    @ResponseBody
    public RestResult showAllOrders(@ModelAttribute("OrderFilterInfo")OrderFilterInfo orderFilterInfo){
        int userId = userService.hasLogin(orderFilterInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = orderService.getAllNoGrabOrder(userId,orderFilterInfo);
            return RestResult.CreateResult(1,list);
        }
    }

    @RequestMapping(value = "getOrderDetail",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getOrderDetail(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            OrderDetail orderDetail = orderService.getOrderDetail(userId,orderIdInfoWithAuth.getId());
            return RestResult.CreateResult(1,orderDetail);
        }
    }

}
