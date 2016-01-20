package dao.impl;

import dao.AccountDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import model.Account;

import java.util.List;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {


    public List<Object> getAccounts(int userId) {
        return super.getList(Account.class,"userId",userId+"");
    }

    public int getTotalAccountNum() {
        int count = ((Long)getSession().createQuery("select count(*) from Account").uniqueResult()).intValue();
        return count;
    }

    public boolean addAccount(Account account) {
        try {
            super.save(account);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public List getRecentAccounts(int num) {
        Session session=getSession();
        String strSQL="from Account as a order by buildTime desc";
        Query query = session.createQuery(strSQL);
        query.setFirstResult(0);
        query.setMaxResults(num);
        return query.list();
    }

    public boolean findAccount() {
        return false;
    }

    public boolean updateAccount(Account account) {

        try{
            super.update(account);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteAccount(int id) {
        return false;
    }
}
