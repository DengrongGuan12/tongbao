package affairs;

import model.Order;

import java.util.List;

/**
 * Created by cg on 2016/3/9.
 */
public interface OrderAffairs {
    public int saveOrderAffairs(Order order,String [] tTypes);
    public boolean deleteOrderAffairs(Order order);
    public int cancelOrderAffairs(Order order);
    public boolean finishOrderAffairs(Order order);
    public boolean autoFinishOrderAffairs(List<Order> orders);
    public boolean autoFinishTimeOutAffairs(List<Order> orders);
    public List<Order> getAllAutoFinishOrders();
    public List<Order> getAllLoadTimeOutOrders();

}
