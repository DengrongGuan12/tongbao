package dao;

import org.hibernate.Session;

import java.util.List;

/**
 * Created by dengrong on 2015/12/29.
 */
public interface BaseDao {
    public Session getSession();

    public Session getNewSession();

    public void flush();

    public void clear();

    public Object load(Class c, String id);

    public List getAllList(Class c);

    public List getList(Class c, String para, String val);

    public List getList(Class c, String[] para, String[] val,
                        String[] operation, String order, boolean isAsc);

    public List getList(Class c, String[] para, String[] val, String[] operation);

    public List getList(Class[] c, String[] para, String[] val,
                        String[] operation, String order, boolean isAsc);

    public Long getTotalCount(Class c);

    public void save(Object bean);

    public void update(Object bean);

    public void delete(Object bean);

    public void delete(Class c, String id);

    public void delete(Class c, String[] ids);

    public void delete(Class c, String para, String val);

    public Object getLast(Class c);
}
