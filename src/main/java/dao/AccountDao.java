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
    public int getTotalAccountNum();
}
