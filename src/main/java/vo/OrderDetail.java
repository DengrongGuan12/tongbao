package vo;

/**
 * Created by DengrongGuan on 2016/1/10.
 */
public class OrderDetail extends OrderSimple {

    //司机手机号
    private String driverPhoneNum;
    //货主手机号
    private String shipperPhoneNum;
    //货物类型
    private String goodsType;

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getDriverPhoneNum() {
        return driverPhoneNum;
    }

    public void setDriverPhoneNum(String driverPhoneNum) {
        this.driverPhoneNum = driverPhoneNum;
    }

    public String getShipperPhoneNum() {
        return shipperPhoneNum;
    }

    public void setShipperPhoneNum(String shipperPhoneNum) {
        this.shipperPhoneNum = shipperPhoneNum;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    //货物重量
    private double goodsWeight;

    private Byte evaluatePoint;

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public Byte getEvaluatePoint() {
        return evaluatePoint;
    }

    public void setEvaluatePoint(Byte evaluatePoint) {
        this.evaluatePoint = evaluatePoint;
    }

    private String evaluateContent;


}
