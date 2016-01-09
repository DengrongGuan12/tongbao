package dao.impl;

import dao.ContactsDao;
import model.Contacts;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class ContactsDaoImpl extends BaseDaoImpl implements ContactsDao{
    public List<Object> getShipperContacts(int userId) {
        return super.getList(Contacts.class,"shipperId",userId+"");
    }

    public List<Object> getDriverContacts(int userId) {
        return super.getList(Contacts.class,"driverId",userId+"");
    }

    public boolean addContacts(Object o) {
        return false;
    }
}
