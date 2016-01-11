package dao.impl;

import dao.OrderTruckTypeDao;
import model.OrderTruckType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by I322233 on 1/11/2016.
 */
@Repository
public class OrderTruckTypeDaoImpl extends BaseDaoImpl implements OrderTruckTypeDao {
    public List getTruckTypesByOrderId(int orderId) {
        try{
            return super.getList(OrderTruckType.class,"order_id",orderId+"");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
