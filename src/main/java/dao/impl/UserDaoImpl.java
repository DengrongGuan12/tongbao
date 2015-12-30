package dao.impl;

import dao.BaseDao;
import dao.UserDao;
import model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by dengrong on 2015/12/29.
 */
@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private BaseDao baseDao;
    public boolean registerUser(User user){
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(user);
            tx.commit();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }finally {
            HibernateUtil.flushClearClose();
        }
    }
    public User queryUserByPhone(String phone_number){
        return null;
    }

    public User getUserById(int id) {
        return (User) baseDao.load(User.class,id);
    }
}
