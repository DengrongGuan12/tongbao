package service.impl;

import dao.Driver_auth_Dao;
import dao.Truck_type_Dao;
import manager.UserManager;
import model.Driver_auth;
import model.Trucks_type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.RealNameAuthInfo;
import pojo.TruckAuthInfo;
import pojo.TruckInfo;
import service.DriverService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I322233 on 1/4/2016.
 */
@Service
public class DriverServiceImpl implements DriverService {
    UserManager userManager = UserManager.getInstance();

    @Autowired
    private Driver_auth_Dao driver_auth_dao;

    @Autowired
    private Truck_type_Dao truck_type_dao;
    /*
    注:
    1.根据车牌号设置认证状态
    2.只能司机设置，不能是货主，且该车牌号必须是该司机添加的
    3.不能重复认证，即只有尚未认证的才能认证
    4.只有当两种认证都填写完整时才设置正在认证的状态
     */
    public boolean setRealNameInfo(int userId, RealNameAuthInfo realNameAuthInfo) {
        int userType = userManager.getUserType(userId);
        if(userType == 1){
            String truckNum = realNameAuthInfo.getTruckNum();
            if(truckNum == null){
                return false;
            }
            Driver_auth driver_auth = driver_auth_dao.getDriverAuthByTruckNum(truckNum);
            if(driver_auth != null){
                if(driver_auth.getUserId() == userId && (driver_auth.getAuthState() == 0 || driver_auth.getAuthState() == 3)){
                    if(realNameAuthInfo.getDriverHeadPicUrl() == null || realNameAuthInfo.getLicenseNum() == null
                            || realNameAuthInfo.getLicensePicUrl() == null || realNameAuthInfo.getRealName() == null){
                        return false;
                    }else{
                        driver_auth.setTruckLicense(realNameAuthInfo.getLicensePicUrl());
                        driver_auth.setLicenseNum(realNameAuthInfo.getLicenseNum());
                        driver_auth.setRealName(realNameAuthInfo.getRealName());
                        driver_auth.setHeadPicture(realNameAuthInfo.getDriverHeadPicUrl());
                        if(driver_auth.getDrivingLicense() != null){
                            //因为插入的时候已经做了限制，所以只要判断一个是否为null就行了
                            driver_auth.setAuthState((byte)1);

                        }
                        if(driver_auth_dao.updateDriverAuth(driver_auth)){
                            return true;
                        }else{
                            return false;
                        }

                    }

                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /*
    注:
    1.根据车牌号设置认证状态
    2.只能司机设置，不能是货主，且该车牌号必须是该司机添加的
    3.不能重复认证，即只有尚未认证的才能认证
    4.只有当两种认证都填写完整时才设置正在认证的状态
     */
    public boolean setTruckAuthInfo(int userId, TruckAuthInfo truckAuthInfo) {
        int userType = userManager.getUserType(userId);
        if(userId == 1){
            String truckNum = truckAuthInfo.getTruckNum();
            if(truckNum == null){
                return false;
            }
            Driver_auth driver_auth = driver_auth_dao.getDriverAuthByTruckNum(truckNum);
            if(driver_auth != null){
                if(driver_auth.getUserId() == userId && (driver_auth.getAuthState() == 0 || driver_auth.getAuthState() == 3)){
                    if(truckAuthInfo.getDriveLicensePicUrl() == null || truckAuthInfo.getTruckHeadPicUrl() == null){
                        return false;
                    }else{
                        driver_auth.setDrivingLicense(truckAuthInfo.getDriveLicensePicUrl());
                        driver_auth.setTruckPicture(truckAuthInfo.getTruckHeadPicUrl());
                        if(driver_auth.getRealName() != null){
                            driver_auth.setAuthState((byte)1);
                        }
                        if(driver_auth_dao.updateDriverAuth(driver_auth)){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return  false;
        }
    }

    /*
    添加车辆信息
    注：
    1.不能重复添加，根据车牌号判断
    2.只能司机添加，不能是货主
    3.type要存在
     */
    public boolean addTruck(int userId, TruckInfo truckInfo) {
        //判断是否为司机
        int userType = userManager.getUserType(userId);
        if(userType == 1){
            Driver_auth driver_auth = new Driver_auth();
            driver_auth.setPhoneNum(truckInfo.getPhoneNum());
            driver_auth.setUserId(userId);
            driver_auth.setAuthState((byte)0);
            driver_auth.setTruckNum(truckInfo.getTruckNum());
            Byte type = truckInfo.getType();
           //判断type是否存在
            if (truck_type_dao.hasType(type)){
                driver_auth.setType(type);
                if(driver_auth_dao.addDriverAuth(driver_auth)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            //货主
            return false;
        }
    }

    //获取尚未验证和验证失败的司机数量
    public int getUnSubmittedDriverNum() {
        return driver_auth_dao.getUnSubmittedDriverNum();
    }

    //获取正在验证的司机数量
    public int getWaitingExamineDriverNum() {
        return driver_auth_dao.getWaitingExamineDriverNum();
    }

    //获取验证成功和验证失败的司机数量
    public int getExaminedDriverNum() {
        return driver_auth_dao.getExaminedDriverNum();
    }

    // TODO: 1/21/2016
    //获取某个用户的尚未验证和验证失败的司机数量
    public int getUnSubmittedDriverNum(int userId) {
        return 0;
    }

    // TODO: 1/21/2016
    //获取某个用户的正在验证的司机数量
    public int getWaitingExamineDriverNum(int userId) {
        return 0;
    }

    // TODO: 1/21/2016
    // 获取某个用户验证成功和验证失败的司机数量
    public int getExaminedDriverNum(int userId) {
        return 0;
    }

    // TODO: 1/21/2016
    //获取某个用户的所有车辆信息
    //需要设置的信息有
    //id,车牌号,车辆类型名称,车辆载重,车辆长度,车辆宽度,车辆高度,驾驶人姓名,驾驶证号码,随车电话,审核状态
    public List getAllTruckInfoByUserId(int userId) {
        List list = new ArrayList();
        return list;
    }
}
