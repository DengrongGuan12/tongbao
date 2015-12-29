package service;

import model.User;

/**
 * Created by cg on 2015/12/29.
 */
public interface UserService {
    public boolean registerUser(User user);
    public User getUserById(int id);

}
