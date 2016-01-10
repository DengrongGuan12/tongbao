package pojo;

/**
 * Created by DengrongGuan on 2016/1/10.
 */
public class OrderFilterInfo extends TokenAuthInfo {
    private String fromAddress;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    private String toAddress;

}
