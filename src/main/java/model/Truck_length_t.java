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
@Table(name="truck_length")
public class Truck_length_t implements Serializable{
    private Byte truckType;
    private String lengthRange;
    @Id
    @Column(name="truck_type")
    public Byte getTruckType() {
        return truckType;
    }

    public void setTruckType(Byte truckType) {
        this.truckType = truckType;
    }
    @Column(name="length_range")
    public String getLengthRange() {
        return lengthRange;
    }

    public void setLengthRange(String lenthRange) {
        this.lengthRange = lenthRange;
    }
}
