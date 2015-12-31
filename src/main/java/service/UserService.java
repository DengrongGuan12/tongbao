package service;

import model.User;

/**
 * Created by cg on 2015/12/29.
 */
public interface UserService {
    public boolean registerUser(User user);
    public User getUserById(int id);
    public boolean register(String phoneNumber, String password);
    public User login(String phoneNumber,String password);
    public int hasLogin(String token);
    public boolean changeNickName(int id, String nickName);
    public boolean changePassword(int id, String oldPassword, String newPassword);
    public boolean changeIconUrl(int id, String url);

}
