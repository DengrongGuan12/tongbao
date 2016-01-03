package service.impl;

import org.springframework.stereotype.Service;
import pojo.OrderInfo;
import service.OrderService;

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
    返回0表示失败,1表示成功，2表示取消了正在进行的订单并将订单状态设为正在取消中
     */
    public int cancelOrder(int userId, int orderId) {
        return 0;
    }
}
