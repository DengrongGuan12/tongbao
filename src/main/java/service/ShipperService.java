package service;

import pojo.AddressInfo;
import vo.DriverPosition;

import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
public interface ShipperService {
    public boolean evaluateOrder(int userId, int id, Byte evaluateType, String content);
    public List getFrequentDrivers(int userId);
    public List getFrequentAddresses(int userId);
    public List searchDriversByPhoneNum(String phoneNum);
    public boolean addFrequentAddress(int userId, AddressInfo addressInfo);
    public boolean editFrequentAddress(int userId, AddressInfo addressInfo);
    public boolean deleteFrequentAddress(int userId, int id);
    public boolean addFrequentDriver(int userId, int driverId);
    public boolean deleteFrequentDriver(int userId, int driverId);
    public List<DriverPosition> getDriversPosition();
}
