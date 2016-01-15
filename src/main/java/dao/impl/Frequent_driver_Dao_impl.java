package dao.impl;

import dao.Frequent_driver_Dao;
import model.Frequent_driver;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
@Repository
public class Frequent_driver_Dao_impl extends BaseDaoImpl implements Frequent_driver_Dao {

    public boolean addFrequentDriver(Frequent_driver fD) {
        try {
            super.save(fD);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteFrequentDriver(Frequent_driver fD) {
        try {
            super.delete(fD);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List getFrequentDriversByShipperId(int shipperId) {
        return super.getList(Frequent_driver.class,"shipper_id",shipperId+"");
    }
}
