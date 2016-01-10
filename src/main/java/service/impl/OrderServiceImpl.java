package service.impl;

import dao.OrderDao;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.OrderInfo;
import service.OrderService;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    /*
    创建订单时要考虑是否有匹配的司机，如果成功返回1，找不到匹配的司机返回2，其他失败(如用户不是货主就没有权限创建)返回0
     */
    public int createOrder(int userId, OrderInfo orderInfo) {
        Order orderTemp=new Order();
        orderTemp.setShipperId(userId);
        orderTemp.setBuildTime(new Timestamp(System.currentTimeMillis()));
//        orderTemp.setLoadTime(orderInfo.getLoadTime());
        orderTemp.setAddressFrom(orderInfo.getAddressFrom());
        orderTemp.setAddressTo(orderInfo.getAddressTo());
//        orderTemp.setGoodsType(orderInfo.getGoodsType());
//        orderTemp.setGoodsWeight(orderInfo.getGoodsWeight());
//        orderTemp.setGoodsSize(orderInfo.getGoodsSize());
//        orderTemp.setTruckType(orderInfo.getTruckType());


        return 1;
    }


    /*
    要判断一下这个订单是不是该用户发布的以及该订单的状态是否允许删除
     */
    public boolean deleteOrder(int userId, int orderId) {
        return false;
    }

    /*
    返回0表示失败,1表示成功，2表示取消了正在进行的订单并将订单状态设为正在取消中,司机和货主都调用这个方法,根据userId对应的type区分,如果是司机则直接取消不返回2
     */
    public int cancelOrder(int userId, int orderId) {
        return 0;
    }

    public boolean finishOrder(int userId, int orderId) {
        return false;
    }

    public boolean grabOrder(int userId, int orderId) {
        return false;
    }

    public List getAllNoGrabOrder() {
        List list = new ArrayList();
        vo.Order order = new vo.Order();
        order.setAddressFrom("汉口路22号");
        order.setAddressTo("汉口路24号");
        order.setId(1);
        order.setMoney(23);
        order.setTime("2015-11-11 00:00:00");
        list.add(order);
        return list;
    }

    public vo.Order getOrderDetail(int orderId) {
        vo.Order order = new vo.Order();
        order.setId(orderId);
        order.setTime("2015-11-11 00:00:00");
        order.setMoney(23);
        order.setAddressTo("sdfsdfsdf");
        order.setAddressFrom("dsfdfgdfg");
        return order;
    }

    /*
    根据订单信息进行拆单
     */
    public boolean splitOrder(int userId, OrderInfo orderInfo) {
        return false;
    }

    /*
    先根据userId 判断用户类型，
    如果是货主,type:0表示尚未开始,1表示正在进行,2表示已经完成,3表示被取消
    如果是司机,type:1表示正在进行,2表示已经完成
     */
    public List getMyOrderList(int userId, int type) {
        return null;
    }
}
