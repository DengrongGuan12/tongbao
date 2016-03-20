package dao;

import model.Order;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by cg on 2015/12/31.
 */
public interface OrderDao {
    public boolean createOrder(Order order);
    public List<Object> showShippersOrders(int userId);
    public List<Object> showDriverOrders(int userId);
    public Order showOrderDetail(int orderId);
    public boolean updateOrder(Order order);
    public boolean deleteOrder(int orderId);
    public Order getOrderByShipperIdAndBuildTime(int shipperId,Timestamp buildTime);
    public int getTotalOrderNum();
    public List getRecentOrders(int num);
    public List getAllOrders();
    public List getAllNoGrabOrder(String fromAdd,String toAdd);
    public List getAllAutoFinishOrder();
}
