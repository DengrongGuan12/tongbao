package service;

import pojo.RealNameAuthInfo;
import pojo.TruckAuthInfo;
import pojo.TruckInfo;

/**
 * Created by I322233 on 1/4/2016.
 */
public interface DriverService {
    public boolean setTruckInfo(int userId, TruckInfo truckInfo);
    public boolean setRealNameInfo(int userId, RealNameAuthInfo realNameAuthInfo);
    public boolean setTruckAuthInfo(int userId, TruckAuthInfo truckAuthInfo);
}
