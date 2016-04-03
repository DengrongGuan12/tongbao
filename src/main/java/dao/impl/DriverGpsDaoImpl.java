package dao.impl;

import affairs.Impl.HibernateUtil;
import dao.DriverGpsDao;
import model.DriverGps;
import model.UserToken;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/4/3.
 */
@Repository
public class DriverGpsDaoImpl extends BaseDaoImpl implements DriverGpsDao {
    @Override
    public List<DriverGps> getAllDriversPosition() {
        return super.getAllList(DriverGps.class);
    }

    @Override
    public boolean updateMyPosition(DriverGps positionInfo) {

        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("update driver_gps set collect_time= :ct, receive_time= :rt, lat= :lat, lng=: lng where driver_id= :di");
            query.setParameter("ct", positionInfo.getCollect_time());
            query.setParameter("rt", positionInfo.getReceive_time());
            query.setParameter("lat", positionInfo.getLat());
            query.setParameter("lng", positionInfo.getLng());
            query.setParameter("di", positionInfo.getDriver_id());
            int result = query.executeUpdate();
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }
}
