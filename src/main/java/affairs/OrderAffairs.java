package affairs;

import model.Order;

/**
 * Created by cg on 2016/3/9.
 */
public interface OrderAffairs {
    public int saveOrderAffairs(Order order,String [] tTypes);
    public boolean deleteOrderAffairs(Order order);
    public int cancelOrderAffairs(Order order);
    public boolean finishOrderAffairs(Order order);
}
