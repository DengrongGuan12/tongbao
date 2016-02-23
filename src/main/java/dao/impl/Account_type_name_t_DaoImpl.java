package dao.impl;

import dao.Account_type_name_t_Dao;
import model.Account_type_name_t;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cg on 2016/1/3.
 */
@Repository
public class Account_type_name_t_DaoImpl extends BaseDaoImpl implements Account_type_name_t_Dao {
    @Override
    public String getAccountTypeById(Byte type) {
        Account_type_name_t account_type_name_t =(Account_type_name_t) super.load(Account_type_name_t.class,type);
        return account_type_name_t.getName();
    }

    @Override
    public Map getAllAccountTypes() {
        Map <Byte,String> map = new HashMap();
        List list = getAllList(Account_type_name_t.class);
        for(int i = 0;i<list.size();i++)
        {
            Account_type_name_t account_type_name_t = (Account_type_name_t) list.get(i);
            map.put(account_type_name_t.getType(),account_type_name_t.getName());
        }
        return map;
    }
}
