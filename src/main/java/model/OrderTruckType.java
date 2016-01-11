package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by I322233 on 1/11/2016.
 */
@Entity
@Table(name = "order_truck_type")
public class OrderTruckType {
    private int id;
    private int orderId;
    private Byte truckType;

    @Id
    @Column(name="id",length = 32,nullable = true)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator",strategy = "increment")
    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    @Column(name = "order_id")
    public int getOrderId(){
        return orderId;
    }
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    @Column(name = "truck_type")
    public Byte getTruckType(){
        return truckType;
    }
    public void setTruckType(Byte truckType){
        this.truckType = truckType;
    }



}
