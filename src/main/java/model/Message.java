package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by cg on 2016/1/8.
 */
@Entity(name="message")
public class Message implements Serializable{
    private int id;
    private Byte type;
    private int object_id;
    private String content;
    private int user_id;
    private Timestamp time;
    private Byte has_read;

    @Id
    @Column(name="id",nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
    @Column(columnDefinition = "INT default 0")
    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }
    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Column
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    @Column
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    @Column
    public Byte getHas_read() {
        return has_read;
    }

    public void setHas_read(Byte has_read) {
        this.has_read = has_read;
    }
}
