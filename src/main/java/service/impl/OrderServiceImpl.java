package service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.Driver_auth_Dao;
import dao.OrderDao;
import dao.OrderTruckTypeDao;
import dao.Truck_type_Dao;
import manager.UserManager;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.OrderFilterInfo;
import pojo.OrderInfo;
import service.OrderService;
import vo.OrderDetail;
import vo.OrderSimple;
import vo.TruckType;


import java.sql.Timestamp;
import java.util.*;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class OrderServiceImpl implements OrderService {
    UserManager userManager = UserManager.getInstance();
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderTruckTypeDao orderTruckTypeDao;
    @Autowired
    Truck_type_Dao truckTypeDao;
    @Autowired
    Driver_auth_Dao driver_auth_dao;

    /*
    创建订单时要考虑是否有匹配的司机，如果成功返回1，找不到匹配的司机返回2，其他失败(如用户不是货主就没有权限创建)返回0
     */
    public int createOrder(int userId, OrderInfo orderInfo) {
        //注意orderInfo 中的truckTypes是string类型的，需要转成json数组类型
        int userType = userManager.getUserType(userId);
        String truckTypeStr = orderInfo.getTruckTypes();
        JSONArray truckTypes = JSON.parseArray(truckTypeStr);
        String [] tTypes=new String[truckTypes.size()];
        for(int i=0;i<truckTypes.size();i++){
            tTypes[i]=truckTypes.get(i)+"";
        }
        if(userType==1){
            return 0;
        }else if(userType==0){
            if(truckTypes.size()==1){
                this.saveOrder(userId,orderInfo,tTypes);
                return 1;
            }
            MatchDriverModel m = this.maxMatchDriver(tTypes);
            //找到匹配司机
            if(tTypes.length==m.carType.size()){
                this.saveOrder(userId,orderInfo,tTypes);
                return 1;
            }else{
                return 2;
            }
        }
        return 0;
    }
    private boolean saveOrder(int userId, OrderInfo orderInfo,String [] tTypes){
        Order order=new Order();
        order.setBuildTime(new Timestamp(System.currentTimeMillis()));
        order.setShipperId(userId);
        order.setLoadTime(orderInfo.getLoadTime());
        order.setAddressFrom(orderInfo.getAddressFrom());
        order.setAddressTo(orderInfo.getAddressTo());
        order.setFrom_contact_name(orderInfo.getFromContactName());
        order.setFrom_contact_phone(orderInfo.getFromContactPhone());
        order.setTo_contact_name(orderInfo.getToContactName());
        order.setTo_contact_phone(orderInfo.getToContactPhone());
        order.setGoodsType(orderInfo.getGoodsType());
        order.setGoodsWeight(orderInfo.getGoodsWeight());
        order.setGoodsSize(orderInfo.getGoodsSize());
        order.setPayType(orderInfo.getPayType());
        order.setPrice(orderInfo.getPrice());
        order.setRemark(orderInfo.getRemark());
        order.setState(new Byte("0"));
        orderDao.createOrder(order);
        Order orderTemp = orderDao.getOrderByShipperIdAndBuildTime(userId,order.getBuildTime());
        for(int i=0;i<tTypes.length;i++){
            OrderTruckType orderTruckType = new OrderTruckType();
            orderTruckType.setTruckType(new Byte(tTypes[i]));
            orderTruckType.setOrderId(orderTemp.getId());
            orderTruckTypeDao.addTruckTypes(orderTruckType);
        }

        return true;
    }

    private class MatchDriverModel{
        //driver_auth的id,type(车辆类型）
        Map<Integer,Byte>carType=new HashMap<Integer, Byte>();
    }
    //找出最大匹配

    /**
     * 找出最大匹配
     * @param types 需要匹配的汽车类型
     * @return 内部类，包含最多匹配的map
     */
    private MatchDriverModel maxMatchDriver(String [] types){
        //shipperId和内部类
        Map<Integer,MatchDriverModel>matchMap = new HashMap<Integer,MatchDriverModel>();
        for (int i=0;i<types.length;i++){
            List listTemp = driver_auth_dao.getDriverByTruckType(types[i]);
            for(int j=0;j<listTemp.size();j++){
                Driver_auth driverTemp = (Driver_auth)listTemp.get(j);
                if(!matchMap.containsKey(driverTemp.getUserId())){
                    MatchDriverModel md= new MatchDriverModel();
                    md.carType.put(driverTemp.getId(),driverTemp.getType());
                    matchMap.put(driverTemp.getUserId(),md);
                }else {
                    MatchDriverModel md = matchMap.get(driverTemp.getUserId());
                    if(!md.carType.containsKey(driverTemp.getId())){
                        md.carType.put(driverTemp.getId(),new Byte(driverTemp.getType()));
                    }
                }
            }
        }
        Iterator iterator = matchMap.keySet().iterator();
        int max=0;
        MatchDriverModel mdd=new MatchDriverModel();
        while (iterator.hasNext()){
            MatchDriverModel driverModel=(MatchDriverModel)matchMap.get(iterator.next());
            int count=driverModel.carType.size();
            if(max<count){
                max=count;
                mdd=driverModel;
            }
        }

        return mdd;
    }


    /*
    要判断一下这个订单是不是该用户发布的以及该订单的状态是否允许删除
    只要是正在进行的都不允许删除
    设置删除状态:
    5:被货主删除
    6:被司机删除
    7:被司机和货主都删除

     */
    public boolean deleteOrder(int userId, int orderId) {

        int userType=userManager.getUserType(userId);
        Order order = orderDao.showOrderDetail(orderId);
        //1:正在进行
        if(order.getState()!=1){
            //货主删除订单
            if(userType==0){
                if(order.getShipperId()==userId){
                    if(order.getState().equals(new Byte("6"))){
                        order.setState(new Byte("7"));
                    }else {
                        order.setState(new Byte("5"));
                    }


                }else {
                    return false;
                }
                //司机删除订单
            }else if(userType==1){
                if(order.getDriverId()==userId){
                    if(order.getState().equals(new Byte("5"))){
                        order.setState(new Byte("7"));
                    }else {
                        order.setState(new Byte("6"));
                    }
                }else{
                    return false;
                }
            }

            orderDao.updateOrder(order);
            return true;
        }

        return false;
    }

    /*
    返回0表示失败,1表示成功，2表示取消了正在进行的订单并将订单状态设为正在取消中,司机和货主都调用这个方法,根据userId对应的type区分,
    如果是司机则直接取消不返回2
    如果货主想取消正在进行的订单，需要给司机发送推送通知
     */
    public int cancelOrder(int userId, int orderId) {
        int userType = userManager.getUserType(userId);
        Order order = orderDao.showOrderDetail(orderId);
        int orderState = order.getState();
        //订单已经被删除
        if(orderState==5||orderState==6||orderState==7){
            return 0;

        }else {
            //货主
            if(userType==0){
                order.setState(new Byte("4"));
                orderDao.updateOrder(order);
                return 2;
            }else if(userType==1){
                order.setDriverId(0);
                order.setState(new Byte("3"));
                orderDao.updateOrder(order);
                return 1;
            }
        }
        return 0;

    }

    /*
    要根据userId判断该订单是否是该user发出的
     */
    public boolean finishOrder(int userId, int orderId) {
        Order order = orderDao.showOrderDetail(orderId);
        int orderState = order.getState();
        if(order.getShipperId()==userId){
            if(orderState==5||orderState==7){
                return false;
            }else{
                order.setState(new Byte("2"));
                orderDao.updateOrder(order);
                return true;
            }
        }else {
            return false;
        }

    }

    /*
    需要判断该用户是否司机以及该订单是否是处在尚未被抢的状态
     */
    public boolean grabOrder(int userId, int orderId) {
        int userType = userManager.getUserType(userId);
        Order order = orderDao.showOrderDetail(orderId);
        if(userType==1&&order.getState()==0){
            order.setDriverId(orderId);
            order.setState(new Byte("1"));
            orderDao.updateOrder(order);
            return true;
        }
        return false;
    }

    /*
    只有司机能获取所有未被强到订单
    根据起点终点过滤订单
    是根据名字还是根据经纬度过滤待定，需要看一下百度地图
     */
    public List getAllNoGrabOrder(int userId, OrderFilterInfo orderFilterInfo) {
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

    /*
    根据userId 获取用户类型,如果是货主,
    OrderDetail 中的state表示 0:尚未被抢1:已经被抢，正在进行2:进行中取消，正在等待司机确认3:已经取消4:已经完成
    如果是司机,OrderDetail中的state表示 0:尚未被抢1:已经被抢，正在进行2:进行中取消，正在等待司机确认4:已经完成
    */
    public OrderDetail getOrderDetail(int userId, int orderId) {
        Order order = orderDao.showOrderDetail(orderId);
        List truckTypes = orderTruckTypeDao.getTruckTypesByOrderId(orderId);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(order.getId());
        orderDetail.setAddressFrom(order.getAddressFrom());
        orderDetail.setAddressTo(order.getAddressTo());
        orderDetail.setFromContactName(order.getFrom_contact_name());
        orderDetail.setFromContactPhone(order.getFrom_contact_phone());
        orderDetail.setMoney(order.getPrice());
        orderDetail.setLoadTime(order.getLoadTime());
        orderDetail.setToContactName(order.getTo_contact_name());
        orderDetail.setToContactPhone(order.getTo_contact_phone());
        orderDetail.setTime(order.getBuildTime()+"");
        orderDetail.setTruckTypes(truckTypes);
        orderDetail.setState(order.getState());
        return orderDetail;
    }

    /*
    根据订单信息进行拆单
     */
    public boolean splitOrder(int userId, OrderInfo orderInfo) {
        String truckTypeStr = orderInfo.getTruckTypes();
        JSONArray truckTypes = JSON.parseArray(truckTypeStr);
        String [] tTypes=new String[truckTypes.size()];
        for(int i=0;i<truckTypes.size();i++){
            tTypes[i]=truckTypes.get(i)+"";
        }
        //取已经选择过的司机，防止二次选择
//        Set set = new HashSet();
        while (tTypes.length>0){
            if(tTypes.length==1){
                this.saveOrder(userId,orderInfo,tTypes);
                break;
            }
            MatchDriverModel m = this.maxMatchDriver(tTypes);
            Iterator iterator=m.carType.keySet().iterator();

            String [] tT = new String[m.carType.size()];
            int index=0;
            while (iterator.hasNext()){
                int driverAuthId = Integer.parseInt(iterator.next() + "");
                tT[index]=m.carType.get(driverAuthId)+"";
                index++;
//                if(!set.contains(driverAuthId)){
//                    set.add(driverAuthId);
//
//                }

            }
            this.saveOrder(userId,orderInfo,tT);
            for (int i=0;i<tT.length;i++){
                for(int j=0;j<tTypes.length;j++){
                    if(tT[i].equals(tTypes[j])){
                        tTypes[j]=null;
                        break;
                    }
                }
            }
            String temp[] = new String[tTypes.length-tT.length];
            int ti=0;
            for(int i=0;i<tTypes.length;i++){
                if(tTypes[i]!=null){
                    temp[ti]=tTypes[i];
                    ti++;
                }
            }
            tTypes=temp;
        }
        return true;
    }

    /*
    先根据userId 判断用户类型，
    如果是货主,type:0表示尚未开始,1表示正在进行,2表示已经完成,3表示被取消
    如果是司机,type:1表示正在进行,2表示已经完成
    by DengrongGuan
     */
    public List getMyOrderList(int userId, int type) {
        int userType = userManager.getUserType(userId);
        if(userType == 1){
            //司机
            if(type == 1 || type == 2){
                List list = orderDao.showDriverOrders(userId);
                List resultList = new ArrayList();
                if(type == 1){
                    for(Object o:list){
                        Order order = (Order)o;
                        if (order.getState() == 1 || order.getState() == 4){

                            resultList.add(genOrderSimpleFromOrder(order));
                        }
                    }
                }else{
                    for(Object o: list){
                        Order order = (Order)o;
                        if(order.getState() == type){
                            resultList.add(genOrderSimpleFromOrder(order));
                        }
                    }
                }
                return resultList;
            }else{
                return null;
            }
        }else{
            //货主
            if(type == 0 || type == 1 || type == 2 || type == 3){
                List list = orderDao.showShippersOrders(userId);
                List resultList = new ArrayList();
                if(type == 1){
                    for(Object o:list){
                        Order order = (Order)o;
                        if (order.getState() == 1 || order.getState() == 4){

                            resultList.add(genOrderSimpleFromOrder(order));
                        }
                    }
                }else{
                    for(Object o: list){
                        Order order = (Order)o;
                        if(order.getState() == type){
                            resultList.add(genOrderSimpleFromOrder(order));
                        }
                    }
                }
                return resultList;
            }else{
                return null;
            }
        }
    }

    //获取总的订单数量
    public int getTotalOrderNum() {
        return orderDao.getTotalOrderNum();
    }

    /*
    获取最近的订单列表,按时间倒序排序, 使用OrderSimple, 需要set的值有:
    id,money,time,state,addressFrom, addressTo,
    stateStr: 状态描述,根据state的值,这样就不需要前台去解析了,对应关系如下
    0:待处理
    1:进行中
    2:已完成
    3:被取消
    4:待取消
    5:货主删除
    6:司机删除
    7:删除
     */

    public List getRecentOrders(int num) {
        return null;
    }

    /*
    根据order 生成orderSimple
     */
    private OrderSimple genOrderSimpleFromOrder(Order order){
        OrderSimple orderSimple = new OrderSimple();
        orderSimple.setId(order.getId());
        orderSimple.setTime(order.getBuildTime()+"");
        orderSimple.setAddressFrom(order.getAddressFrom());
        orderSimple.setAddressTo(order.getAddressTo());
        orderSimple.setFromContactName(order.getFrom_contact_name());
        orderSimple.setFromContactPhone(order.getFrom_contact_phone());
        orderSimple.setToContactName(order.getTo_contact_name());
        orderSimple.setToContactPhone(order.getTo_contact_phone());
        orderSimple.setMoney(order.getPrice());
        orderSimple.setLoadTime(order.getLoadTime()+"");
        orderSimple.setState(order.getState());
        List truckNames = new ArrayList();
        List truckTypes = orderTruckTypeDao.getTruckTypesByOrderId(order.getId());
        for (Object object:truckTypes){
            OrderTruckType orderTruckType = (OrderTruckType)object;
            Trucks_type trucks_type = truckTypeDao.getTruckType(orderTruckType.getTruckType());
            truckNames.add(trucks_type.getName());
        }
        return orderSimple;
    }
}
