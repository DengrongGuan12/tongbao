package dao;

import model.Driver_auth;

import java.util.List;

/**
 * Created by cg on 2015/12/31.
 */
public interface Driver_auth_Dao {
    public boolean addDriverAuth(Driver_auth driver_auth);
    public Driver_auth getDriverAuthByUserId(int userId);
    public Driver_auth getDriverAuthByTruckNum(String truckNum);
    public boolean updateDriverAuth(Driver_auth driver_auth);
    public boolean deleteDriverAuth(int userId);
    public List getDriverByTruckType(String truckType);
    public int getUnSubmittedDriverNum();
    public int getWaitingExamineDriverNum();
    public int getExaminedDriverNum();
    public int getUnSubmittedDriverNum(int userId);
    public int getWaitingExamineDriverNum(int userId);
    public int getExaminedDriverNum(int userId);
    public List getAllTruckInfoByUserId(int userId);
    public boolean deleteTruckById(int id);
}
