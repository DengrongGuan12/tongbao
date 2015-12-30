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
@Table(name="truck_capacity_t")
public class Truck_capacity_t implements Serializable{
    private Byte truckType;
    private String capacityRange;
    @Id
    @Column(name="truck_type")
    public Byte getTruckType() {
        return truckType;
    }

    public void setTruckType(Byte truckType) {
        this.truckType = truckType;
    }
    @Column(name="capacity_range")
    public String getCapacityRange() {
        return capacityRange;
    }

    public void setCapacityRange(String capacityRange) {
        this.capacityRange = capacityRange;
    }
}
