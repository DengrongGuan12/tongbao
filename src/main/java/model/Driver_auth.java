package model;



import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.CachePut;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity(name = "driver_auth")
public class Driver_auth implements Serializable{
    private int id;
    private int userId;
    private String truckNum;
    private Byte type;
    private Byte authState;
    private String realName;
    private String truckLicense;
    private String licenseNum;
    private String drivingLicense;
    private String truckPicture;
    private String phoneNum;
    private String headPicture;

    @Id
    @Column(name="id",nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    @Column(name="user_id")

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Column(name="truck_num",length = 10,nullable = true, unique = true)
    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }
    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Column(name="auth_state")
    public Byte getAuthState() {
        return authState;
    }

    public void setAuthState(Byte authState) {
        this.authState = authState;
    }
    @Column(name="real_name")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Column(name="truck_license")
    public String getTruckLicense() {
        return truckLicense;
    }

    public void setTruckLicense(String truckLicense) {
        this.truckLicense = truckLicense;
    }
    @Column(name = "license_num")
    public String getLicenseNum(){
        return licenseNum;
    }
    public void setLicenseNum(String licenseNum){
        this.licenseNum = licenseNum;
    }
    @Column(name = "head_picture")
    public String getHeadPicture(){
        return headPicture;
    }
    public void setHeadPicture(String headPicture){
        this.headPicture = headPicture;
    }
    @Column(name="truck_picture")
    public String getTruckPicture() {
        return truckPicture;
    }

    public void setTruckPicture(String truckPicture) {
        this.truckPicture = truckPicture;
    }
    @Column(name="phone_num")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(name="driving_license")
    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
}
