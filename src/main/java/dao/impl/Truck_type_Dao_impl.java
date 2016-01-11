package dao.impl;

import dao.Truck_type_Dao;
import model.Trucks_type;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
@Repository
public class Truck_type_Dao_impl extends BaseDaoImpl implements Truck_type_Dao{
    public List getAllTruckType() {
        return super.getAllList(Trucks_type.class);
    }

    public boolean hasType(Byte type) {
        Trucks_type trucks_type = (Trucks_type) super.load(Trucks_type.class,type);
        if(trucks_type == null){
            return false;
        }else{
            return true;
        }
    }

    public Trucks_type getTruckType(Byte type) {
        return (Trucks_type)super.load(Trucks_type.class,type);
    }
}
