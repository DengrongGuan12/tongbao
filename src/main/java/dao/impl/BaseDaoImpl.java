package dao.impl;

import dao.BaseDao;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dengrong on 2015/12/29.
 */
@Repository
@EnableTransactionManagement
@Transactional
public class BaseDaoImpl implements BaseDao {
    @Autowired
    protected SessionFactory sessionFactory;
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Session getNewSession() {
        return sessionFactory.openSession();
    }

    public void flush() {
        getSession().flush();

    }

    public void clear() {
        getSession().clear();
    }

    public Object load(Class c, int id) {
        Session session = getSession();
//        Session session = HibernateUtil.getSession();
        return session.get(c, id);
    }

    public List getAllList(Class c) {
        String hql = "from " + c.getName();
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    public List getList(Class c, String para, String val) {
        String hql = "from " + c.getName() + " where " + para + " = " + val;
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    public List getList(Class c, String[] para, String[] val, String[] operation, String order, boolean isAsc) {
        String hql = "from " + c.getName();
        for (int i = 0; i < para.length; i++) {
            if (i == 0) {
                hql = hql + " where " + para[i] + " " + operation[i] + " "
                        + val[i];
            } else {
                hql = hql + " and " + para[i] + " " + operation[i] + " "
                        + val[i];
            }
        }
        hql = hql + " order by " + order;
        if (!isAsc) {
            hql += " desc";
        }
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    public List getList(Class c, String[] para, String[] val, String[] operation) {
        String hql = "from " + c.getName();
        for (int i = 0; i < para.length; i++) {
            if (i == 0) {
                hql = hql + " where " + para[i] + " " + operation[i] + " "
                        + val[i];
            } else {
                hql = hql + " and " + para[i] + " " + operation[i] + " "
                        + val[i];
            }
        }
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    public List getList(Class[] c, String[] para, String[] val, String[] operation, String order, boolean isAsc) {
        String hql = "from";
        for (int i = 0; i < c.length; i++) {
            if (i == 0) {
                hql = hql + " " + c[i].getName();
            } else {
                hql = hql + ", " + c[i].getName();
            }
        }
        for (int i = 0; i < para.length; i++) {
            if (i == 0) {
                hql = hql + " where " + para[i] + " " + operation[i] + " "
                        + val[i];
            } else {
                hql = hql + " and " + para[i] + " " + operation[i] + " "
                        + val[i];
            }
        }
        hql = hql + " order by " + order;
        if (!isAsc) {
            hql += " desc";
        }
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    public Long getTotalCount(Class c) {
        Session session = getNewSession();
        String hql = "select count(*) from " + c.getName();
        Long count = (Long) session.createQuery(hql).uniqueResult();
        session.close();
        return count != null ? count.longValue() : 0;
    }

    public void save(Object bean) {
        try {
            Session session = getNewSession();
            session.save(bean);
            session.flush();
            session.clear();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(Object bean) {
        Session session = getNewSession();
        session.update(bean);
        session.flush();
        session.clear();
        session.close();
    }

    public void delete(Object bean) {
        Session session = getNewSession();
        session.delete(bean);
        session.flush();
        session.clear();
        session.close();
    }

    public void delete(Class c, String id) {
        Session session = getNewSession();
        Object obj = session.get(c, id);
        session.delete(obj);
        flush();
        clear();
    }

    public void delete(Class c, String[] ids) {
        for (String id : ids) {
            Object obj = getSession().get(c, id);
            if (obj != null) {
                getSession().delete(obj);
            }
        }
    }

    public void delete(Class c, String para, String val) {
        Session session = getNewSession();
        org.hibernate.Transaction tx = session.beginTransaction();// 开始一个事务
        Query query = session.createQuery("delete from " + c.getName()
                + " where " + para + "=" + val);
        // 跟据条件生成HQL语句
        query.executeUpdate();
        tx.commit();// 提交事务
        session.flush();
        session.clear();
        session.close();
    }

    public Object getLast(Class c) {
        String hql = "from " + c.getName() + " ORDER BY id DESC";
        Session session = getSession();
        return session.createQuery(hql).list().get(0);
    }
}
