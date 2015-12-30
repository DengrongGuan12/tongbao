package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name="cancel_state_name_t")
public class Cancel_state_name_t implements Serializable{
    private Byte cancelState;
    private String name;
    @Id
    @Column(name="cancel_state")
    public Byte getCancelState() {
        return cancelState;
    }

    public void setCancelState(Byte cancelState) {
        this.cancelState = cancelState;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
