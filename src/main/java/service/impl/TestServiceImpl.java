package service.impl;

import affairs.OrderAffairs;
import dao.MessageDao;
import dao.OrderDao;
import model.Message;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.TestService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by I322233 on 3/14/2016.
 */
@Service
public class TestServiceImpl implements TestService {
    public static String[] districts = {"栖霞区","鼓楼区","秦淮区","雨花台区","建邺区","玄武区","浦口区","六合区","江宁区","溧水区","高淳区"};
    public static String[] roads = {"中山北路","湖南路","上海路","莫愁路","宁杭高速","金桥路","汉口路","珠江路","广州路","北京东路","北京西路","中央路","中央南路"};
    public static int num = 20;

    @Autowired
    OrderAffairs orderAffairs;
    @Autowired
    MessageDao messageDao;
    @Autowired
    OrderDao orderDao;
    public void getAutoFinishOrder(){
        List list = orderDao.getAllAutoFinishOrder();
        System.out.println(list.size());
        for (int i=0;i<list.size();i++){
            Order order = (Order)list.get(i);
            System.out.println(order.getLoadTime());
        }
    }

    public void genOrderData() {
        for(int i = 0;i<20;i++){
            int district = (int)(districts.length*Math.random());
            int road =(int) (roads.length*Math.random());
            String number = (int)(Math.random()*num)+"号";
            String fromPlace = "南京市"+districts[district]+roads[road]+number;
            district = (int)(districts.length*Math.random());
            road =(int) (roads.length*Math.random());
            number = (int)(num*Math.random())+"号";
            String toPlace = "南京市"+districts[district]+roads[road]+number;
//            String buildTime = (new Date()).toString();
            String fromContactName = "联系人"+(int)(Math.random()*100)+"号";
            String fromContactPhone = "15950570277";
            String toContactName = "联系人"+(int)(Math.random()*100)+"号";
            String toContactPhone = "15850943849";
            double goodsSize = Math.random()*10;
            String goodsType = "食品";
            double goodsWeight = Math.random()*10;
//            String loadTime = (new Date()).toString();
            int payType = (int)(Math.random()+0.5);
            double price = Math.random()*100;
            String remark = "轻拿轻放谢谢！";
            double fromPlaceLat = Math.random()*1.23+31.4;
            double fromPlaceLng = Math.random()*0.92+118.22;
            double toPlaceLat = Math.random()*1.23+31.4;
            double toPlaceLng = Math.random()*0.92+118.22;
            int shipperId = (int) (Math.random()*10)+2;
            int state = 0;
            Order order = new Order();
            order.setAddressFrom(fromPlace);
            order.setAddressTo(toPlace);
            order.setBuildTime(new Timestamp(new Date().getTime()));
            order.setFrom_contact_name(fromContactName);
            order.setFrom_contact_phone(fromContactPhone);
            order.setGoodsSize(goodsSize);
            order.setGoodsType(goodsType);
            order.setGoodsWeight(goodsWeight);
            order.setLoadTime(new Timestamp(new Date().getTime()));
            order.setPayType((byte)payType);
            order.setPrice(price);
            order.setTo_contact_name(toContactName);
            order.setTo_contact_phone(toContactPhone);
            order.setShipperId(shipperId);
            order.setRemark(remark);
            order.setState((byte)state);
            order.setFromLat(fromPlaceLat);
            order.setFromLng(fromPlaceLng);
            order.setToLat(toPlaceLat);
            order.setTolng(toPlaceLng);
            int types_num = (int)(Math.random()*9);
            String types[] = new String[types_num+1];
            for(i = 0;i<=types_num;i++){
                types[i] = (int)(Math.random()*9)+"";
            }
            int order_id =orderAffairs.saveOrderAffairs(order,types);
            System.out.println("订单的id是："+order_id);

        }


    }

    public void genMessageData(int id) {
        for (int i = 0;i<10;i++){
            Message message = new Message();
            message.setType((byte)3);
            String content = "转账"+(i+100)+"元成功，请核对账户余额!";
            message.setContent(content);
            message.setHas_read((byte)0);
            message.setObject_id(0);
            message.setTime(new Timestamp(new Date().getTime()));
            message.setUser_id(id);
            messageDao.addMessage(message);
        }
    }

}
