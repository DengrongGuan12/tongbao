package pojo;

import java.util.Date;

/**
 * Created by dengrong on 2016/1/3.
 */
public class OrderInfo extends TokenAuthInfo{
    private String addressFrom;
    private double addressFromLat;
    private double addressFromLng;

    private String addressTo;
    private double addressToLat;

    public double getAddressToLng() {
        return addressToLng;
    }

    public void setAddressToLng(double addressToLng) {
        this.addressToLng = addressToLng;
    }

    public double getAddressFromLat() {
        return addressFromLat;
    }

    public void setAddressFromLat(double addressFromLat) {
        this.addressFromLat = addressFromLat;
    }

    public double getAddressFromLng() {
        return addressFromLng;
    }

    public void setAddressFromLng(double addressFromLng) {
        this.addressFromLng = addressFromLng;
    }

    public double getAddressToLat() {
        return addressToLat;
    }

    public void setAddressToLat(double addressToLat) {
        this.addressToLat = addressToLat;
    }

    private double addressToLng;

    private String fromContactName;
    private String fromContactPhone;
    private String toContactName;
    private String toContactPhone;
    private String loadTime;
    private String goodsType;
    private double goodsWeight;
    private double goodsSize;
    private String truckTypes;
    private Byte payType;
    private double price;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private double distance;//公里数
    //备注信息
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getToContactPhone() {
        return toContactPhone;
    }

    public void setToContactPhone(String toContactPhone) {
        this.toContactPhone = toContactPhone;
    }

    public String getFromContactName() {
        return fromContactName;
    }

    public void setFromContactName(String fromContactName) {
        this.fromContactName = fromContactName;
    }

    public String getFromContactPhone() {
        return fromContactPhone;
    }

    public void setFromContactPhone(String fromContactPhone) {
        this.fromContactPhone = fromContactPhone;
    }

    public String getToContactName() {
        return toContactName;
    }

    public void setToContactName(String toContactName) {
        this.toContactName = toContactName;
    }


    public String getTruckTypes() {
        return truckTypes;
    }

    public void setTruckTypes(String truckTypes) {
        this.truckTypes = truckTypes;
    }



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



    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }


}
