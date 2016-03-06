package dao.impl;

import dao.Truck_type_Dao;
import model.Trucks_type;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Byte, Trucks_type> getAllTruckTypeMap() {
        List list = this.getAllTruckType();
        Map<Byte,Trucks_type> map = new HashMap<Byte, Trucks_type>();
        for(int i =0;i<list.size();i++){
            Trucks_type trucks_type = (Trucks_type) list.get(i);
            map.put(trucks_type.getType(),trucks_type);
        }
        return map;
    }

    public Trucks_type getTruckType(Byte type) {
        return (Trucks_type)super.load(Trucks_type.class,type);
    }
}
