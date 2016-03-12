package dao.impl;

import dao.OrderDao;
import model.Order;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao{

    public boolean deleteOrder(int orderId) {
        try {
            super.delete(Order.class,orderId);
            return true;
        }catch(Exception e){
            return false;
        }

    }


    public List getAllOrders() {
        return super.getAllList(Order.class);
    }

    public List getRecentOrders(int num) {
        Session session=getSession();
        String strSQL="from orders as o order by buildTime desc";
        Query query = session.createQuery(strSQL);
        query.setFirstResult(0);
        query.setMaxResults(num);
        return query.list();

    }

    public int getTotalOrderNum() {
        int count = ((Long)getSession().createQuery("select count(*) from orders").uniqueResult()).intValue();
        return count;
    }

    public Order getOrderByShipperIdAndBuildTime(int shipperId, Timestamp buildTime) {
//        String hql = "from " + Order.class.getName() + " where " + "shipperId = " + shipperId +" and "+" buildTime = "+buildTime ;
        String hql = "from " + Order.class.getName() + " where " + "shipperId = " + shipperId + "order by id desc";
        Session session = getSession();
        return (Order)session.createQuery(hql).list().get(0);

    }

    public boolean createOrder(Order order) {
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
            super.update(order);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public List getAllNoGrabOrder(String fromAdd,String toAdd){
        try{
            Session session=getSession();
            String strSQL="from orders as o where o.addressFrom like :fromAdd and o.addressTo like :toAdd and o.state = 0";
            Query query = session.createQuery(strSQL);
            query.setString("fromAdd","%"+fromAdd+"%");
            query.setString("toAdd","%"+toAdd+"%");
            return query.list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
