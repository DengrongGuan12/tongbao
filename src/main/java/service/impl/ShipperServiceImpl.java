package service.impl;

import org.springframework.stereotype.Service;
import service.ShipperService;

/**
 * Created by dengrong on 2016/1/3.
 */
@Service
public class ShipperServiceImpl implements ShipperService{

    public boolean evaluateOrder(int userId, int id, int evaluateType, String content) {
        return false;
    }

    public boolean recharge(int userId, double money) {
        return false;
    }
}
