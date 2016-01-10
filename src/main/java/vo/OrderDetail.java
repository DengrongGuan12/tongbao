package vo;

/**
 * Created by DengrongGuan on 2016/1/10.
 */
public class OrderDetail extends OrderSimple {
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private int state;

}
