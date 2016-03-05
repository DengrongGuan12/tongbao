package service;

import pojo.RealNameAuthInfo;
import pojo.TruckAuthInfo;
import pojo.TruckInfo;
import vo.TruckDetail;

import java.util.List;

/**
 * Created by I322233 on 1/4/2016.
 */
public interface DriverService {
    public List getTruckList(int userId);
    public TruckDetail getTruckDetail(int userId,int id);
    public boolean setRealNameInfo(int userId, RealNameAuthInfo realNameAuthInfo);
    public boolean setTruckAuthInfo(int userId, TruckAuthInfo truckAuthInfo);
    public boolean addTruck(int userId,TruckInfo truckInfo);
    public boolean removeTruck(int userId, String truckNum);
    public int getUnSubmittedDriverNum();
    public int getWaitingExamineDriverNum();
    public int getExaminedDriverNum();
    public int getUnSubmittedDriverNum(int userId);
    public int getWaitingExamineDriverNum(int userId);
    public int getExaminedDriverNum(int userId);
    public List getAllTruckInfoByUserId(int userId);
    public boolean deleteTruckById(int id);
    public vo.TruckInfo getTruckInfoById(int id);
    public boolean setAuthState(int id, Byte state);
}
