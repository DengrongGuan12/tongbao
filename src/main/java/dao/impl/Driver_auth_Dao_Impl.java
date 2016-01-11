package dao.impl;

import dao.Driver_auth_Dao;
import model.Driver_auth;
import org.springframework.stereotype.Repository;

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
}
