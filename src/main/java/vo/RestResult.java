package vo;

import javax.persistence.Entity;

/**
 * Created by I322233 on 12/30/2015.
 */
@Entity
public class RestResult {
    private int result;
    private Object data;
    private String errorMsg;
    public String getErrorMsg(){
        return errorMsg;
    }
    public int getResult(){
        return result;
    }
    public Object getData(){
        return data;
    }
    public RestResult(int result, Object data){
        this.result = result;
        this.data = data;
    }
    public RestResult(int result, String errorMsg){
        this.result = result;
        this.errorMsg = errorMsg;
    }
    public RestResult(int result){
        this.result = result;
    }
    public static RestResult CreateResult(int result, Object data){
        return new RestResult(result,data);
    }
    public static RestResult CreateResult(int result, String errorMsg){
        return new RestResult(result,errorMsg);
    }
    public static RestResult CreateResult(int result){
        return new RestResult(result);
    }
}
