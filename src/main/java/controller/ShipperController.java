package controller;

import org.apache.taglibs.standard.extra.spath.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.OrderService;
import service.ShipperService;
import service.UserService;
import vo.RestResult;

import java.util.ArrayList;
import java.util.HashMap;
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

    @RequestMapping(value = "placeOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult placeOrder(@ModelAttribute("OrderInfo")OrderInfo orderInfo){
//        return RestResult.CreateResult(1,orderInfo);
        int userId = userService.hasLogin(orderInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(orderService.createOrder(userId,orderInfo)){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "evaluateOrder", method = RequestMethod.POST)
        @ResponseBody
    public  RestResult evaluateOrder(@ModelAttribute("EvaluateInfo")EvaluateInfo evaluateInfo){
        int userId = userService.hasLogin(evaluateInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(shipperService.evaluateOrder(userId,evaluateInfo.getId(),evaluateInfo.getEvaluateType(),evaluateInfo.getEvaluate())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "showMyOrderList", method = RequestMethod.POST)
        @ResponseBody
    public RestResult showMyOrderList(@ModelAttribute("OrderListType")OrderListType orderListType){
        int userId = userService.hasLogin(orderListType.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = orderService.getOrderList(userId,orderListType.getType());
            return RestResult.CreateResult(1,list);
        }
    }

    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult deleteOrder(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else {
            if(orderService.deleteOrder(userId,orderIdInfoWithAuth.getId())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"删除失败!");
            }
        }

    }
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult cancelOrder(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录！");
        }else{
            switch (orderService.cancelOrder(userId,orderIdInfoWithAuth.getId())){
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
    }

    @RequestMapping(value = "finishOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult finishOrder(@ModelAttribute("OrderIdInfoWithAuth")OrderIdInfoWithAuth orderIdInfoWithAuth){
        int userId = userService.hasLogin(orderIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(orderService.finishOrder(userId,orderIdInfoWithAuth.getId())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"结束失败!");
            }
        }
    }

    @RequestMapping(value = "recharge",method = RequestMethod.POST)
        @ResponseBody
    public RestResult recharge(@ModelAttribute("RechargeInfo")RechargeInfo rechargeInfo){
        int userId = userService.hasLogin(rechargeInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(shipperService.recharge(userId,rechargeInfo.getMoney())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"充值失败!");
            }
        }
    }

}
