package affairs.Impl;

import affairs.UserAffairs;
import model.Account;
import model.Driver_auth;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


/**
 * Created by cg on 2016/3/9.
 */
@Repository
public class UserAffairsImpl implements UserAffairs {

    public boolean withdraw(User user, Account account) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(user);
            session.save(account);
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public boolean recharge(User user, Account account) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(user);
            session.save(account);
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public boolean register(User user) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(user);
            int type = user.getType();
            if(type == 1){

                Driver_auth driver_auth = new Driver_auth();
                driver_auth.setUserId(user.getId());
                driver_auth.setAuthState(new Byte("0"));
                session.save(driver_auth);
            }
            tx.commit();
            return true;
        }catch (Exception e){
            if(tx!=null) tx.rollback();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
    }
}
