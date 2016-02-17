package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity(name="auth_state_name_t")
public class Auth_state_name_t implements Serializable{
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
