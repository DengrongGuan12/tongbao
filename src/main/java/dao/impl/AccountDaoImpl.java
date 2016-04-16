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


    public List<Account> getAccountsByMonth(int userId, int year, int month) {
        Session session = getSession();
        String str = "Select acc from account acc where userId = :userId and month(acc.buildTime) = :month and year(acc.buildTime) = :year";
        Query query = session.createQuery(str);
        query.setInteger("userId",userId);
        query.setInteger("month",month);
        query.setInteger("year",year);
        return query.list();
    }

    public List<Object> getAccounts(int userId) {
        return super.getList(Account.class,"userId",userId+"");
    }

    public int getTotalAccountNum() {
        int count = ((Long)getSession().createQuery("select count(*) from account").uniqueResult()).intValue();
        return count;
    }


    public List getAllAccounts() {
        return super.getAllList(Account.class);
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
        String strSQL="from account as a order by buildTime desc";
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
        try {
            super.delete(Account.class,id);
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
