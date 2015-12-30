package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name="contacts")
public class Contacts implements Serializable {
    private int id;
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

    private int shipperId;
    private int driverId;
    private Byte shipperToDriver;
    private Byte driverToShipper;
    @Column(name="shipper_id")
    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }
    @Column(name="driver_id")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    @Column(name="shipper_to_driver")
    public Byte getShipperToDriver() {
        return shipperToDriver;
    }

    public void setShipperToDriver(Byte shipperToDriver) {
        this.shipperToDriver = shipperToDriver;
    }
    @Column(name="driver_to_shipper")
    public Byte getDriverToShipper() {
        return driverToShipper;
    }

    public void setDriverToShipper(Byte driverToShipper) {
        this.driverToShipper = driverToShipper;
    }


}
