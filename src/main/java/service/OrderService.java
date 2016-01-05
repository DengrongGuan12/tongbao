package service;

import pojo.OrderInfo;
import vo.Order;

import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
public interface OrderService {
    public boolean createOrder(int userId, OrderInfo orderInfo);
    public List getOrderList(int userId, int type);
    public boolean deleteOrder(int userId, int orderId);
    public int cancelOrder(int userId, int orderId);
    public boolean finishOrder(int userId, int orderId);
    public boolean grabOrder(int userId, int orderId);
    public List getAllNoGrabOrder();
    public Order getOrderDetail(int orderId);
}
