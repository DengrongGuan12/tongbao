package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by cg on 2016/1/8.
 */
@Entity(name="order_state_name_t")
public class Order_state_name_t implements Serializable{
    private Byte state;
    private String name;
    @Id
    @Column(name="state")
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
