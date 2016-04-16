package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by I322233 on 4/15/2016.
 */
@Entity(name="feedback")
public class Feedback {
    private int state;// 0表示未处理（默认）,1表示已处理
    private String content;//内容
    private int id;//自增的id
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
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




}
