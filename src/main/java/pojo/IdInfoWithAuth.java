package pojo;

/**
 * Created by dengrong on 2016/1/3.
 */
public class IdInfoWithAuth extends TokenAuthInfo{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


}
