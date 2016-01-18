package dao.impl;

import dao.AccountDao;
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

    public boolean findAccount() {
        return false;
    }

    public boolean updateAccount(Account account) {
        return false;
    }

    public boolean deleteAccount(int id) {
        return false;
    }
}
