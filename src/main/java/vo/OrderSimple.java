package vo;

import java.util.List;

/**
 * Created by DengrongGuan on 2016/1/10.
 */
public class OrderSimple {
    private int id;
    private String time;
    private String addressFrom;
    private String addressTo;
    private double money;
    private List truckTypes;
    private String fromContactName;
    private String fromContactPhone;
    private String toContactName;
    private String toContactPhone;
    private int state;
    private String loadTime;
    private String stateStr = "";
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double lng;

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }




    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List getTruckTypes() {
        return truckTypes;
    }

    public void setTruckTypes(List truckTypes) {
        this.truckTypes = truckTypes;
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

    public String getToContactPhone() {
        return toContactPhone;
    }

    public void setToContactPhone(String toContactPhone) {
        this.toContactPhone = toContactPhone;
    }
}
