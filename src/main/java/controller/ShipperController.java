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

    @RequestMapping(value = "getFrequentDrivers", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getFrequentDrivers(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = shipperService.getFrequentDrivers(userId);
            return RestResult.CreateResult(1,list);
        }
    }

    @RequestMapping(value = "getFrequentAddresses",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getFrequentAddresses(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = shipperService.getFrequentAddresses(userId);
            return RestResult.CreateResult(1,list);
        }
    }
    @RequestMapping(value = "addFrequentDriver",method = RequestMethod.POST)
    @ResponseBody
    public RestResult addFrequentDriver(@ModelAttribute("UserIdInfoWithAuth")UserIdInfoWithAuth userIdInfoWithAuth){
        int userId = userService.hasLogin(userIdInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            return null;
        }
    }

    @RequestMapping(value = "searchDriver", method = RequestMethod.POST)
    @ResponseBody
    public RestResult searchDriver(@ModelAttribute("SearchInfoWithAuth")SearchInfoWithAuth searchInfoWithAuth){
        int userId = userService.hasLogin(searchInfoWithAuth.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = shipperService.searchDriversByPhoneNum(searchInfoWithAuth.getPhoneNum());
            return RestResult.CreateResult(1,list);
        }
    }

    @RequestMapping(value = "addFrequentAddress", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addFrequentAddress(@ModelAttribute("AddressInfo")AddressInfo addressInfo){
        int userId = userService.hasLogin(addressInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(shipperService.addFrequentAddress(userId,addressInfo)){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "placeOrder", method = RequestMethod.POST)
        @ResponseBody
    public RestResult placeOrder(@ModelAttribute("OrderInfo")OrderInfo orderInfo){
//        return RestResult.CreateResult(1,orderInfo);
        int userId = userService.hasLogin(orderInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
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
    }

    @RequestMapping(value = "evaluateOrder", method = RequestMethod.POST)
        @ResponseBody
    public  RestResult evaluateOrder(@ModelAttribute("EvaluateInfo")EvaluateInfo evaluateInfo){
        int userId = userService.hasLogin(evaluateInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(shipperService.evaluateOrder(userId,evaluateInfo.getId(),evaluateInfo.getEvaluatePoint(),evaluateInfo.getEvaluate())){
                return RestResult.CreateResult(1);
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "splitOrder",method = RequestMethod.POST)
    @ResponseBody
    public RestResult splitOrder(@ModelAttribute("OrderInfo")OrderInfo orderInfo){
        int userId = userService.hasLogin(orderInfo.getToken());
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            if(orderService.splitOrder(userId,orderInfo)){
                return RestResult.CreateResult(1,"拆单成功!");
            }else{
                return RestResult.CreateResult(0,"操作失败!");
            }
        }
    }

    @RequestMapping(value = "showMyOrderList", method = RequestMethod.POST)
        @ResponseBody
    public RestResult showMyOrderList(@ModelAttribute("OrderListType")OrderListType orderListType){
        int userId = userService.hasLogin(orderListType.getToken());
//        userId = 1;
        if(userId == 0){
            return RestResult.CreateResult(0,"尚未登录!");
        }else{
            List list = orderService.getMyOrderList(userId,orderListType.getType());
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
