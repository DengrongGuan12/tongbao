package service;

import pojo.TruckInfo;

/**
 * Created by I322233 on 1/4/2016.
 */
public interface DriverService {
    public boolean setTruckInfo(int userId, TruckInfo truckInfo);
}
