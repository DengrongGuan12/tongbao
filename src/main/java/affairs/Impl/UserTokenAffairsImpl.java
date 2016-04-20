package affairs.Impl;

import affairs.UserTokenAffairs;
import model.UserToken;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cg on 2016/3/27.
 */
@Repository
public class UserTokenAffairsImpl implements UserTokenAffairs {
    public boolean addAllNewUserTokens(List<UserToken> allNewUserTokens) {

        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 1;
            for(UserToken userToken : allNewUserTokens){
                session.save(userToken);
                i++;
                if(i%20==0){
                    session.flush();
                    session.clear();
                }
            }
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

    public boolean updateAllNewUserTokens(List<UserToken> allNewUserTokens) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i =1;
            for(UserToken userToken : allNewUserTokens){
                Query query = session.createSQLQuery("update user_token set lastLoginTime= :llt, token= :t, userType= :ut where user_id= :ui");
                query.setParameter("llt", userToken.getLastLoginTime());
                query.setParameter("t", userToken.getToken());
                query.setParameter("ut", userToken.getUserType());
                query.setParameter("ui", userToken.getUserId());
                int result = query.executeUpdate();
//                session.update(userToken);
                i++;
                if(i%20==0){
                    session.flush();
                    session.clear();
                }
            }
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

    public List<UserToken> getAllUserToken() {
        Session session = HibernateUtil.getSession();
        String hql = "from " + UserToken.class.getName();
        try{
            return session.createQuery(hql).list();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            HibernateUtil.closeSession();
        }

    }
}
