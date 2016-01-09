package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2016/1/8.
 */
@Entity
@Table(name="frequent_address")
public class Frequent_addresses implements Serializable{
    private int id;

    private int shipper_id;
    private String address;
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
    @Column
    public int getShipper_id() {
        return shipper_id;
    }

    public void setShipper_id(int shipper_id) {
        this.shipper_id = shipper_id;
    }
    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
