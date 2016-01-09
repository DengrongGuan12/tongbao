package dao;

import java.util.List;

/**
 * Created by cg on 2015/12/31.
 */
public interface ContactsDao {
    public List<Object>getShipperContacts(int userId);
    public List<Object>getDriverContacts(int userId);
    public boolean addContacts(Object o);

}
