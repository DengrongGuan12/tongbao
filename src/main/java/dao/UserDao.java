package dao;

import model.User;

import java.util.List;

/**
 * Created by dengrong on 2015/12/29.
 */
public interface UserDao {
    public boolean registerUser(User user);
    public User getUserByPhoneNumber(String phoneNumber);
    public User getUserById(int id);
    public boolean updateUser(User user);
    public boolean deleteUser(int id);
    public List searchDriver(String phoneNumber);

}
