package pojo;

/**
 * Created by I322233 on 1/4/2016.
 */
public class UserIdInfoWithAuth extends TokenAuthInfo {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

}
