package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by cg on 2016/1/23.
 */
@Entity(name = "message_type")
public class MessageType {
    private Byte type;
    private String name;
    @Id
    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
