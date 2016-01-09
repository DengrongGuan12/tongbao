package vo;

/**
 * Created by I322233 on 1/8/2016.
 */
public class Message {
    private int id;
    private Byte type;
    private String content;
    private Byte hasRead;
    private String time;
    private int objectId;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getHasRead() {
        return hasRead;
    }

    public void setHasRead(Byte hasRead) {
        this.hasRead = hasRead;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
