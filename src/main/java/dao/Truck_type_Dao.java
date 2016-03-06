package dao;

import model.Trucks_type;

import java.util.List;
import java.util.Map;

/**
 * Created by cg on 2016/1/9.
 */
public interface Truck_type_Dao {
    public List getAllTruckType();
    public Map<Byte,Trucks_type>getAllTruckTypeMap();
    public boolean hasType(Byte type);
    public Trucks_type getTruckType(Byte type);
}
