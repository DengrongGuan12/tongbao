package dao;

import java.util.Map;

/**
 * Created by cg on 2015/12/31.
 */
public interface Account_type_name_t_Dao {
    public String getAccountTypeById(Byte type);
    public Map getAllAccountTypes();
}
