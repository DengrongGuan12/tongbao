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
}
