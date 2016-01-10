package service.impl;

import org.springframework.stereotype.Service;
import pojo.AddressInfo;
import service.ShipperService;
import vo.Address;
import vo.User;
import vo.UserSimple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class ShipperServiceImpl implements ShipperService{


    /*
    这边要判断该user是否是订单的发出者，如果不是返回false
     */
    public boolean evaluateOrder(int userId, int id, int evaluatePoint, String content) {
        return false;
    }

    public List getFrequentDrivers(int userId) {
        List list = new ArrayList();
        User user = new User();
        user.setNickName("sdfsdf");
        list.add(user);
        return list;

    }

    public List getFrequentAddresses(int userId) {
        List list = new ArrayList();
        Address address = new Address();
        address.setLat(32.08);
        address.setLng(108.09);
        address.setName("南京市鼓楼区");
        list.add(address);
        return list;
    }

    /*
    根据手机号模糊查找司机，返回司机列表
     */
    public List searchDriversByPhoneNum(String phoneNum) {
        List list = new ArrayList();
        UserSimple userSimple = new UserSimple();
        userSimple.setId(1);
        userSimple.setNickName("sdfg");
        userSimple.setPhoneNum("123123123123");
        list.add(userSimple);
        return list;
    }

    /*
    添加常用地址, 很有可能会加经纬度
     */
    public boolean addFrequentAddress(int userId, AddressInfo addressInfo) {
        return false;
    }

}
