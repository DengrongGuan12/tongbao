package pojo;

/**
 * Created by I322233 on 1/4/2016.
 */
public class UserIdInfoWithAuth extends TokenAuthInfo {
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

}
