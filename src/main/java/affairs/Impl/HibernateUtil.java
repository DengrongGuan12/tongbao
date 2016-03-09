package affairs.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by dengrong on 2015/12/29.
 */
@Repository
public class HibernateUtil {
    private static final ThreadLocal<Session> localSession = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public static Session getSession(){
        Session session = localSession.get();
        if(session == null|| !session.isOpen()){
            session = sessionFactory.openSession();
            localSession.set(session);
        }
        return session;
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static void closeSession(){
        Session session = localSession.get();
        localSession.set(null);
        if(session!=null){
            session.close();
        }
    }
    public static void closeSessionFactory(){
        sessionFactory.close();
    }
    public static void flushClearClose(){
        Session currentSession = (Session) localSession.get();
        currentSession.flush();
        currentSession.clear();
        closeSession();
    }

}
