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
    //正在进行的订单，超过装载实践7天的
    private List<Order> allAutoFinishOrders;
    //未完成的订单，超过装载时间的
    private List<Order> allLoadTimeOutOrders;
    @Autowired
    private OrderAffairs orderAffairs;
    @Autowired
    private OrderDao orderDao;
    public void autoFinishOrder(){
        System.out.println("----------------------我是分割线--------------------------");
        allAutoFinishOrders = orderAffairs.getAllAutoFinishOrders();
        System.out.println("正在进行订单中需要自动结束的个数为：" + allAutoFinishOrders.size());
        allLoadTimeOutOrders = orderAffairs.getAllLoadTimeOutOrders();
        System.out.println("未完成订单中需要自动结束的个数为：" + allLoadTimeOutOrders.size());
        if(allAutoFinishOrders.size()!=0){
            if(orderAffairs.autoFinishOrderAffairs(allAutoFinishOrders)){
                allAutoFinishOrders.clear();
            }
        }
        if(allLoadTimeOutOrders.size()!=0){
            if(orderAffairs.autoFinishTimeOutAffairs(allLoadTimeOutOrders)){
                allLoadTimeOutOrders.clear();
            }
        }

    }

}
