package dao;

import model.Trucks_type;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
public interface Truck_type_Dao {
    public List getAllTruckType();
    public boolean hasType(Byte type);
    public Trucks_type getTruckType(Byte type);
}
