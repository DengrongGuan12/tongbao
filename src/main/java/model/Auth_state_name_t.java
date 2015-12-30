package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name="auth_state_name_t")
public class Auth_state_name_t implements Serializable{
    private Byte authState;
    private String name;
    @Id
    @Column(name="auth_state")
    public Byte getAuthState() {
        return authState;
    }

    public void setAuthState(Byte authState) {
        this.authState = authState;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
