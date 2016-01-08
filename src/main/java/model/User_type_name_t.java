package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by cg on 2015/12/30.
 */
@Entity
@Table(name="user_type_name_t")
public class User_type_name_t implements Serializable{
    private Byte type;
    private String name;
    @Id
    @Column(name="type")
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
