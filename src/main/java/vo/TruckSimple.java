package vo;

/**
 * Created by I322233 on 2/29/2016.
 */
public class TruckSimple {
    private int id;
    private String truckNum;

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    private int authState;
}
