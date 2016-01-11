package pojo;

/**
 * Created by I322233 on 1/4/2016.
 */
public class TruckInfo extends TokenAuthInfo {
    private String truckNum;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    private Byte type;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    private String phoneNum;
}
