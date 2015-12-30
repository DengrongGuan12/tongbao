package vo;

import javax.persistence.Entity;

/**
 * Created by I322233 on 12/30/2015.
 */
@Entity
public class RestResult {
    private int status;
    private Object data;
    public int getStatus(){
        return status;
    }
    public Object getData(){
        return data;
    }
    public RestResult(int status, Object data){
        this.status = status;
        this.data = data;
    }
    public static RestResult CreateResult(int status, Object data){
        return new RestResult(status,data);
    }
}
