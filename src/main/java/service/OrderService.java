package service;

import pojo.OrderFilterInfo;
import pojo.OrderInfo;
import vo.OrderDetail;

import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
public interface OrderService {
    public int createOrder(int userId, OrderInfo orderInfo);
    public boolean deleteOrder(int userId, int orderId);
    public int cancelOrder(int userId, int orderId);
    public boolean finishOrder(int userId, int orderId);
    public boolean grabOrder(int userId, int orderId);
    public List getAllNoGrabOrder(int userId,OrderFilterInfo orderFilterInfo);
    public OrderDetail getOrderDetail(int userId, int orderId);
    public boolean splitOrder(int userId, OrderInfo orderInfo);
    public List getMyOrderList(int userId, int type);
}
