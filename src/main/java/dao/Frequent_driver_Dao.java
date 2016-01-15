package dao;

import model.Frequent_driver;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
public interface Frequent_driver_Dao {
    public List getFrequentDriversByShipperId(int shipperId);
    public boolean addFrequentDriver(Frequent_driver fD);
    public boolean deleteFrequentDriver(Frequent_driver fD);
}
