package service.impl;


import dao.DriverGpsDao;
import dao.Driver_auth_Dao;
import dao.Truck_type_Dao;
import manager.UserManager;
import model.DriverGps;
import model.Driver_auth;
import model.Trucks_type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.PositionInfo;
import pojo.RealNameAuthInfo;
import pojo.TruckAuthInfo;
import pojo.TruckInfo;
import service.DriverService;
import service.UserService;
import vo.TruckDetail;
import vo.TruckSimple;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    UserService userService;

    @Autowired
    DriverGpsDao driverGpsDao;

    public List getTruckList(int userId) {
        //根据司机id获取该司机的所有车辆,不用考虑是否是司机，如果不是司机自然不会有车辆
        int type = userManager.getUserType(userId);
        if(type != 1){
            return null;
        }
        List allTrucks = driver_auth_dao.getAllTruckInfoByUserId(userId);
        List truckList = new ArrayList<TruckSimple>();
        for(int i=0;i<allTrucks.size();i++){
            Driver_auth driver_auth = (Driver_auth) allTrucks.get(i);
            TruckSimple truckSimple = new TruckSimple();
            truckSimple.setId(driver_auth.getId());
            truckSimple.setAuthState(driver_auth.getAuthState());
            truckSimple.setTruckNum(driver_auth.getTruckNum());
            truckList.add(truckSimple);
        }

        return truckList;
    }

    public TruckDetail getTruckDetail(int userId,int id) {

        //根据车辆id 获取该车辆的具体信息
        //注意该车辆是否是属于该用户，如果不是则返回null
        Driver_auth driver_auth = driver_auth_dao.getDriverAuthMessage(id);
        if(driver_auth.getUserId()!=userId){
            return null;
        }
        TruckDetail truckDetail = new TruckDetail();
        truckDetail.setTruckNum(driver_auth.getTruckNum());
        truckDetail.setAuthState(driver_auth.getAuthState());
        Trucks_type trucks_type = truck_type_dao.getTruckType(driver_auth.getType());
        truckDetail.setTypeName(trucks_type.getName());
        truckDetail.setLength(trucks_type.getLength());
        truckDetail.setCapacity(trucks_type.getCapacity());
        truckDetail.setPhoneNum(driver_auth.getPhoneNum());
        truckDetail.setRealName(driver_auth.getRealName());
        return truckDetail;

    }

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
    public String setTruckAuthInfo(int userId, TruckAuthInfo truckAuthInfo) {
        int userType = userManager.getUserType(userId);
        if(userType == 1){
            String truckNum = truckAuthInfo.getTruckNum();
            if(truckNum == null){
                return "车牌号不能为空!";
            }
            Driver_auth driver_auth = driver_auth_dao.getDriverAuthByTruckNum(truckNum);
            if(driver_auth != null){
                if(driver_auth.getUserId() == userId && (driver_auth.getAuthState() == 0 || driver_auth.getAuthState() == 3)){
                    if(truckAuthInfo.getDriveLicensePicUrl() == null || truckAuthInfo.getTruckHeadPicUrl() == null){
                        return "图片不能为空!";
                    }else{
                        driver_auth.setDrivingLicense(truckAuthInfo.getDriveLicensePicUrl());
                        driver_auth.setTruckPicture(truckAuthInfo.getTruckHeadPicUrl());
                        if(driver_auth.getRealName() != null){
                            driver_auth.setAuthState((byte)1);
                        }
                        if(driver_auth_dao.updateDriverAuth(driver_auth)){
                            return null;
                        }else{
                            return "数据库错误!";
                        }
                    }
                }else{
                    return "正在验证,不能提交!";
                }
            }else{
                return "找不到该车牌号的车!";
            }
        }else{
            return  "你根本不是司机!";
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
            //判断车牌号重复

           //判断type是否存在
            if (truck_type_dao.hasType(type)){
                driver_auth.setType(type);
                //truckNum被设置为unique，重复的值会无法添加
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

    public boolean removeTruck(int userId, String truckNum) {
        Driver_auth driver_auth = driver_auth_dao.getDriverAuthByTruckNum(truckNum);
        if(driver_auth==null){
            return false;
        }
        if(driver_auth.getUserId()==userId)
        {
            return driver_auth_dao.deleteTruckById(driver_auth.getId());
        }else{
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


    //获取某个用户的尚未验证和验证失败的司机数量
    public int getUnSubmittedDriverNum(int userId) {
        return driver_auth_dao.getUnSubmittedDriverNum(userId);
    }


    //获取某个用户的正在验证的司机数量
    public int getWaitingExamineDriverNum(int userId) {
        return driver_auth_dao.getWaitingExamineDriverNum(userId);
    }

    // 获取某个用户验证成功和验证失败的司机数量
    public int getExaminedDriverNum(int userId) {
        return driver_auth_dao.getExaminedDriverNum(userId);
    }

    //获取某个用户的所有车辆信息
    //需要设置的信息有
    //id,车牌号,车辆类型名称,车辆载重,车辆长度,车辆宽度,车辆高度,驾驶人姓名,驾驶证号码,随车电话,审核状态
    public List getAllTruckInfoByUserId(int userId) {
        List list = new ArrayList();
        List listTemp = driver_auth_dao.getAllTruckInfoByUserId(userId);
        for (int i=0;i<listTemp.size();i++){
            vo.TruckInfo truckInfo = new vo.TruckInfo();
            Driver_auth driver_auth = (Driver_auth) listTemp.get(i);
            //...
            truckInfo.setId(driver_auth.getId());
            truckInfo.setTruckNum(driver_auth.getTruckNum());
            Byte type = driver_auth.getType();
            Trucks_type trucks_type = truck_type_dao.getTruckType(type);
            truckInfo.setCapacity(trucks_type.getCapacity());
            truckInfo.setLength(trucks_type.getLength());
            truckInfo.setWidth(trucks_type.getWidth());
            truckInfo.setHeight(trucks_type.getHeight());
            truckInfo.setRealName(driver_auth.getRealName());
            truckInfo.setLicenseNum(driver_auth.getLicenseNum());
            truckInfo.setPhoneNum(driver_auth.getPhoneNum());
            truckInfo.setAuthState(driver_auth.getAuthState());
            truckInfo.setTypeName(trucks_type.getName());
            list.add(truckInfo);

        }
        return list;

    }

    //根据driver_auth 中的id 删除司机的某辆车的信息
    public boolean deleteTruckById(int id) {
        return driver_auth_dao.deleteTruckById(id);
    }


    //根据id 获取某辆车的具体信息
    public vo.TruckInfo getTruckInfoById(int id) {
        Driver_auth driver_auth = driver_auth_dao.getDriverAuthMessage(id);
        Trucks_type trucks_type = truck_type_dao.getTruckType(driver_auth.getType());
        vo.TruckInfo truckInfo = new vo.TruckInfo();
        truckInfo.setId(driver_auth.getId());
        truckInfo.setTruckNum(driver_auth.getTruckNum());
        truckInfo.setTypeName(trucks_type.getName());
        truckInfo.setCapacity(trucks_type.getCapacity());
        truckInfo.setLength(trucks_type.getLength());
        truckInfo.setWidth(trucks_type.getWidth());
        truckInfo.setHeight(trucks_type.getHeight());
        truckInfo.setRealName(driver_auth.getRealName());
        truckInfo.setLicenseNum(driver_auth.getLicenseNum());
        truckInfo.setPhoneNum(driver_auth.getPhoneNum());
        truckInfo.setAuthState(driver_auth.getAuthState());
        truckInfo.setDrivingLicense(driver_auth.getDrivingLicense());
        truckInfo.setHeadPicture(driver_auth.getHeadPicture());
        truckInfo.setTruckLicense(driver_auth.getTruckLicense());
        truckInfo.setTruckPicture(driver_auth.getTruckPicture());
        truckInfo.setUserId(driver_auth.getUserId());
        return truckInfo;
    }

    //设置某辆车的认证状态
    //注意认证状态的转化方式 只能 1->2 或者1->3 ,否则返回false
    public boolean setAuthState(int id, Byte state) {
        Driver_auth driver_auth = driver_auth_dao.getDriverAuthMessage(id);
        if(driver_auth.getAuthState().equals(new Byte("1"))){
            driver_auth.setAuthState(state);
            driver_auth_dao.updateDriverAuth(driver_auth);
            Map<String,String> extras = new HashMap<String, String>();
            switch (state){
                case 2:
                    //认证成功
                    extras.put("type",UserServiceIml.truck_auth_pass+"");
                    extras.put("id",id+"");
                    userService.push(driver_auth.getUserId()+"","审核通过!","车牌号为"+driver_auth.getTruckNum()+"审核通过！",extras,UserServiceIml.userType_driver);
                    break;
                case 3:
                    //认证失败
                    extras.put("type",UserServiceIml.truck_auth_fail+"");
                    extras.put("id",id+"");
                    userService.push(driver_auth.getUserId()+"","审核未通过!","车牌号为"+driver_auth.getTruckNum()+"审核未通过！",extras,UserServiceIml.userType_driver);
                    break;
            }
            return true;
        }

        return false;
    }

    public boolean updateMyPosition(int userId, PositionInfo positionInfo) {
        // 更新司机的位置 表 driver_gps, collect_time 是传过来（收集）的时间戳,receive_time 是系统当前时间戳
        //如果表中没有司机位置，就新增一条记录
        int userType = userManager.getUserType(userId);
        //如果不是司机
        if(userType!=1){
            return false;
        }
        DriverGps driverGpsTemp = driverGpsDao.getMyPosition(userId);
        if(driverGpsTemp!=null){
            driverGpsTemp.setCollect_time(Timestamp.valueOf(positionInfo.getCollectTime()));
            driverGpsTemp.setDriver_id(userId);
            driverGpsTemp.setReceive_time(new Timestamp(System.currentTimeMillis()));
            driverGpsTemp.setLng(positionInfo.getLng());
            driverGpsTemp.setLat(positionInfo.getLat());
            return driverGpsDao.updateMyPosition(driverGpsTemp);

        }else {
            DriverGps driverGps = new DriverGps();
            driverGps.setDriver_id(userId);
            driverGps.setCollect_time(Timestamp.valueOf(positionInfo.getCollectTime()));
            driverGps.setReceive_time(new Timestamp(System.currentTimeMillis()));
            driverGps.setLng(positionInfo.getLng());
            driverGps.setLat(positionInfo.getLat());
            return driverGpsDao.createDriverPosition(driverGps);
        }

    }
}
