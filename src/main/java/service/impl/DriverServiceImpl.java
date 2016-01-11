package service.impl;

import dao.Driver_auth_Dao;
import manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserManager userManager = UserManager.getInstance();

    @Autowired
    private Driver_auth_Dao driver_auth_dao;
    /*
    注:
    1.根据车牌号设置认证状态
    2.只能司机设置，不能是货主，且该车牌号必须是该司机添加的
    3.不能重复认证，即只有尚未认证的才能认证
    4.只有当两种认证都填写完整时才设置正在认证的状态
     */
    public boolean setRealNameInfo(int userId, RealNameAuthInfo realNameAuthInfo) {
        return false;
    }

    /*
    注:
    1.根据车牌号设置认证状态
    2.只能司机设置，不能是货主，且该车牌号必须是该司机添加的
    3.不能重复认证，即只有尚未认证的才能认证
    4.只有当两种认证都填写完整时才设置正在认证的状态
     */
    public boolean setTruckAuthInfo(int userId, TruckAuthInfo truckAuthInfo) {
        return false;
    }

    /*
    添加车辆信息
    注：
    1.不能重复添加，根据车牌号判断
    2.只能司机添加，不能是货主
     */
    public boolean addTruck(int userId, TruckInfo truckInfo) {
        //判断是否为司机
        int userType = userManager.getUserType(userId);
        if(userType == 1){
            //判断车牌号是否已存在

        }else{
            //货主
            return false;
        }
        return false;
    }
}
