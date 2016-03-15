package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by cg on 2015/12/31.
 */
@Entity(name="orders")
public class Order implements Serializable{
    private int id;
    private Timestamp buildTime;
    private int shipperId;
    private int driverId;
    private Timestamp loadTime;
    private String addressFrom;
    private String addressTo;
    private String from_contact_name;
    private String from_contact_phone;
    private String to_contact_name;
    private String to_contact_phone;
    private String goodsType;
    private double goodsWeight;
    private double goodsSize;
    private Byte payType;
    private double price;
    private Byte evaluate_point;
    private String evaluate_content;
    private String remark;
    private Byte state;
    private double fromLat;
    private double fromLng;
    private double toLat;
    private double tolng;

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
    @Column(name="build_time")
    public Timestamp getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Timestamp buildTime) {
        this.buildTime = buildTime;
    }
    @Column(name="shipper_id")
    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }
    @Column(name = "driver_id")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    @Column(name="load_time")
    public Timestamp getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Timestamp loadTime) {
        this.loadTime = loadTime;
    }
    @Column(name="address_from")
    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }
    @Column(name="address_to")
    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }
    @Column(name="goods_type")
    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
    @Column(name="goods_weight")
    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }
    @Column(name="goods_size")
    public double getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(double goodsSize) {
        this.goodsSize = goodsSize;
    }

    @Column(name="pay_type")
    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }
    @Column
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Column
    public String getFrom_contact_name() {
        return from_contact_name;
    }

    public void setFrom_contact_name(String from_contact_name) {
        this.from_contact_name = from_contact_name;
    }
    @Column
    public String getFrom_contact_phone() {
        return from_contact_phone;
    }

    public void setFrom_contact_phone(String from_contact_phone) {
        this.from_contact_phone = from_contact_phone;
    }
    @Column
    public String getTo_contact_name() {
        return to_contact_name;
    }

    public void setTo_contact_name(String to_contact_name) {
        this.to_contact_name = to_contact_name;
    }
    @Column
    public String getTo_contact_phone() {
        return to_contact_phone;
    }

    public void setTo_contact_phone(String to_contact_phone) {
        this.to_contact_phone = to_contact_phone;
    }
    @Column
    public Byte getEvaluate_point() {
        return evaluate_point;
    }

    public void setEvaluate_point(Byte evaluate_point) {
        this.evaluate_point = evaluate_point;
    }
    @Column
    public String getEvaluate_content() {
        return evaluate_content;
    }

    public void setEvaluate_content(String evaluate_content) {
        this.evaluate_content = evaluate_content;
    }
    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Column(columnDefinition = "0")
    public double getFromLat() {
        return fromLat;
    }

    public void setFromLat(double fromLat) {
        this.fromLat = fromLat;
    }
    @Column(columnDefinition = "0")
    public double getFromLng() {
        return fromLng;
    }

    public void setFromLng(double fromLng) {
        this.fromLng = fromLng;
    }
    @Column(columnDefinition = "0")
    public double getToLat() {
        return toLat;
    }

    public void setToLat(double toLat) {
        this.toLat = toLat;
    }
    @Column(columnDefinition = "0")
    public double getTolng() {
        return tolng;
    }

    public void setTolng(double tolng) {
        this.tolng = tolng;
    }
}
