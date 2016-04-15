package dao.impl;

import dao.Frequent_address_Dao;
import model.Frequent_addresses;
import org.hibernate.Query;
import org.hibernate.Session;
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

    public boolean deleteFrequentAddressByShipperId(int Fid, int shipperId) {
        try{
            Session session = getSession();
            String hql = "delete from frequent_address where shipper_id= :sid AND id= :Fid";
            Query query = session.createQuery(hql);
            query.setInteger("sid",shipperId);
            query.setInteger("Fid",Fid);
            System.out.println(query.executeUpdate());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFrequentAddress(Frequent_addresses frequent_addresses) {
        try{
            super.update(frequent_addresses);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Frequent_addresses getFrequent_address(int id) {
        return (Frequent_addresses)super.load(Frequent_addresses.class,id);
    }
}
