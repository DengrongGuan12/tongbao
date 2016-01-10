package dao.impl;

import dao.Frequent_address_Dao;
import model.Frequent_addresses;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
@Repository
public class Frequent_address_Dao_Impl extends BaseDaoImpl implements Frequent_address_Dao{
    public boolean addFrequentAddress(Frequent_addresses fa) {
        try {
            super.save(fa);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List getFrequentAddressesByShipperId(int userId) {
        return super.getList(Frequent_addresses.class,"shipper_id",userId+"");
    }
}
