package vo;

/**
 * Created by I322233 on 1/4/2016.
 */
public class ContactDetail extends ContactSimple {
    private int sex;

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    private String regTime;
}
