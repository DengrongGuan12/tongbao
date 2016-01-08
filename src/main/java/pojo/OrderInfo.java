package pojo;

import java.util.Date;

/**
 * Created by dengrong on 2016/1/3.
 */
public class OrderInfo extends TokenAuthInfo{
    private String addressFrom;
    private String addressTo;
    private String fromContactName;
    private String fromContactPhone;
    private String toContactName;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    //备注信息
    private String remark;

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

    private String toContactPhone;
    private String loadTime;
    private String goodsType;
    private double goodsWeight;
    private double goodsSize;

    public String getTruckTypes() {
        return truckTypes;
    }

    public void setTruckTypes(String truckTypes) {
        this.truckTypes = truckTypes;
    }

    private String truckTypes;
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



    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    private double price;
}
