package service.impl;

import affairs.OrderAffairs;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.*;
import manager.UserManager;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.OrderFilterInfo;
import pojo.OrderInfo;
import service.OrderService;
import service.UserService;
import vo.OrderDetail;
import vo.OrderSimple;


import java.sql.Timestamp;
import java.util.*;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class OrderServiceImpl implements OrderService {
    //起步距离
    private static final double BACE_DISTANCE= 5.0;
    UserManager userManager = UserManager.getInstance();
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderTruckTypeDao orderTruckTypeDao;
    @Autowired
    Truck_type_Dao truckTypeDao;
    @Autowired
    Driver_auth_Dao driver_auth_dao;
    @Autowired
    UserDao userDao;
    @Autowired
    Order_state_name_t_Dao order_state_name_t_dao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    UserService userService;
    @Autowired
    OrderAffairs orderAffairs;



    /*
    创建订单时要考虑是否有匹配的司机，如果成功返回1，找不到匹配的司机返回2，其他失败(如用户不是货主就没有权限创建)返回0
     */
    public int createOrder(int userId, OrderInfo orderInfo) {
        // TODO: 3/14/2016 下面的注释是加的四个属性(起始点的经纬度)
//        orderInfo.getAddressFromLat();
//        orderInfo.getAddressFromLng();
//        orderInfo.getAddressToLat();
//        orderInfo.getAddressToLng();
        Map <Byte,Trucks_type>truckTypeMap = truckTypeDao.getAllTruckTypeMap();
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
            //先根据总价格和用户账户中的钱判断是否够钱产生订单
            double price = 0;
            double distance = orderInfo.getDistance();
            for(int i =0;i<tTypes.length;i++){
                Trucks_type trucks_type = truckTypeMap.get(new Byte(tTypes[i]));
                price+=trucks_type.getBase_price();
                //当距离大于起步距离时
                if(distance>BACE_DISTANCE){
                    price+=(distance-BACE_DISTANCE)*trucks_type.getOver_price();
                }
            }
            User user = userDao.getUserById(userId);
            //钱不够返回0
            if(user.getMoney()<price||truckTypes.size()<1){
                return 0;
            }
            if(truckTypes.size()==1){
                if(!(this.saveOrder(userId,orderInfo,tTypes))){
                    return 0;
                }
                return 1;
            }
            MatchDriverModel m = this.maxMatchDriver(tTypes,new HashSet<Integer>());
            //找到匹配司机
            if(tTypes.length==m.carType.size()){
                if(!(this.saveOrder(userId,orderInfo,tTypes))){
                    return 0;
                }
                return 1;
            }else{
                return 2;
            }
        }
        return 0;
    }
    //保存订单，并根据tTypes和公里数计算价格。
    //设置在5公里内按起步价计算，超过5公里按车辆类型的公里单价继续计算
    private boolean saveOrder(int userId, OrderInfo orderInfo,String [] tTypes){
        Map <Byte,Trucks_type>truckTypeMap = truckTypeDao.getAllTruckTypeMap();
        Byte payType = orderInfo.getPayType();
        double distance = orderInfo.getDistance();
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
        order.setRemark(orderInfo.getRemark());
        order.setEvaluate_point(new Byte("0"));
        order.setEvaluate_content("");
        order.setState(new Byte("0"));
        double price = 0;
        for(int i =0;i<tTypes.length;i++){
            Trucks_type trucks_type = truckTypeMap.get(new Byte(tTypes[i]));
            price+=trucks_type.getBase_price();
            //当距离大于起步距离时
            if(distance>BACE_DISTANCE){
                price+=(distance-BACE_DISTANCE)*trucks_type.getOver_price();
            }
        }
        order.setPrice(price);
        return orderAffairs.saveOrderAffairs(order,tTypes);
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
    private MatchDriverModel maxMatchDriver(String [] types,Set<Integer>hadSelected){
        //shipperId和内部类
        Map<Integer,MatchDriverModel>matchMap = new HashMap<Integer,MatchDriverModel>();
        for (int i=0;i<types.length;i++){
            List listTemp = driver_auth_dao.getDriverByTruckType(types[i]);
            for(int j=0;j<listTemp.size();j++){
                Driver_auth driverTemp = (Driver_auth)listTemp.get(j);
                if(driverTemp.getAuthState().equals(new Byte("2"))){
                    if(!matchMap.containsKey(driverTemp.getUserId())){
                        MatchDriverModel md= new MatchDriverModel();
                        md.carType.put(driverTemp.getId(),driverTemp.getType());
                        matchMap.put(driverTemp.getUserId(),md);
                    }else {
                        MatchDriverModel md = matchMap.get(driverTemp.getUserId());
                        if(!md.carType.containsKey(driverTemp.getId())&&(!hadSelected.contains(driverTemp.getId()))){
                            md.carType.put(driverTemp.getId(),new Byte(driverTemp.getType()));
                        }
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
    0:尚未被抢
    1:已经被抢，正在进行
    2:表示已经完成
    3:表示被取消
    4:进行中取消，正在等待司机确认
    5:被货主删除
    6:被司机删除
    7:被司机和货主都删除

     */
    public boolean deleteOrder(int userId, int orderId) {

        int userType=userManager.getUserType(userId);
        Order order = orderDao.showOrderDetail(orderId);
        //1:正在进行和4进行中取消
        int orderState = order.getState();
        if(orderState!=1&&orderState!=4){
            //货主删除订单
            if(userType==0){
                if(order.getShipperId()==userId){
                    if(order.getState().equals(new Byte("6"))||order.getState().equals(new Byte("7"))){
                        order.setState(new Byte("7"));
                    }else {
                        order.setState(new Byte("5"));
                        //线上支付且订单尚未被抢,使用事务办法，包含退款账单
                        if(order.getPayType().equals(new Byte("0"))&&order.getState().equals(new Byte("0"))){
                            return orderAffairs.deleteOrderAffairs(order);
                        }
                    }

                }else {
                    return false;
                }
                //司机删除订单
            }else if(userType==1){
                if(order.getDriverId()==userId&&!(order.getState().equals(new Byte("0")))){
                    if(order.getState().equals(new Byte("5"))||order.getState().equals(new Byte("7"))){
                        order.setState(new Byte("7"));
                    }else {
                        order.setState(new Byte("6"));
                    }
                }else{
                    return false;
                }
            }else {
                return false;
            }

            return orderDao.updateOrder(order);
        }

        return false;
    }

    /*
    返回0表示失败,1表示成功，2表示取消了正在进行的订单并将订单状态设为正在取消中,司机和货主都调用这个方法,根据userId对应的type区分,
    如果是司机则直接取消不返回2
    如果货主想取消正在进行的订单，需要给司机发送推送通知
    0:尚未被抢
    1:已经被抢，正在进行
    2:表示已经完成
    3:表示被取消
    4:进行中取消，正在等待司机确认
    5:被货主删除
    6:被司机删除
    7:被司机和货主都删除
     */
    public int cancelOrder(int userId, int orderId) {
        int userType = userManager.getUserType(userId);
        Order order = orderDao.showOrderDetail(orderId);
        int orderState = order.getState();

        //订单已经被删除或已完成或已经取消
        if(orderState==2||orderState==3||orderState==5||orderState==6||orderState==7){
            return 0;

        }else {
            //货主
            if(userType==0){
                if(order.getShipperId()!=userId){
                    return 0;
                }
                //尚未被抢
                if(orderState==0){
                    order.setState(new Byte("3"));
                    //取消尚未被抢的在线支付订单
                    if(order.getPayType().equals(new Byte("0"))){
                        return orderAffairs.cancelOrderAffairs(order);
                    }
                    orderDao.updateOrder(order);
                    return 1;
                    //取消了正在进行的订单并将订单状态设为正在取消中

                }else if(orderState==1){
                    order.setState(new Byte("4"));
                    orderDao.updateOrder(order);
                    Map<String,String> extras = new HashMap<String, String>();
                    extras.put("type",UserServiceIml.order_cancel_request+"");
                    extras.put("id",orderId+"");
                    userService.push(order.getDriverId()+"","订单取消！","该订单被货主要求取消，请根据实际情况进行选择!",extras);
                    return 2;
                }else{
                    //状态为4，正在被取消中
                    return 0;
                }


            }else if(userType==1){
                if(order.getDriverId()!=userId||orderState==0){
                    return 0;
                }
                if(orderState==1){
                    //若正在进行中，直接取消
                    order.setState(new Byte("0"));
                    order.setDriverId(-1);
                    Map<String,String> extras = new HashMap<String, String>();
                    extras.put("type",UserServiceIml.order_canceled+"");
                    extras.put("id",orderId+"");
                    userService.push(order.getDriverId()+"","订单取消！","该订单已被司机取消，正在等待新的司机!",extras);
                }else{
                    //状态为4
                    order.setState(new Byte("3"));
                    Map<String,String> extras = new HashMap<String, String>();
                    extras.put("type",UserServiceIml.order_canceled+"");
                    extras.put("id",orderId+"");
                    userService.push(order.getDriverId()+"","订单取消！","该订单按您的要求已被司机取消!",extras);
                    if(order.getPayType().equals(new Byte("0"))){
                        return orderAffairs.cancelOrderAffairs(order);
                    }

                }

                orderDao.updateOrder(order);
                return 1;
            }
        }
        return 0;

    }

    /*
    要根据userId判断该订单是否是该user发出的
    只有在正在进行时才能被完成。
    只有货主有这个功能
     */
    public boolean finishOrder(int userId, int orderId) {
        Order order = orderDao.showOrderDetail(orderId);
        int orderState = order.getState();
        if(order.getShipperId()==userId&&orderState==1){

            order.setState(new Byte("2"));
            if(order.getPayType().equals(new Byte("0"))) {
                return orderAffairs.finishOrderAffairs(order);
            }
            orderDao.updateOrder(order);
            Map<String,String> extras = new HashMap<String, String>();
            extras.put("type",UserServiceIml.order_finished+"");
            extras.put("id",orderId+"");
            userService.push(order.getDriverId()+"","订单结束！","该订单被已被货主结束，核对付款的金额!",extras);
            return true;

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


            if(this.matchOrder(userId,orderId)){
                order.setDriverId(orderId);
                order.setState(new Byte("1"));
                orderDao.updateOrder(order);
                Map<String,String> extras = new HashMap<String, String>();
                extras.put("type",UserServiceIml.order_grabbed+"");
                extras.put("id",orderId+"");
                userService.push(order.getShipperId()+"","订单被抢","您的订单被司机抢到了，请尽快联系司机!",extras);
                return true;
            }
        }
        return false;
    }
    //判断司机是否有足够车辆进行抢单
    private boolean matchOrder(int driverId,int orderId){
        List allAuthTruck = driver_auth_dao.getAllAuthTrucksByDriverId(driverId);
        List allTruckType = orderTruckTypeDao.getTruckTypesByOrderId(orderId);
        Map <Byte,Integer> matchMap = new HashMap();
        for(int i = 0;i<allTruckType.size();i++){
            OrderTruckType orderTruckType = (OrderTruckType)allTruckType.get(i);
            Byte truckType = orderTruckType.getTruckType();
            if(!matchMap.containsKey(truckType)){
                matchMap.put(truckType,1);
            }else {
                int t = matchMap.get(truckType)+1;
                matchMap.put(truckType,t);
            }
        }
        for(int i = 0;i<allAuthTruck.size();i++){
            Driver_auth driver_auth = (Driver_auth) allAuthTruck.get(i);
            Byte truckType = driver_auth.getType();
            if(matchMap.containsKey(truckType)){
                int t = matchMap.get(truckType)-1;
                matchMap.put(truckType,t);
            }
        }
        Iterator iterator = matchMap.keySet().iterator();
        boolean match = true;
        while (iterator.hasNext()){
            int valueTemp = matchMap.get(iterator.next());
            if(valueTemp>0){
                match = false;
            }
        }
        return match;
    }

    /*
    只有司机能获取所有未被强到订单
    根据起点终点过滤订单
    是根据名字还是根据经纬度过滤待定，需要看一下百度地图
     */
    //TODO
    public List getAllNoGrabOrder(int userId, OrderFilterInfo orderFilterInfo) {
        int userType = userManager.getUserType(userId);
        if(userType==0){
            return null;
        }
        List list = new ArrayList();
        List listTemp = orderDao.getAllNoGrabOrder(orderFilterInfo.getFromAddress(),orderFilterInfo.getToAddress());
        for(int i = 0;i < listTemp.size();i++){
            Order order = (Order)listTemp.get(i);
            OrderSimple orderSimple = this.genOrderSimpleFromOrder(order);
            list.add(orderSimple);
        }

        return list;
    }

    /*
    根据userId 获取用户类型,如果是货主,
    OrderDetail 中的state表示 0:尚未被抢1:已经被抢，正在进行2:进行中取消，正在等待司机确认3:已经取消4:已经完成
    如果是司机,OrderDetail中的state表示 0:尚未被抢1:已经被抢，正在进行2:进行中取消，正在等待司机确认4:已经完成
    */
    public OrderDetail getOrderDetail(int userId, int orderId) {
        Map <Byte,String> map = order_state_name_t_dao.getAllOrderStateName();
        Map <Byte,Trucks_type> truckType = truckTypeDao.getAllTruckTypeMap();
        int userType = userManager.getUserType(userId);
        Order order = orderDao.showOrderDetail(orderId);
        if(userType==0){
            if(order.getShipperId()!=userId){
                return null;
            }
        }
        else if(userType==1){
            //对于司机来说，如果不是尚未被抢的状态，则需要抢到该订单才能查看详情
            if(!(order.getState().equals(new Byte("0")))){
                if(order.getDriverId()!=userId){
                    return null;
                }
            }
        }

        List orderTruckTypes = orderTruckTypeDao.getTruckTypesByOrderId(orderId);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(order.getId());
        orderDetail.setTime(order.getBuildTime().toString());
        orderDetail.setAddressFrom(order.getAddressFrom());
        orderDetail.setAddressTo(order.getAddressTo());
        orderDetail.setMoney(order.getPrice());
        orderDetail.setFromContactName(order.getFrom_contact_name());
        orderDetail.setFromContactPhone(order.getFrom_contact_phone());
        orderDetail.setLoadTime(order.getLoadTime());
        orderDetail.setState(order.getState());
        orderDetail.setToContactName(order.getTo_contact_name());
        orderDetail.setToContactPhone(order.getTo_contact_phone());

        List truckTypeName = new ArrayList();
        //货车类型
        for(int i = 0;i<orderTruckTypes.size();i++){
            OrderTruckType t = (OrderTruckType)orderTruckTypes.get(i);
            String truckName = truckType.get(t.getTruckType()).getName();
            truckTypeName.add(truckName);
        }
        orderDetail.setTruckTypes(truckTypeName);
        orderDetail.setStateStr(map.get(order.getState()));
        User shipper = userDao.getUserById(order.getShipperId());
        User driver = userDao.getUserById(order.getDriverId());
        orderDetail.setDriverPhoneNum(shipper.getPhone_number());
        orderDetail.setShipperPhoneNum(driver.getPhone_number());
        orderDetail.setGoodsWeight(order.getGoodsWeight());
        orderDetail.setGoodsType(order.getGoodsType());
        return orderDetail;
    }

    /*
    根据订单信息进行拆单
     */
    public boolean splitOrder(int userId, OrderInfo orderInfo) {
        // TODO: 3/14/2016  同createOrder 
//        orderInfo.getDistance() 获取公里数
        String truckTypeStr = orderInfo.getTruckTypes();
        JSONArray truckTypes = JSON.parseArray(truckTypeStr);
        String [] tTypes=new String[truckTypes.size()];
        for(int i=0;i<truckTypes.size();i++){
            tTypes[i]=truckTypes.get(i)+"";
        }
        //取已经选择过的司机，防止二次选择
        Set set = new HashSet();
        while (tTypes.length>0){
            if(tTypes.length==1){
                this.saveOrder(userId,orderInfo,tTypes);
                break;
            }
            MatchDriverModel m = this.maxMatchDriver(tTypes,set);
            Iterator iterator=m.carType.keySet().iterator();

            String [] tT = new String[m.carType.size()];
            int index=0;
            while (iterator.hasNext()){
                int driverAuthId = Integer.parseInt(iterator.next() + "");
                set.add(driverAuthId);
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
    7:已删除
     */

    public List getRecentOrders(int num) {
        Map <Byte,String>orderState = order_state_name_t_dao.getAllOrderStateName();
        List listTemp = orderDao.getRecentOrders(num);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            model.Order orderTemp = (model.Order)listTemp.get(i);
            vo.OrderSimple order = new vo.OrderSimple();
            order.setId(orderTemp.getId());
            order.setMoney(orderTemp.getPrice());
            order.setTime(orderTemp.getBuildTime().toString());
            order.setAddressFrom(orderTemp.getAddressFrom());
            order.setAddressTo(orderTemp.getAddressTo());
            Byte state = orderTemp.getState();
            order.setState(state);
            order.setStateStr(orderState.get(state));
            list.add(order);

        }
        return list;
    }

    //获取所有订单
    //使用orderDetail, 需要设置的值有id,addressFrom, addressTo, time, loadTime, driverPhoneNum, shipperPhoneNum,money,goodsType,goodsWeight,state
    public List getAllOrders() {
        List listTemp = orderDao.getAllOrders();
        List list = new ArrayList();
        for(int i = 0;i<listTemp.size();i++){
            Order order = (Order)listTemp.get(i);
            User driver = userDao.getUserById(order.getDriverId());
            User shipper =userDao.getUserById(order.getShipperId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(order.getId());
            orderDetail.setAddressFrom(order.getAddressFrom());
            orderDetail.setAddressTo(order.getAddressTo());
            orderDetail.setTime(order.getBuildTime().toString());
            orderDetail.setLoadTime(order.getLoadTime());
            orderDetail.setDriverPhoneNum(driver.getPhone_number());
            orderDetail.setShipperPhoneNum(shipper.getPhone_number());
            orderDetail.setMoney(order.getPrice());
            orderDetail.setGoodsType(order.getGoodsType());
            orderDetail.setGoodsWeight(order.getGoodsWeight());
            orderDetail.setState(order.getState());
            list.add(orderDetail);
        }
        return list;
    }

    //管理员删除订单，只能删除已经完成的或者被取消或者被司机货主删除的订单（即state只能为2，3，5，6，7），否则返回false
    public boolean deleteOrder(int id) {
        Order order = orderDao.showOrderDetail(id);
        int orderState = order.getState();
        if(orderState==2||orderState==3||orderState==5||orderState==6||orderState==7){
            orderDao.deleteOrder(id);
            return true;
        }else{
            return false;
        }
    }

    //管理员取消订单，只能取消正在进行或者等待司机取消的订单（即state 只能为1，4），否则返回false
    //注意取消订单需要退还相应金额
    public boolean cancelOrder(int id){

        Order order = orderDao.showOrderDetail(id);
        int orderState = order.getState();
        if(orderState==1||orderState==4){
            order.setState(new Byte("3"));
            if(order.getPayType().equals(new Byte("0"))){
                int res = orderAffairs.cancelOrderAffairs(order);
                if(res==1){
                    Map<String,String> extras = new HashMap<String, String>();
                    extras.put("type",UserServiceIml.order_canceled+"");
                    extras.put("id",id+"");
                    userService.push(order.getShipperId()+"","订单被管理员取消!","您的订单被管理员取消了，付款已被退还!",extras);
                    return true;
                }
                return false;
            }

            orderDao.updateOrder(order);
            Map<String,String> extras = new HashMap<String, String>();
            extras.put("type",UserServiceIml.order_canceled+"");
            extras.put("id",id+"");
            userService.push(order.getShipperId()+"","订单被管理员取消!","您的订单被管理员取消了!",extras);
            return true;
        }else{
            return false;
        }
    }

    /*
    根据order 生成orderSimple
     */
    private OrderSimple genOrderSimpleFromOrder(Order order){
        Map map  = truckTypeDao.getAllTruckTypeMap();
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
            Trucks_type trucks_type = (Trucks_type) map.get(orderTruckType.getTruckType());
//                    truckTypeDao.getTruckType(orderTruckType.getTruckType());
            truckNames.add(trucks_type.getName());
        }

        return orderSimple;
    }
}
