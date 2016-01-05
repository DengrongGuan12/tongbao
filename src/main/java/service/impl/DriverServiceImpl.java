package service.impl;

import org.springframework.stereotype.Service;
import pojo.RealNameAuthInfo;
import pojo.TruckAuthInfo;
import pojo.TruckInfo;
import service.DriverService;

/**
 * Created by I322233 on 1/4/2016.
 */
@Service
public class DriverServiceImpl implements DriverService {
    public boolean setTruckInfo(int userId, TruckInfo truckInfo) {
        return false;
    }

    public boolean setRealNameInfo(int userId, RealNameAuthInfo realNameAuthInfo) {
        return false;
    }

    public boolean setTruckAuthInfo(int userId, TruckAuthInfo truckAuthInfo) {
        return false;
    }
}
