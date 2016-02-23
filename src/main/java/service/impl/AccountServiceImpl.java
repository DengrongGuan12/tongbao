package service.impl;

import dao.AccountDao;
import dao.Account_type_name_t_Dao;
import dao.Driver_auth_Dao;
import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AccountService;
import vo.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by I322233 on 1/27/2016.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Account_type_name_t_Dao account_type_name_t_dao;
    //获取所有订单, 使用account 需要设置的信息有 id, 用户id, 用户联系方式, 账单生成时间, 账单类型, 金额, 订单id
    public List getAllAccounts() {
        List listTemp = accountDao.getAllAccounts();
        List list = new ArrayList();
        Map <Byte,String> allAccountType = account_type_name_t_dao.getAllAccountTypes();
        for(int i = 0;i<listTemp.size();i++){
            Account account = new Account();
            model.Account accountTemp = (model.Account) listTemp.get(i);
            model.User user = userDao.getUserById(accountTemp.getUserId());
            account.setId(accountTemp.getId());
            account.setUserId(accountTemp.getUserId());
            account.setUserPhoneNum(user.getPhone_number());
            account.setTime(accountTemp.getBuildTime().toString());
            account.setTypeStr(allAccountType.get(accountTemp.getType()));
            account.setMoney(accountTemp.getMoney());
            account.setOrderId(account.getOrderId());
            list.add(account);
        }
        return list;
    }

    public boolean deleteAccount(int id) {
        return accountDao.deleteAccount(id);
    }
}
