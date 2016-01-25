package vo;

/**
 * Created by I322233 on 1/21/2016.
 */
public class TruckInfo {
    private int id;
    //0未验证，1正在验证，2验证成功，3验证失败
    private Byte authState = 0;
    //行驶证图片
    private String drivingLicense = "";
    //驾驶人头像
    private String headPicture = "";
    //驾驶证号码
    private String licenseNum = "";
    //随车电话
    private String phoneNum = "";
    //真实姓名
    private String realName = "";
    //驾驶证图片
    private String truckLicense = "";
    //车牌号
    private String truckNum = "";
    //货车图片
    private String truckPicture = "";
    //类型名称
    private String typeName = "";
    //载重
    private double capacity = 0;
    //长
    private double length = 0;
    //宽
    private double width = 0;
    //高
    private double height = 0;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //user id
    private int userId = 0;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTruckLicense() {
        return truckLicense;
    }

    public void setTruckLicense(String truckLicense) {
        this.truckLicense = truckLicense;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    public String getTruckPicture() {
        return truckPicture;
    }

    public void setTruckPicture(String truckPicture) {
        this.truckPicture = truckPicture;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Byte getAuthState() {
        return authState;
    }

    public void setAuthState(Byte authState) {
        this.authState = authState;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }


}
