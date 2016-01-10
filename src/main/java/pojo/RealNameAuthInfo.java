package pojo;

/**
 * Created by I322233 on 1/5/2016.
 */
public class RealNameAuthInfo extends TokenAuthInfo{
    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    private String truckNum;
    private String realName;
    private String licenseNum;
    private String licensePicUrl;

    public String getDriverHeadPicUrl() {
        return driverHeadPicUrl;
    }

    public void setDriverHeadPicUrl(String driverHeadPicUrl) {
        this.driverHeadPicUrl = driverHeadPicUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getLicensePicUrl() {
        return licensePicUrl;
    }

    public void setLicensePicUrl(String licensePicUrl) {
        this.licensePicUrl = licensePicUrl;
    }

    private String driverHeadPicUrl;
}
