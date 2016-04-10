package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2016/1/8.
 */
@Entity(name="frequent_address")
public class Frequent_addresses implements Serializable{
    private int id;

    private int shipper_id;
    private String address;
    private double lat;
    private double lng;



    private String contact_name;
    private String contact_phone;
    @Column
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    @Column
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

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
    @Column
    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    @Column
    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

}
