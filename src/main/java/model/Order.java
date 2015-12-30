package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cg on 2015/12/31.
 */
@Entity
@Table(name="orders")
public class Order implements Serializable{
    private int orderId;
    private Date buildTime;
    private int shipperId;
    private int driverId;
    private Date loadTime;
    private String addressFrom;
    private String addressTo;
    private Byte goodsType;
    private String goodsWeightSize;
    private Byte truckType;
    private Byte truckCapacity;
    private Byte payType;
    private int price;
    private Byte evaluateType;
    private String evaluate;
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
    @Column(name="build_time")
    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
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
    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
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
    public Byte getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
    }
    @Column(name="goods_weight_size")
    public String getGoodsWeightSize() {
        return goodsWeightSize;
    }

    public void setGoodsWeightSize(String goodsWeightSize) {
        this.goodsWeightSize = goodsWeightSize;
    }
    @Column(name="truck_type")
    public Byte getTruckType() {
        return truckType;
    }

    public void setTruckType(Byte truckType) {
        this.truckType = truckType;
    }
    @Column(name="truck_capacity")
    public Byte getTruckCapacity() {
        return truckCapacity;
    }

    public void setTruckCapacity(Byte truckCapacity) {
        this.truckCapacity = truckCapacity;
    }
    @Column(name="pay_type")
    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }
    @Column
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Column(name="evaluate_type")
    public Byte getEvaluateype() {
        return evaluateType;
    }

    public void setEvaluateype(Byte evaluateType) {
        this.evaluateType = evaluateType;
    }
    @Column
    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}
