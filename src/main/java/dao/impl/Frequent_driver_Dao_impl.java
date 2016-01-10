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
    public List getFrequentDriversByShipperId(int shipperId) {
        return super.getList(Frequent_driver.class,"shipper_id",shipperId+"");
    }
}
