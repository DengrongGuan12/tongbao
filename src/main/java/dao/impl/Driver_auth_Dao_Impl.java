package dao.impl;

import dao.Driver_auth_Dao;
import model.Driver_auth;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class Driver_auth_Dao_Impl extends BaseDaoImpl implements Driver_auth_Dao{
    public boolean addDriverAuth(Driver_auth driver_auth) {
        try {
            super.save(driver_auth);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Driver_auth getDriverAuthByTruckNum(String truckNum) {
//        List<Driver_auth> driver_auths = super.getList(Driver_auth.class,"truck_num","'"+truckNum+"'");
//        return driver_auths.get(0);
        return null;
    }

    public boolean updateDriverAuth(Driver_auth driver_auth) {
        try{
            super.update(driver_auth);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List getDriverByTruckType(String truckType) {
        String hql = "from " + Driver_auth.class.getName() + " where authState = 2 and type = " + truckType;
        Session session = getSession();
        return session.createQuery(hql).list();
    }
}
