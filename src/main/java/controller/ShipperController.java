package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pojo.*;
import service.OrderService;
import service.ShipperService;
import service.UserService;
import vo.OrderDetail;
import vo.RestResult;
import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
@Controller
@RequestMapping("/shipper")
public class ShipperController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperService shipperService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/getFrequentDrivers", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getFrequentDrivers(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        List list = shipperService.getFrequentDrivers(userId);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/auth/getFrequentAddresses",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getFrequentAddresses(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        List list = shipperService.getFrequentAddresses(userId);
        return RestResult.CreateResult(1,list);
    }
    @RequestMapping(value = "/auth/addFrequentDriver",method = RequestMethod.POST)
    @ResponseBody
    public RestResult addFrequentDriver(@ModelAttribute("UserIdInfoWithAuth")UserIdInfoWithAuth userIdInfoWithAuth){
        int userId = userService.hasLogin(userIdInfoWithAuth.getToken());
        if(shipperService.addFrequentDriver(userId,userIdInfoWithAuth.getId())){
            return RestResult.CreateResult(1);
        }else {
            return RestResult.CreateResult(0,"添加失败!");
        }
    }

    @RequestMapping(value = "/auth/searchDriver", method = RequestMethod.POST)
    @ResponseBody
    public RestResult searchDriver(@ModelAttribute("SearchInfoWithAuth")SearchInfoWithAuth searchInfoWithAuth){
        int userId = userService.hasLogin(searchInfoWithAuth.getToken());
        List list = shipperService.searchDriversByPhoneNum(searchInfoWithAuth.getPhoneNum());
        return RestResult.CreateResult(1,list);

    }

    @RequestMapping(value = "/auth/addFrequentAddress", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addFrequentAddress(@ModelAttribute("AddressInfo")AddressInfo addressInfo){
        int userId = userService.hasLogin(addressInfo.getToken());
        if(shipperService.addFrequentAddress(userId,addressInfo)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }

    @RequestMapping(value = "/auth/placeOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult placeOrder(@ModelAttribute("OrderInfo")OrderInfo orderInfo){
//        return RestResult.CreateResult(1,orderInfo);
        int userId = userService.hasLogin(orderInfo.getToken());
        int result = orderService.createOrder(userId,orderInfo);
        switch (result){
            case 0:
                return RestResult.CreateResult(0,"操作失败!");
            case 1:
                return RestResult.CreateResult(1,"创建成功!");
            case 2:
                return RestResult.CreateResult(2,"需要拆单!");
            default:
                return RestResult.CreateResult(0,"操作失败!");
        }

    }

    @RequestMapping(value = "/auth/evaluateOrder", method = RequestMethod.POST)
        @ResponseBody
    public  RestResult evaluateOrder(@ModelAttribute("EvaluateInfo")EvaluateInfo evaluateInfo){
        int userId = userService.hasLogin(evaluateInfo.getToken());
        if(shipperService.evaluateOrder(userId,evaluateInfo.getId(),evaluateInfo.getEvaluatePoint(),evaluateInfo.getEvaluate())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }

    @RequestMapping(value = "/auth/splitOrder",method = RequestMethod.POST)
    @ResponseBody
    public RestResult splitOrder(@ModelAttribute("OrderInfo")OrderInfo orderInfo){
        int userId = userService.hasLogin(orderInfo.getToken());
        if(orderService.splitOrder(userId,orderInfo)){
            return RestResult.CreateResult(1,"拆单成功!");
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }

    @RequestMapping(value = "/auth/showMyOrderList", method = RequestMethod.POST)
        @ResponseBody
    public RestResult showMyOrderList(@ModelAttribute("OrderListType")OrderListType orderListType){
        int userId = userService.hasLogin(orderListType.getToken());
        List list = orderService.getMyOrderList(userId,orderListType.getType());
        return RestResult.CreateResult(1,list);
//        userId = 1;
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
    @RequestMapping(value = "/auth/cancelOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult cancelOrder(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        switch (orderService.cancelOrder(userId, idInfoWithAuth.getId())){
            case 0:
                return RestResult.CreateResult(0,"取消失败!");
            case 1:
                return RestResult.CreateResult(1);
            case 2:
                return RestResult.CreateResult(2);
            default:
                return RestResult.CreateResult(1);
        }
    }

    @RequestMapping(value = "/auth/finishOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult finishOrder(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        if(orderService.finishOrder(userId, idInfoWithAuth.getId())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"结束失败!");
        }
    }

    @RequestMapping(value = "/auth/getOrderDetail",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getOrderDetail(@ModelAttribute("IdInfoWithAuth") IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        OrderDetail orderDetail = orderService.getOrderDetail(userId, idInfoWithAuth.getId());
        return RestResult.CreateResult(1,orderDetail);
    }

}
