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


    public List<Object> showAccount(int userId) {
        return null;
    }
}
