package affairs.Impl;

import affairs.OrderAffairs;
import model.Account;
import model.Order;
import model.OrderTruckType;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import service.UserService;
import service.impl.UserServiceIml;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by cg on 2016/3/8.
 */
@Repository
public class OrderAffairsImpl  implements OrderAffairs{
    @Autowired
    UserService userService;
        //保存使用事务办法，包含订单，订单用车类型，账单，更新用户账户
        public int saveOrderAffairs(Order order,String [] tTypes){
            Session session = HibernateUtil.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(order);
                Byte payType = order.getPayType();
                if(payType.equals(0)){
                    Account account = new Account();
                    account.setOrderId(order.getId());
                    account.setBuildTime(order.getBuildTime());
                    account.setUserId(order.getShipperId());
                    account.setMoney(order.getPrice());
                    account.setType(new Byte("2"));
                    session.save(account);
                    //扣钱
                    User user = (User) session.load(User.class,order.getShipperId());
                    double moneyNow = user.getMoney()-order.getPrice();
                    user.setMoney(moneyNow);
                    session.update(user);
                }
                for(int i=0;i<tTypes.length;i++){
                    OrderTruckType orderTruckType = new OrderTruckType();
                    orderTruckType.setTruckType(new Byte(tTypes[i]));
                    orderTruckType.setOrderId(order.getId());
                    session.save(orderTruckType);
                }
                tx.commit();
                return order.getId();
            }catch (Exception e){
                if(tx!=null) tx.rollback();
                e.printStackTrace();
                return -1;
            }finally {
                HibernateUtil.closeSession();
            }


        }

    public boolean finishOrderAffairs(Order order) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(order);
            Account account = new Account();
            account.setOrderId(order.getId());
            account.setBuildTime(new Timestamp(System.currentTimeMillis()));
            account.setUserId(order.getDriverId());
            account.setMoney(order.getPrice());
            account.setType(new Byte("4"));//表示到账账单
            session.save(account);
            User user = (User) session.load(User.class,order.getDriverId());
            double moneyNow = user.getMoney()+order.getPrice();
            user.setMoney(moneyNow);
            session.update(user);//到账
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    public int cancelOrderAffairs(Order order) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(order);
            Account account = new Account();
            account.setOrderId(order.getId());
            account.setBuildTime(new Timestamp(System.currentTimeMillis()));
            account.setUserId(order.getShipperId());
            account.setMoney(order.getPrice());
            account.setType(new Byte("3"));//表示退款账单
            session.save(account);
            User user = (User) session.load(User.class,order.getShipperId());
            double moneyNow = user.getMoney()+order.getPrice();
            user.setMoney(moneyNow);
            session.update(user);//退钱
            tx.commit();
            return 1;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
            return 0;
        }finally {
            HibernateUtil.closeSession();
        }

    }

    //线上支付且订单尚未被抢,使用事务办法，包含退款账单，把钱退回用户账号
    public boolean deleteOrderAffairs(Order order) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(order);
            Account account = new Account();
            account.setOrderId(order.getId());
            account.setBuildTime(new Timestamp(System.currentTimeMillis()));
            account.setUserId(order.getShipperId());
            account.setMoney(order.getPrice());
            account.setType(new Byte("3"));//表示退款账单
            session.save(account);
            User user = (User) session.load(User.class,order.getShipperId());
            double moneyNow = user.getMoney()+order.getPrice();
            user.setMoney(moneyNow);
            session.update(user);//退钱
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public boolean autoFinishTimeOutAffairs(List<Order> orders) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(Order order : orders) {
                order.setState(new Byte("2"));
                session.update(order);
                //表示在线支付
                if(order.getPayType().equals(new Byte("0"))){
                    Account account = new Account();
                    account.setOrderId(order.getId());
                    account.setBuildTime(new Timestamp(System.currentTimeMillis()));
                    account.setUserId(order.getDriverId());
                    account.setMoney(order.getPrice());
                    account.setType(new Byte("3"));//表示退款账单
                    session.save(account);
                    User user = (User) session.load(User.class, order.getShipperId());
                    double moneyNow = user.getMoney() + order.getPrice();
                    user.setMoney(moneyNow);
                    session.update(user);//到账
                    //TODO: 16/5/22在线支付中，未完成订单退款给货主，给货主发送提示消息
//                    Map<String,String> extras = new HashMap<String, String>();
//                    extras.put("type", UserServiceIml.order_finished+"");
//                    extras.put("id",order.getId()+"");
//                    userService.push(order.getDriverId()+"","订单结束！","该订单被已被货主结束，核对付款的金额!",extras,UserServiceIml.userType_driver);
                }

            }
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    public boolean autoFinishOrderAffairs(List<Order> orders) {

        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for(Order order : orders) {
                order.setState(new Byte("2"));
                session.update(order);
                //表示在线支付
                if(order.getPayType().equals(new Byte("0"))){
                    Account account = new Account();
                    account.setOrderId(order.getId());
                    account.setBuildTime(new Timestamp(System.currentTimeMillis()));
                    account.setUserId(order.getDriverId());
                    account.setMoney(order.getPrice());
                    account.setType(new Byte("4"));//表示到账账单
                    session.save(account);
                    User user = (User) session.load(User.class, order.getDriverId());
                    double moneyNow = user.getMoney() + order.getPrice();
                    user.setMoney(moneyNow);
                    session.update(user);//到账
                    Map<String,String> extras = new HashMap<String, String>();
                    extras.put("type", UserServiceIml.order_finished+"");
                    extras.put("id",order.getId()+"");
                    userService.push(order.getDriverId()+"","订单结束！","该订单被已被货主结束，核对付款的金额!",extras,UserServiceIml.userType_driver);
                }

            }
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    public List<Order> getAllAutoFinishOrders() {
        //7天之前
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,1970);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date fromDate = calendar.getTime();
        Date now = new Date();
        Date sevenDaysAgo = new Date(now.getTime() -(7L*24L*60L*60L*1000L));
        Session sess = HibernateUtil.getSession();
        Criteria criteria = sess.createCriteria(Order.class);
        criteria.add(Restrictions.between("loadTime", fromDate, sevenDaysAgo));
        criteria.add(Restrictions.eq("state",new Byte("1")));
        try{
            return criteria.list();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            HibernateUtil.closeSession();
        }
    }


    public List<Order> getAllLoadTimeOutOrders() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,1970);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date fromDate = calendar.getTime();
        Date now = new Date();
        Date nowTime = new Date(now.getTime());
        Session sess = HibernateUtil.getSession();
        Criteria criteria = sess.createCriteria(Order.class);
        criteria.add(Restrictions.between("loadTime", fromDate, nowTime));
        criteria.add(Restrictions.eq("state",new Byte("0")));
        try{
            return criteria.list();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            HibernateUtil.closeSession();
        }
    }
}
