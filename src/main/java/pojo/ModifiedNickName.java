package pojo;

/**
 * Created by I322233 on 12/31/2015.
 */
public class ModifiedNickName extends TokenAuthInfo {
    public String nickName;
    public String getNickName(){
        return nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }
}
