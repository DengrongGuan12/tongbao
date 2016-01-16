package service.impl;

import dao.*;
import manager.UserManager;
import model.*;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;
import vo.*;
import vo.Account;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
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
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private Truck_type_Dao trucks_type_dao;
    @Autowired
    private AccountDao accountDao;


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
        user.setRegister_time(new Timestamp(System.currentTimeMillis()));
        return userDao.registerUser(user);
    }

    /*
    登录,登录成功返回用户信息,否则返回null
     */
    public vo.User login(String phoneNumber, String password, HttpSession session) {
        User userTemp=userDao.getUserByPhoneNumber(phoneNumber);
        if(userTemp!=null&&userTemp.getPassword().equals(password)){
            String token=userManager.addToOnlineList(userTemp.getId(),userTemp.getType());
            vo.User userReturn=new vo.User();
            userReturn.setIconUrl(userTemp.getIcon());
            userReturn.setMoney(userTemp.getMoney());
            userReturn.setNickName(userTemp.getNick_name());
            userReturn.setToken(token);
            userReturn.setPoint(userTemp.getPoint());
            userReturn.setToken(token);
            session.setAttribute("type",userTemp.getType());
            return userReturn;
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


    /**
     *
     * 还缺少获取order的部分
     * @param userId
     * @return
     */
    public List getUserAccount(int userId) {
        List listTemp=accountDao.getAccounts(userId);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            model.Account accountTemp=(model.Account)listTemp.get(i);
            Account account = new Account();
            account.setMoney(accountTemp.getMoney());
            account.setType(accountTemp.getType());
            account.setTime(accountTemp.getBuildTime().toString());

            account.setOrder(null);

            list.add(account);
        }


        return list;
    }

    public List getContacts(int userId) {
        int userType=userManager.getUserType(userId);
        List contacts;
        List returnContacts=new ArrayList();
        if(userType==0){
            contacts=contactsDao.getShipperContacts(userId);
        }else if(userType==1){
            contacts=contactsDao.getDriverContacts(userId);
        }else{
            return null;
        }
        for(int i=0;i<contacts.size();i++){
            Contacts contactsTemp=(Contacts)contacts.get(i);
            User userTemp;
            if(userType==0){
                 userTemp=getUserById(contactsTemp.getDriverId());
            }else{
                 userTemp=getUserById(contactsTemp.getShipperId());
            }
            vo.ContactSimple contactSimple=new vo.ContactSimple();
            contactSimple.setId(userTemp.getId());
            contactSimple.setNickName(userTemp.getNick_name());
            contactSimple.setPhoneNum(userTemp.getPhone_number());
            contactSimple.setType(userTemp.getType());
            returnContacts.add(contactSimple);
        }

        return returnContacts;
    }

    public ContactDetail getContactDetail(int contactId) {
        User userTemp=userDao.getUserById(contactId);
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setId(userTemp.getId());
        contactDetail.setNickName(userTemp.getNick_name());
        contactDetail.setPhoneNum(userTemp.getPhone_number());
        contactDetail.setType(userTemp.getType());
        contactDetail.setRegTime(userTemp.getRegister_time()+"");
        return contactDetail;
    }

    public List getTruckTypes() {
        List listTemp=trucks_type_dao.getAllTruckType();
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            Trucks_type trucksTemp=(Trucks_type)listTemp.get(i);
            TruckType truckType = new TruckType();
            truckType.setType(trucksTemp.getType());
            truckType.setName(trucksTemp.getName());
            truckType.setBasePrice(trucksTemp.getBase_price());
            truckType.setCapacity(trucksTemp.getCapacity());
            truckType.setOverPrice(trucksTemp.getOver_price());
            truckType.setLength(trucksTemp.getLength());
            truckType.setWidth(trucksTemp.getWidth());
            truckType.setHeight(trucksTemp.getHeight());
            list.add(truckType);
        }

        return list;
    }

    public List getMessagesByUserId(int userId) {
        List listTemp=messageDao.getMyMessage(userId);
        List returnList=new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            model.Message messageTemp=(model.Message)listTemp.get(i);
            vo.Message message=new vo.Message();
            message.setContent(messageTemp.getContent());
            message.setHasRead(messageTemp.getHas_read());
            message.setId(messageTemp.getId());
            message.setTime(messageTemp.getTime().toString());
            message.setObjectId(messageTemp.getObject_id());
            message.setType(messageTemp.getType());
            returnList.add(message);
        }
        return returnList;
    }
    public boolean recharge(int userId, double money) {
        User user=getUserById(userId);
        double moneyTemp=user.getMoney();
        user.setMoney(moneyTemp+money);
        model.Account account=new model.Account();
        account.setBuildTime(new Timestamp(System.currentTimeMillis()));
        account.setUserId(userId);
        //0代表充值
        account.setType(new Byte("0"));
        account.setMoney(money);
        accountDao.addAccount(account);
        return userDao.updateUser(user);
    }
}
