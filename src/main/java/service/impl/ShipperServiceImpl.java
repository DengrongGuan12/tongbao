package service.impl;

import dao.*;
import manager.UserManager;
import model.DriverGps;
import model.Frequent_addresses;
import model.Frequent_driver;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.AddressInfo;
import pojo.OrderInfo;
import service.ShipperService;
import vo.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class ShipperServiceImpl implements ShipperService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private Frequent_driver_Dao frequent_driver_dao;
    @Autowired
    private Frequent_address_Dao frequent_address_dao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private DriverGpsDao driverGpsDao;

    /*
    这边要判断该user是否是订单的发出者且是否已经完成，如果不是返回false
     */
    public boolean evaluateOrder(int userId, int id, Byte evaluatePoint, String content) {
        model.Order order = orderDao.showOrderDetail(id);
        if(order!=null&&userId==order.getShipperId()&&order.getState().equals(new Byte("2"))){
            order.setEvaluate_point(evaluatePoint);
            order.setEvaluate_content(content);
            return orderDao.updateOrder(order);
        }else{
            return false;
        }
    }
    /*
    货主添加常用司机
    需要属性包括货主id,司机id
     */
    public boolean addFrequentDriver(int userId, int driverId) {
        model.User shipper = userDao.getUserById(userId);
        model.User driver = userDao.getUserById(driverId);
        Byte b1 = shipper.getType();
        Byte b2 = driver.getType();
        if(!(b1.equals(new Byte("0")))||!(b2.equals(new Byte("1")))){
            return false;
        }
        Frequent_driver frequent_driver=new Frequent_driver();
        frequent_driver.setShipper_id(userId);
        frequent_driver.setDriver_id(driverId);
        return frequent_driver_dao.addFrequentDriver(frequent_driver);
    }

    public boolean deleteFrequentDriver(int userId, int driverId) {
        //   删除我的某个常用司机
        return frequent_driver_dao.deleteFrequentDriver(userId,driverId);
    }

    public List<DriverPosition> getDriversPosition() {
        //从表 driver_gps 表中获取数据
        List<DriverGps>allDriverP = driverGpsDao.getAllDriversPosition();
        List<DriverPosition> driverPositions = new ArrayList<DriverPosition>();
        for(DriverGps driverGps : allDriverP){
            DriverPosition driverPosition = new DriverPosition();
            driverPosition.setDriverId(driverGps.getDriver_id());
            driverPosition.setCollectTime(driverGps.getCollect_time().toString());
            driverPosition.setReceiveTime(driverGps.getReceive_time().toString());
            driverPosition.setLat(driverGps.getLat());
            driverPosition.setLng(driverGps.getLng());
            driverPositions.add(driverPosition);
        }
        return driverPositions;
    }

    /*
    货主获取常用司机列表
     */
    public List getFrequentDrivers(int userId) {
        int userType = UserManager.getInstance().getUserType(userId);
        if(userType==1){
            return null;
        }
        List listTemp=frequent_driver_dao.getFrequentDriversByShipperId(userId);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            Frequent_driver fd=(Frequent_driver)listTemp.get(i);
            model.User userTemp=userDao.getUserById(fd.getDriver_id());
            UserSimple user = new UserSimple();
            user.setNickName(userTemp.getNick_name());
            user.setId(userTemp.getId());
            user.setPhoneNum(userTemp.getPhone_number());
            list.add(user);
        }

        return list;

    }
    /*
    货主获取常用地址
     */

    public List getFrequentAddresses(int userId) {
        List listTemp=frequent_address_dao.getFrequentAddressesByShipperId(userId);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            Frequent_addresses fa=(Frequent_addresses)listTemp.get(i);
            Address address = new Address();
            address.setLat(fa.getLat());
            address.setLng(fa.getLng());
            address.setName(fa.getAddress());
            address.setContactName(fa.getContact_name());
            address.setContactPhone(fa.getContact_phone());
            address.setId(fa.getId());
            list.add(address);
        }

        return list;
    }

    /*
    根据手机号模糊查找司机，返回司机列表
     */
    public List searchDriversByPhoneNum(String phoneNum) {
        List listTemp = userDao.searchDriver(phoneNum);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            UserSimple userSimple = new UserSimple();
            model.User userTemp = (model.User)listTemp.get(i);
            userSimple.setId(userTemp.getId());
            userSimple.setNickName(userTemp.getNick_name());
            userSimple.setPhoneNum(userTemp.getPhone_number());
            list.add(userSimple);
        }

        return list;
    }

    /*
    添加常用地址, 很有可能会加经纬度
     */
    public boolean addFrequentAddress(int userId, AddressInfo addressInfo) {
        model.Frequent_addresses fa=new model.Frequent_addresses();
        fa.setShipper_id(userId);
        fa.setAddress(addressInfo.getAddress());
        fa.setLat(addressInfo.getLat());
        fa.setLng(addressInfo.getLng());
        fa.setContact_name(addressInfo.getContactName());
        fa.setContact_phone(addressInfo.getContactPhone());
        return frequent_address_dao.addFrequentAddress(fa);
    }

    public boolean editFrequentAddress(int userId, AddressInfo addressInfo) {
        // 更新常用地址信息，addressInfo中有id的信息，只更新不为null的值，注意只能更新自己的
        Frequent_addresses frequent_addresses = frequent_address_dao.getFrequent_address(addressInfo.getId());
        if(frequent_addresses==null||frequent_addresses.getShipper_id()!=userId){
            return false;
        }
        if(addressInfo.getAddress()!=null){
            frequent_addresses.setAddress(addressInfo.getAddress());
        }
        if(addressInfo.getContactName()!=null){
            frequent_addresses.setContact_name(addressInfo.getContactName());
        }
        if(addressInfo.getContactPhone()!=null){
            frequent_addresses.setContact_phone(addressInfo.getContactPhone());
        }
        if(addressInfo.getLat()!=0){
            frequent_addresses.setLat(addressInfo.getLat());
        }
        if(addressInfo.getLng()!=0){
            frequent_addresses.setLng(addressInfo.getLng());
        }

        return frequent_address_dao.updateFrequentAddress(frequent_addresses);
    }

    public boolean deleteFrequentAddress(int userId, int id) {
        //  根据id删除常用地址, 注意只能删除自己的
        Frequent_addresses frequent_addresses = frequent_address_dao.getFrequent_address(id);
        if(frequent_addresses==null||frequent_addresses.getShipper_id()!=userId){
            return false;
        }
        return frequent_address_dao.deleteFrequentAddressByShipperId(id, userId);
    }


}
