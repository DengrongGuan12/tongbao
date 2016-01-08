package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by cg on 2015/12/29.
 */
@Entity
@Table(name = "user")
public class User implements Serializable{
    private int id;
    private Byte type;
    private String phone_number;
    private String password;
    private String nick_name;
    private String icon;
    private int point;
    private int money;
    private Timestamp register_time;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(){super();}

    /**
     * 用于注册
     * @param phone_number
     * @param password
     * @param type
     */
    public User(String phone_number,String password,Byte type){
        this.type=type;
        this.password=password;
        this.phone_number=phone_number;

    }

    /***
     * id自增
     *
     */
    @Id
    @Column(name="id",length = 32,nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    /**
     * 用户类型 0代表货主，1代表司机
     *
     */
    @Column(name="type",nullable = false)
    public byte getType(){return type;}
    public void setType(Byte type){this.type=type;}

    @Column(name="phone_number",nullable= false,unique = true)
    public String getPhone_number(){return phone_number;}

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    @Column(name="password",nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name="nick_name",length = 20)
    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    @Column(name="icon_url")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    @Column
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    @Column
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    @Column(updatable = false)
    public Timestamp getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Timestamp register_time) {
        this.register_time = register_time;
    }
}
