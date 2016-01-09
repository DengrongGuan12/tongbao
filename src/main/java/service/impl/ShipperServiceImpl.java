package service.impl;

import org.springframework.stereotype.Service;
import service.ShipperService;
import vo.Address;
import vo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class ShipperServiceImpl implements ShipperService{

    public boolean evaluateOrder(int userId, int id, int evaluateType, String content) {
        return false;
    }

    public List getFrequentDrivers(int userId) {
        List list = new ArrayList();
        User user = new User();
        user.setNickName("sdfsdf");
        list.add(user);
        return list;

    }

    public List getFrequentAddresses(int userId) {
        List list = new ArrayList();
        Address address = new Address();
        address.setLat(32.08);
        address.setLng(108.09);
        address.setName("南京市鼓楼区");
        list.add(address);
        return list;
    }

}
