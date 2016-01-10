package service;

import pojo.AddressInfo;

import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
public interface ShipperService {
    public boolean evaluateOrder(int userId, int id, int evaluateType, String content);
    public List getFrequentDrivers(int userId);
    public List getFrequentAddresses(int userId);
    public List searchDriversByPhoneNum(String phoneNum);
    public boolean addFrequentAddress(int userId, AddressInfo addressInfo);
}
