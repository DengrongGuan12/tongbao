package dao;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
public interface Frequent_driver_Dao {
    public List getFrequentDriversByShipperId(int shipperId);
}
