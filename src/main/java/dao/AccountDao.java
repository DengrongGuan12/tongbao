package dao;

import model.Account;

import java.util.List;

/**
 * Created by cg on 2015/12/31.
 */
public interface AccountDao {
    public boolean addAccount(Account account);
    public boolean findAccount();
    public boolean updateAccount(Account account);
    public boolean deleteAccount(int id);
    public List<Object>getAccounts(int id);
    public List getAllAccounts();
    public int getTotalAccountNum();
    public List getRecentAccounts(int num);
    public List<Account>getAccountsByMonth(int userId,int year,int month);
}
