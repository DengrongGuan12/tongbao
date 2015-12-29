package dao;

import model.User;

/**
 * Created by dengrong on 2015/12/29.
 */
public interface UserDao {
    public boolean registerUser(User user);
    public User queryUserByPhone(String phone_number);
    public User getUserById(int id);
}
