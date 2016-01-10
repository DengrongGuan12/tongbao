package pojo;

/**
 * Created by DengrongGuan on 2016/1/10.
 */
public class SearchInfoWithAuth extends TokenAuthInfo {
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    private String phoneNum;
}
