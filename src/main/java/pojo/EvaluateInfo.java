package pojo;

/**
 * Created by dengrong on 2016/1/3.
 */
public class EvaluateInfo extends TokenAuthInfo{
    private int id;

    public Byte getEvaluatePoint() {
        return evaluatePoint;
    }

    public void setEvaluatePoint(Byte evaluatePoint) {
        this.evaluatePoint = evaluatePoint;
    }

    private Byte evaluatePoint;

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

    private String evaluate;

}
