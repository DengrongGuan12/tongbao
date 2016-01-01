package dao;

import model.User;

/**
 * Created by dengrong on 2015/12/29.
 */
public interface UserDao {
    public boolean registerUser(User user);
    public User getUserByPhoneNumber(String phoneNumber);
    public User getUserById(int id);
    public boolean updateUser(User user);
    public boolean deleteUser(int id);

}
