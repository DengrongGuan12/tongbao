package dao;

import model.Driver_auth;

import java.util.List;

/**
 * Created by cg on 2015/12/31.
 */
public interface Driver_auth_Dao {
    public boolean addDriverAuth(Driver_auth driver_auth);
    public Driver_auth getDriverAuthByTruckNum(String truckNum);
    public boolean updateDriverAuth(Driver_auth driver_auth);
    public List getDriverByTruckType(String truckType);
}
