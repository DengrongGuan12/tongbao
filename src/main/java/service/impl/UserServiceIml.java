package service.impl;

import dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;



/**
 * Created by cg on 2015/12/29.
 */
@Service
public class UserServiceIml implements UserService {

    @Autowired
    private UserDao userDao;
    public boolean registerUser(User user){
        try {
            userDao.registerUser(user);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
