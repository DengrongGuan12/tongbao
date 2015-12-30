package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name="order_cance_state")
public class Order_cancel_state implements Serializable{

    private int orderId;
    private Byte state;
    private String reason;
    @Id
    @Column(name="order_id",nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    @Column
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
    @Column
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
