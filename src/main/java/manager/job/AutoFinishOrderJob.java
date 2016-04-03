package manager.job;

import affairs.OrderAffairs;
import dao.OrderDao;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cg on 2016/3/20.
 */
@Component("autoFinishOrderJob")
public class AutoFinishOrderJob {
    private List<Order> allAutoFinishOrders;
    @Autowired
    private OrderAffairs orderAffairs;
    @Autowired
    private OrderDao orderDao;
    public void autoFinishOrder(){
        allAutoFinishOrders = orderAffairs.getAllAutoFinishOrders();
        System.out.println("需要自动结束的订单个数为：" + allAutoFinishOrders.size());
        if(allAutoFinishOrders.size()!=0){
            if(orderAffairs.autoFinishOrderAffairs(allAutoFinishOrders)){
                allAutoFinishOrders.clear();
            }
        }

    }

}
