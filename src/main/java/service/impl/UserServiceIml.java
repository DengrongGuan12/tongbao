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

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    /*
    注册
     */
    public boolean register(String phoneNumber, String password) {
        return false;
    }

    /*
    登录,登录成功返回用户信息,否则返回null
     */
    public User login(String phoneNumber, String password) {
        return null;
    }


    /*
    根据判断用户是否登录，如果已经登录返回用户id,否则返回0
     */
    public int hasLogin(String token) {
        return 0;
    }

    /*
    修改用户昵称,成功返回true,否则返回false
     */
    public boolean changeNickName(int id, String nickName) {
        return false;
    }

    /*
    修改用户密码,成功返回true
     */
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        return false;
    }

    /*
    修改用户头像 ,成功返回true
     */
    public boolean changeIconUrl(int id, String url) {
        return false;
    }
}
