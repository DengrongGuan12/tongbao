package dao;

import model.Order;

import java.util.List;

/**
 * Created by cg on 2015/12/31.
 */
public interface OrderDao {
    public boolean placeOrder(Order order);
    public List<Object> showShippersOrders(int userId);
    public List<Object> showDriverOrders(int userId);
    public Order showOrderDetail(int orderId);
    public boolean updateOrder(Order order);
}
