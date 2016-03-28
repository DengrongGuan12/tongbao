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
import vo.TruckDetail;

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

    @RequestMapping(value = "/auth/getTruckList", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getTruckList(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        List truckList = driverService.getTruckList(userId);
        return RestResult.CreateResult(1,truckList);
    }

    @RequestMapping(value = "/auth/getTruckDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getTruckDetail(@ModelAttribute("IdInfoWithAuth")IdInfoWithAuth idInfoWithAuth){
        int id = idInfoWithAuth.getId();
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        TruckDetail truckDetail = driverService.getTruckDetail(userId,id);
        if(truckDetail != null){
            return RestResult.CreateResult(1,truckDetail);
        }else{
            return RestResult.CreateResult(0,"无权限");
        }
    }

    @RequestMapping(value = "/auth/addTruck", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addTruck(@ModelAttribute("TruckInfo")TruckInfo truckInfo){
//        driverService.addTruck(1,truckInfo);
//        return RestResult.CreateResult(1,truckInfo);
        int userId = userService.hasLogin(truckInfo.getToken());
        if(driverService.addTruck(userId, truckInfo)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }
    @RequestMapping(value = "/auth/removeTruck", method = RequestMethod.POST)
    @ResponseBody
    public RestResult removeTruck(@ModelAttribute("TruckInfo")TruckInfo truckInfo){
        int userId = userService.hasLogin(truckInfo.getToken());
        if(driverService.removeTruck(userId,truckInfo.getTruckNum())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"删除失败!");
        }
    }
    @RequestMapping(value = "/auth/setRealNameAuthInfo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult setRealNameAuthInfo(@ModelAttribute("RealNameAuthInfo")RealNameAuthInfo realNameAuthInfo){
        int userId = userService.hasLogin(realNameAuthInfo.getToken());
        if(driverService.setRealNameInfo(userId,realNameAuthInfo)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"设置失败!");
        }
    }
    @RequestMapping(value = "/auth/setTruckAuthInfo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult setTruckAuthInfo(@ModelAttribute("TruckAuthInfo")TruckAuthInfo truckAuthInfo){
        int userId = userService.hasLogin(truckAuthInfo.getToken());
        String result = driverService.setTruckAuthInfo(userId,truckAuthInfo);
        if( result == null){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,result);
        }
    }
    @RequestMapping(value = "/auth/showMyOrderList",method = RequestMethod.POST)
    @ResponseBody
    public RestResult showMyOrderList(@ModelAttribute("OrderListType")OrderListType orderListType){
        int userId = userService.hasLogin(orderListType.getToken());
        List list = orderService.getMyOrderList(userId,orderListType.getType());
        return RestResult.CreateResult(1,list);

    }
    @RequestMapping(value = "/auth/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult cancelOrder(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        if(orderService.cancelOrder(userId, idInfoWithAuth.getId()) == 0){
            return RestResult.CreateResult(0,"取消失败!");
        }else{
            return RestResult.CreateResult(1);
        }
    }
    @RequestMapping(value = "/auth/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteOrder(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        if(orderService.deleteOrder(userId, idInfoWithAuth.getId())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"删除失败!");
        }

    }
    @RequestMapping(value = "/auth/grabOrder", method = RequestMethod.POST)
    @ResponseBody
    public RestResult grabOrder(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        if(orderService.grabOrder(userId, idInfoWithAuth.getId())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"抢单失败!");
        }
    }
    @RequestMapping(value = "/auth/updateMyPostion", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateMyPostion(@ModelAttribute("PositionInfo")PositionInfo positionInfo){
        int userId = userService.hasLogin(positionInfo.getToken());
        if(driverService.updateMyPosition(userId,positionInfo)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"更新失败!");
        }
    }
    @RequestMapping(value = "/auth/showAllOrders",method = RequestMethod.POST)
    @ResponseBody
    public RestResult showAllOrders(@ModelAttribute("OrderFilterInfo")OrderFilterInfo orderFilterInfo){
        int userId = userService.hasLogin(orderFilterInfo.getToken());
        List list = orderService.getAllNoGrabOrder(userId,orderFilterInfo);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/auth/getOrderDetail",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getOrderDetail(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        OrderDetail orderDetail = orderService.getOrderDetail(userId, idInfoWithAuth.getId());
        return RestResult.CreateResult(1,orderDetail);
    }

}
