package service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.OrderDao;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.OrderInfo;
import service.OrderService;
import vo.OrderDetail;
import vo.OrderSimple;


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
        //注意orderInfo 中的truckTypes是string类型的，需要转成json数组类型
        String truckTypestr = orderInfo.getTruckTypes();
        JSONArray truckTypes = JSON.parseArray(truckTypestr);
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
    只要是正在进行的都不允许删除
     */
    public boolean deleteOrder(int userId, int orderId) {
        return false;
    }

    /*
    返回0表示失败,1表示成功，2表示取消了正在进行的订单并将订单状态设为正在取消中,司机和货主都调用这个方法,根据userId对应的type区分,
    如果是司机则直接取消不返回2
    如果货主想取消正在进行的订单，需要给司机发送推送通知
     */
    public int cancelOrder(int userId, int orderId) {
        return 0;
    }

    /*
    要根据userId判断该订单是否是该user发出的
     */
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

    /*
    根据userId 获取用户类型,如果是货主,
    OrderDetail 中的state表示 0:尚未被抢1:已经被抢，正在进行2:进行中取消，正在等待司机确认3:已经取消4:已经完成
    如果是司机,OrderDetail中的state表示 0:尚未被抢1:已经被抢，正在进行2:进行中取消，正在等待司机确认4:已经完成
    */
    public OrderDetail getOrderDetail(int userId, int orderId) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setAddressFrom("dsfsdf");
        orderDetail.setAddressTo("dsfsdf");
        orderDetail.setFromContactName("sdfsdf");
        orderDetail.setFromContactPhone("1231234");
        orderDetail.setMoney(12.3);
        orderDetail.setLoadTime("2015-11-02 11:00:00");
        orderDetail.setToContactName("asdasd");
        orderDetail.setToContactPhone("121212");
        orderDetail.setTime("2015-11-11 00:00:00");
        List truckTypes = new ArrayList();
        truckTypes.add("中型货车");
        truckTypes.add("小型面包车");
        orderDetail.setTruckTypes(truckTypes);
        orderDetail.setState(0);
        return orderDetail;
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
        List list = new ArrayList();
        OrderSimple orderSimple = new OrderSimple();
        orderSimple.setId(1);
        orderSimple.setAddressFrom("dsfsdf");
        orderSimple.setAddressTo("dsfsdf");
        orderSimple.setFromContactName("sdfsdf");
        orderSimple.setFromContactPhone("1231234");
        orderSimple.setMoney(12.3);
        orderSimple.setLoadTime("2015-11-02 11:00:00");
        orderSimple.setToContactName("asdasd");
        orderSimple.setToContactPhone("121212");
        orderSimple.setTime("2015-11-11 00:00:00");
        List truckTypes = new ArrayList();
        truckTypes.add("中型货车");
        truckTypes.add("小型面包车");
        orderSimple.setTruckTypes(truckTypes);
        list.add(orderSimple);
        return list;
    }
}
