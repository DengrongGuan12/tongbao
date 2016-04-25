package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2016/1/8.
 */
@Entity(name="frequent_driver")
public class Frequent_driver implements Serializable{
    private int shipper_id;
    private int driver_id;

    @Id
    @Column(name = "shipper_id")
    public int getShipper_id() {
        return shipper_id;
    }

    public void setShipper_id(int shipper_id) {
        this.shipper_id = shipper_id;
    }
    @Id
    @Column(name = "driver_id")
    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }
}
