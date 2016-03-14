package service.impl;

import org.springframework.stereotype.Service;
import service.TestService;

import java.util.Date;

/**
 * Created by I322233 on 3/14/2016.
 */
@Service
public class TestServiceImpl implements TestService {
    public static String[] districts = {"栖霞区","鼓楼区","秦淮区","雨花台区","建邺区","玄武区","浦口区","六合区","江宁区","溧水区","高淳区"};
    public static String[] roads = {"中山北路","湖南路","上海路","莫愁路","宁杭高速","金桥路","汉口路","珠江路","广州路","北京东路","北京西路","中央路","中央南路"};
    public static int num = 20;
    @Override
    public void genTestData() {
        // TODO: 3/14/2016  生成测试数据
        int district = (int)(districts.length*Math.random());
        int road =(int) (roads.length*Math.random());
        String number = (int)(Math.random()*num)+"号";
        String fromPlace = "南京市"+districts[district]+roads[road]+number;
        district = (int)(districts.length*Math.random());
        road =(int) (roads.length*Math.random());
        number = (int)(num*Math.random())+"号";
        String toPlace = "南京市"+districts[district]+roads[road]+number;
        String buildTime = (new Date()).toString();
        String fromContactName = "联系人"+(int)(Math.random()*100)+"号";
        String fromContactPhone = "15950570277";
        String toContactName = "联系人"+(int)(Math.random()*100)+"号";
        String toContactPhone = "15850943849";
        double goodsSize = Math.random()*10;
        String goodsType = "食品";
        double goodsWeight = Math.random()*10;
        String loadTime = (new Date()).toString();
        int payType = (int)(Math.random()+0.5);
        double price = Math.random()*100;
        String remark = "轻拿轻放谢谢！";

    }
}
