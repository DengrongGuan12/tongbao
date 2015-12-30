package model;



import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.CachePut;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name = "driver_auth")
public class Driver_auth implements Serializable{
    private int userId;
    private String truckNum;
    private Byte type;
    private Byte authState;
    private String realName;
    private String truckLicense;
    private String truckPicture;
    private String phoneNum;
    private Byte length;
    private Byte capacity;

    @Id
    @Column(name="user_id",nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Column(name="truck_num",length = 10,nullable = true)
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
    @Column
    public Byte getLength() {
        return length;
    }

    public void setLength(Byte length) {
        this.length = length;
    }
    @Column
    public Byte getCapacity() {
        return capacity;
    }

    public void setCapacity(Byte capacity) {
        this.capacity = capacity;
    }
}
