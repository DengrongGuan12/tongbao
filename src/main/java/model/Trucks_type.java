package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by cg on 2016/1/8.
 */
@Entity(name="truck_type")
public class Trucks_type implements Serializable{
    private Byte type;
    private String name;
    private double base_price;
    private double capacity;
    private double length;
    private double width;
    private double height;
    private double over_price;
    private int base_distance;


    @Id
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
    @Column
    public int getBase_distance(){return base_distance;}

    public void setBase_distance(int temp){this.base_distance = temp;}
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column
    public double getBase_price() {
        return base_price;
    }

    public void setBase_price(double base_price) {
        this.base_price = base_price;
    }
    @Column
    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    @Column
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    @Column
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    @Column
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    @Column
    public double getOver_price() {
        return over_price;
    }

    public void setOver_price(double over_price) {
        this.over_price = over_price;
    }
}
