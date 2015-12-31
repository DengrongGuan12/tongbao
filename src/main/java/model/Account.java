package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name="account")
public class Account implements Serializable {
    private int accountId;
    private Date buildTime;
    private Byte type;
    private int money;
    private int orderId;
    @Id
    @Column(name="account_id",nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    @Column(name="build_time")
    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }
    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
    @Column
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    @Column(name="order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
