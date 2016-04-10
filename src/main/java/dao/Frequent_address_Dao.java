package dao;

import model.Frequent_addresses;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
public interface Frequent_address_Dao {
    public List getFrequentAddressesByShipperId(int userId);
    public boolean addFrequentAddress(Frequent_addresses fa);
    public boolean deleteFrequentAddressByShipperId(int Fid,int shipperId);
    public boolean updateFrequentAddress(Frequent_addresses frequent_addresses);
    public Frequent_addresses getFrequent_address(int id);
}
