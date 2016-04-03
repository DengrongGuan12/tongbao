package dao;

import model.DriverGps;

import java.util.List;

/**
 * Created by cg on 2016/4/3.
 */
public interface DriverGpsDao {
    public List<DriverGps> getAllDriversPosition();
    public boolean updateMyPosition(DriverGps positionInfo);
}
