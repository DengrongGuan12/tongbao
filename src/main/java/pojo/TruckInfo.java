package pojo;

/**
 * Created by I322233 on 1/4/2016.
 */
public class TruckInfo extends TokenAuthInfo {
    private String truckNum;
    private int truckType;
    private int lengthType;
    private int capacityType;

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

    public int getTruckType() {
        return truckType;
    }

    public void setTruckType(int truckType) {
        this.truckType = truckType;
    }

    public int getLengthType() {
        return lengthType;
    }

    public void setLengthType(int lengthType) {
        this.lengthType = lengthType;
    }

    public int getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(int capacityType) {
        this.capacityType = capacityType;
    }

    private String phoneNum;
}
