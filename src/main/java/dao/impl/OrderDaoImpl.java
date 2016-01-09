package dao.impl;

import dao.OrderDao;
import model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao{

    public boolean placeOrder(Order order) {
        try {
            super.save(order);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public List<Object> showShippersOrders(int userId) {
        try{
            return super.getList(Order.class,"shipper_id",userId+"");
        }catch (Exception e){
            return null;
        }
    }

    public List<Object> showDriverOrders(int userId) {
        try{
            return super.getList(Order.class,"driver_id",userId+"");
        }catch (Exception e){
            return null;
        }
    }

    public Order showOrderDetail(int orderId) {
        try {
            return (Order)super.load(Order.class,orderId);
        }catch (Exception e){
            return null;
        }

    }
//
    public boolean updateOrder(Order order) {
        try {
//            Order orderTemp=(Order)super.load(Order.class,order.getOrderId());

        }catch (Exception e){
            return false;
        }
        return false;
    }
}
