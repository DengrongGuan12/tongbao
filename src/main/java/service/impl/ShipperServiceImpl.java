package service.impl;

import dao.Frequent_address_Dao;
import dao.Frequent_driver_Dao;
import dao.OrderDao;
import dao.UserDao;
import model.Frequent_addresses;
import model.Frequent_driver;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.AddressInfo;
import pojo.OrderInfo;
import service.ShipperService;
import vo.Address;
import vo.OrderDetail;
import vo.User;
import vo.UserSimple;

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
    UserDao userDao;
    @Autowired
    Frequent_driver_Dao frequent_driver_dao;
    @Autowired
    Frequent_address_Dao frequent_address_dao;
    @Autowired
    OrderDao orderDao;

    /*
    这边要判断该user是否是订单的发出者且是否已经完成，如果不是返回false
     */
    public boolean evaluateOrder(int userId, int id, Byte evaluatePoint, String content) {
        model.Order order = orderDao.showOrderDetail(id);
        if(userId==order.getShipperId()&&order.getState().equals(new Byte("2"))){
            order.setEvaluate_point(evaluatePoint);
            order.setEvaluate_content(content);
            orderDao.updateOrder(order);
            return true;
        }else{
            return false;
        }
    }
    /*
    货主添加常用司机
    需要属性包括货主id,司机id
     */
    public boolean addFrequentDriver(int userId, int driverId) {
        Frequent_driver frequent_driver=new Frequent_driver();
        frequent_driver.setShipper_id(userId);
        frequent_driver.setDriver_id(driverId);
        return frequent_driver_dao.addFrequentDriver(frequent_driver);
    }
    /*
    货主获取常用司机列表
     */
    public List getFrequentDrivers(int userId) {
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
        return frequent_address_dao.addFrequentAddress(fa);
    }

    public boolean addFrequentDriver(int userId, int driverId) {
        return false;
    }

}
