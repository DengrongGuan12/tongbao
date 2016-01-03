package pojo;

import java.util.Date;

/**
 * Created by dengrong on 2016/1/3.
 */
public class OrderInfo extends TokenAuthInfo{
    private String addressFrom;
    private String addressTo;
    private String loadTime;
    private String goodsType;
    private double goodsWeight;
    private double goodsSize;
    private int truckType;
    private int truckCapacity;
    private int payType;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public double getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(double goodsSize) {
        this.goodsSize = goodsSize;
    }

    public int getTruckType() {
        return truckType;
    }

    public void setTruckType(int truckType) {
        this.truckType = truckType;
    }

    public int getTruckCapacity() {
        return truckCapacity;
    }

    public void setTruckCapacity(int truckCapacity) {
        this.truckCapacity = truckCapacity;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    private double price;
}
