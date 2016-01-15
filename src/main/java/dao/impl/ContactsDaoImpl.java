package dao.impl;

import dao.ContactsDao;
import model.Contacts;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class ContactsDaoImpl extends BaseDaoImpl implements ContactsDao{
    public List<Object> getShipperContacts(int userId) {
        String hql = "from " + Contacts.class.getName() + " where shipperId = " + userId + " and  shipperToDriver = " + new Byte("1");
        Session session = getSession();
        return session.createQuery(hql).list();

    }

    public List<Object> getDriverContacts(int userId) {
        String hql = "from " + Contacts.class.getName() + " where driverId = " + userId + " and  driverToShipper = " + new Byte("1");
        Session session = getSession();
        return session.createQuery(hql).list();

    }

    public boolean addContacts(Object o) {
        return false;
    }
}
