package service.impl;

import org.springframework.stereotype.Service;
import service.AccountService;
import vo.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I322233 on 1/27/2016.
 */
@Service
public class AccountServiceImpl implements AccountService {

    // TODO: 1/27/2016
    //获取所有订单, 使用account 需要设置的信息有 id, 用户id, 用户联系方式, 账单生成时间, 账单类型, 金额, 订单id
    public List getAllAccounts() {
        List list = new ArrayList();
        for(int i = 0;i<5;i++){
            Account account = new Account();
            account.setId(1);
            account.setUserId(1);
            account.setUserPhoneNum("324234234324");
            account.setTime("2015-11-11 11:11:11");
            account.setTypeStr("付款");
            account.setMoney(123.4);
            account.setOrderId(1);
            list.add(account);
        }
        return list;
    }

    public boolean deleteAccount(int id) {
        return true;
    }
}
