package dao;

import java.util.List;

/**
 * Created by I322233 on 1/11/2016.
 */
public interface OrderTruckTypeDao {
    public List getTruckTypesByOrderId(int orderId);
}
