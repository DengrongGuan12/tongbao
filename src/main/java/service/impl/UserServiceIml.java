package service.impl;

import dao.UserDao;
import manager.UserManager;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;
import vo.Account;
import vo.ContactDetail;
import vo.ContactSimple;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cg on 2015/12/29.
 */
@Service
public class UserServiceIml implements UserService {
    UserManager userManager=UserManager.getInstance();
    @Autowired
    private UserDao userDao;


    public User getUserByPhoneNumber(String phoneNumber) {
        return userDao.getUserByPhoneNumber(phoneNumber);
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    /*
        注册
         */
    public boolean register(String phoneNumber, String password,Byte type) {
        User user=new User(phoneNumber,password,type);
        return userDao.registerUser(user);
    }

    /*
    登录,登录成功返回用户信息,否则返回null
     */
    public User login(String phoneNumber, String password) {
        User userTemp=userDao.getUserByPhoneNumber(phoneNumber);
        if(userTemp!=null&&userTemp.getPassword().equals(password)){
            String token=userManager.addToOnlineList(userTemp.getId(),userTemp.getType());
            userTemp.setToken(token);
            return userTemp;
        }else {
            return null;
        }

    }


    /*
    根据判断用户是否登录，如果已经登录返回用户id,否则返回0
     */
    public int hasLogin(String token) {
        return userManager.getUserIdByKey(token);
    }

    /*
    修改用户昵称,成功返回true,否则返回false
     */
    public boolean changeNickName(int id, String nickName) {
        User userTemp=userDao.getUserById(id);
        if(userTemp!=null){
            userTemp.setNick_name(nickName);
            return userDao.updateUser(userTemp);
        }else {
            return false;
        }
    }

    /*
    修改用户密码,成功返回true
     */
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        User userTemp=userDao.getUserById(id);
        if(userTemp!=null&&userTemp.getPassword().equals(oldPassword)){
            userTemp.setPassword(newPassword);
            return userDao.updateUser(userTemp);
        }else {
            return false;
        }

    }

    /*
    修改用户头像 ,成功返回true
     */
    public boolean changeIconUrl(int id, String url) {
        User userTemp=userDao.getUserById(id);
        if(userTemp!=null){
            userTemp.setIcon(url);
            return userDao.updateUser(userTemp);
        }else {
            return false;
        }
    }

    public List getUserAccount(int userId) {
        List list = new ArrayList();
        Account account = new Account();
        account.setMoney(12.3);
        account.setType(0);
        account.setTime("2013-11-11 00:00:00");
        account.setOrder(null);
        list.add(account);
        return list;
    }

    public List getContacts(int userId) {
        List list = new ArrayList();
        ContactSimple contactSimple1 = new ContactSimple();
        contactSimple1.setId(1);
        contactSimple1.setNickName("dsfsdf");
        contactSimple1.setPhoneNum("1232143242");
        contactSimple1.setType(0);
        ContactSimple contactSimple2 = new ContactSimple();
        contactSimple2.setId(2);
        contactSimple2.setType(1);
        contactSimple2.setNickName("sdfsdfdsf");
        contactSimple2.setPhoneNum("2143234345345");
        list.add(contactSimple1);
        list.add(contactSimple2);
        return list;
    }

    public ContactDetail getContactDetail(int contactId) {
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setNickName("sdfsdf");
        contactDetail.setPhoneNum("121212121212");
        contactDetail.setType(0);
        contactDetail.setSex(0);
        contactDetail.setRegTime("2015-11-02 00:00:00");
        return contactDetail;
    }
}
