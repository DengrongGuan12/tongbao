package service.impl;

import org.springframework.stereotype.Service;
import pojo.OrderInfo;
import service.OrderService;
import vo.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class OrderServiceImpl implements OrderService {
    public boolean createOrder(int userId, OrderInfo orderInfo) {
        return false;
    }

    /*
    0表示尚未开始(只有货主有),1表示正在进行,2表示已经完成,3表示被取消
     */
    public List getOrderList(int userId, int type) {
        List list = new ArrayList();
        list.add("sddfdf");
        list.add("dsfsdfsdf");
        return list;
    }

    /*
    要判断一下这个订单是不是该用户发布的以及该订单的状态是否允许删除
     */
    public boolean deleteOrder(int userId, int orderId) {
        return false;
    }

    /*
    返回0表示失败,1表示成功，2表示取消了正在进行的订单并将订单状态设为正在取消中,司机和货主都调用这个方法,根据userId对应的type区分,如果是司机则直接取消不返回2
     */
    public int cancelOrder(int userId, int orderId) {
        return 0;
    }

    public boolean finishOrder(int userId, int orderId) {
        return false;
    }

    public boolean grabOrder(int userId, int orderId) {
        return false;
    }

    public List getAllNoGrabOrder() {
        List list = new ArrayList();
        Order order = new Order();
        order.setAddressFrom("汉口路22号");
        order.setAddressTo("汉口路24号");
        order.setId(1);
        order.setMoney(23);
        order.setTime("2015-11-11 00:00:00");
        list.add(order);
        return list;
    }

    public Order getOrderDetail(int orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setTime("2015-11-11 00:00:00");
        order.setMoney(23);
        order.setAddressTo("sdfsdfsdf");
        order.setAddressFrom("dsfdfgdfg");
        return order;
    }
}
