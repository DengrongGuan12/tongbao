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
@Table(name="truck_type_name_t")
public class Truck_type_name_t implements Serializable{
    private Byte truckType;
    private String name;
    @Id
    @Column(name="truck_type")
    public Byte getTruckType() {
        return truckType;
    }

    public void setTruckType(Byte truckType) {
        this.truckType = truckType;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
