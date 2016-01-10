package pojo;

/**
 * Created by dengrong on 2016/1/3.
 */
public class EvaluateInfo extends TokenAuthInfo{
    private int id;
    private int evaluateType;

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(int evaluateType) {
        this.evaluateType = evaluateType;
    }

    private String evaluate;

}
