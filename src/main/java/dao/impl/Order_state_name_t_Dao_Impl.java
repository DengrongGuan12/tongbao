package dao.impl;

import dao.Order_state_name_t_Dao;
import model.Order_state_name_t;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cg on 2016/1/9.
 */
@Repository
public class Order_state_name_t_Dao_Impl extends BaseDaoImpl implements Order_state_name_t_Dao {
    public Map<Byte, String> getAllOrderStateName() {
        Map<Byte,String> allStateName = new HashMap<Byte, String>();
        List list = super.getAllList(Order_state_name_t.class);
        for(int i = 0;i<list.size();i++){
            Order_state_name_t temp = (Order_state_name_t) list.get(i);
            allStateName.put(temp.getState(),temp.getName());
        }
        return allStateName;
    }
}
