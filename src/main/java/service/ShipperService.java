package service;

import pojo.AddressInfo;
import pojo.OrderInfo;
import vo.OrderDetail;

import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
public interface ShipperService {

    public List getFrequentDrivers(int userId);
    public List getFrequentAddresses(int userId);
    public List searchDriversByPhoneNum(String phoneNum);
    public boolean addFrequentAddress(int userId, AddressInfo addressInfo);
    public boolean addFrequentDriver(int userId,int driverId);
    public boolean evaluateOrder(int userId, int id, Byte evaluatePoint, String content);

}
