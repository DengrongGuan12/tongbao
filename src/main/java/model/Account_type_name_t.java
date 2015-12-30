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
@Table(name="account_type_name_t")
public class Account_type_name_t implements Serializable{
    private Byte accountType;
    private String name;
    @Id
    @Column(name="account_type")
    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
