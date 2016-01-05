package pojo;

/**
 * Created by I322233 on 1/5/2016.
 */
public class TruckAuthInfo extends TokenAuthInfo {
    private String truckNum;
    private String truckHeadPicUrl;

    public String getDriveLicensePicUrl() {
        return driveLicensePicUrl;
    }

    public void setDriveLicensePicUrl(String driveLicensePicUrl) {
        this.driveLicensePicUrl = driveLicensePicUrl;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    public String getTruckHeadPicUrl() {
        return truckHeadPicUrl;
    }

    public void setTruckHeadPicUrl(String truckHeadPicUrl) {
        this.truckHeadPicUrl = truckHeadPicUrl;
    }

    private String driveLicensePicUrl;

}
