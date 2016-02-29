package service.impl;

import dao.*;
import manager.UserManager;
import model.*;
import model.User;
import org.hibernate.Transaction;
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
    @Autowired
    private Driver_auth_Dao driver_auth_dao;


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
        //TODO 限制密码最小长度为8
        User user=new User(phoneNumber,password,type);
        user.setRegister_time(new Timestamp(System.currentTimeMillis()));
        if(userDao.registerUser(user)){
            if(type.equals(1)){
                Driver_auth driver_auth = new Driver_auth();
                driver_auth.setUserId(user.getId());
                driver_auth.setAuthState(new Byte("0"));
                driver_auth_dao.addDriverAuth(driver_auth);
            }
            return true;
        }

        return false;
    }

    /*
    登录,登录成功返回用户信息,否则返回null
     */
    public vo.User login(String phoneNumber, String password, int type, HttpSession session) {
        //TODO　添加用户类型，以区分不同的客户端，０货主　１司机
        User userTemp=userDao.getUserByPhoneNumber(phoneNumber);
        if(userTemp!=null&&userTemp.getPassword().equals(password)){
            String token=userManager.addToOnlineList(userTemp.getId(),userTemp.getType());
            vo.User userReturn=new vo.User();
            userReturn.setIconUrl(userTemp.getIcon());
            userReturn.setMoney(userTemp.getMoney());
            userReturn.setNickName(userTemp.getNick_name());
            userReturn.setPoint(userTemp.getPoint());
            userReturn.setPhoneNum(userTemp.getPhone_number());
            userReturn.setRegisterTime(userTemp.getRegister_time()+"");
            userReturn.setId(userTemp.getId());
            userReturn.setToken(token);
            session.setAttribute("type", userTemp.getType());
            session.setAttribute("name", userTemp.getNick_name());
            session.setAttribute("id", userTemp.getId());
            session.setAttribute("password", userTemp.getPassword());
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
        //TODO 限制密码最小长度小于8
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

    //获取货主总人数
    public int getTotalShipperNum() {
        return userDao.getTotalShipperNum();
    }

    //获取司机总人数
    public int getTotalDriverNum() {
        return userDao.getTotalDriverNum();
    }

    //获取总的账单数量
    public int getTotalAccountNum() {
        return accountDao.getTotalAccountNum();
    }

    //获取最近注册的用户,type 是用户类型, num 是需要获取的数量, 返回类型是userSimple 的列表, 按注册时间倒序排序, 注意userSimple添加了一个属性注册时间
    public List getRecentRegUsers(Byte type, int num) {
        List listTemp = userDao.getRecentRegUsers(type,num);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            User user = (User) listTemp.get(i);
            UserSimple simple = new UserSimple();
            simple.setId(user.getId());
            simple.setPhoneNum(user.getPhone_number());
            simple.setNickName(user.getNick_name());
            simple.setRegisterTime(user.getRegister_time().toString());
            list.add(simple);

        }
        return list;
    }


    /*
    返回最近生成的账单, 使用Account,按时间倒序,需要设置的值有
    id, type, time, money,
    typeStr 根据type生成,对应关系看一下数据库的表
    有这几种类型 充值,提现,支付,收益,退款
     */
    // : 1/20/2016
    public List getRecentAccounts(int num) {
        List listTemp = accountDao.getRecentAccounts(num);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            model.Account accountTemp = (model.Account)listTemp.get(i);
            vo.Account account = new vo.Account();
            account.setId(accountTemp.getId());
            account.setMoney(accountTemp.getMoney());
            account.setTime(accountTemp.getBuildTime().toString());
            int type = accountTemp.getType();
            account.setType(type);
            String typeStr;
            switch (type){
                case 0:
                    typeStr="充值";
                case 1:
                    typeStr="提现";
                case 2:
                    typeStr="支付";
                case 3:
                    typeStr="收益";
                case 4:
                    typeStr="退款";
                default:
                    typeStr="";
            }
            account.setTypeStr(typeStr);
            list.add(account);


        }
        return list;
    }



    // : 1/20/2016
    /*
    删除用户,注意如果是司机则连带删除它的认证信息
     */
    public boolean deleteUser(int userId) {
        User userTemp = userDao.getUserById(userId);
        Byte type = userTemp.getType();
        if(type.equals(1)){
            driver_auth_dao.deleteDriverAuth(userId);
        }
        return userDao.deleteUser(userId);
    }

    // : 1/20/2016
    //重置某个用户的密码
    public boolean resetPassword(int userId, String newPassword) {
        //TODO 限制密码长度大于等于8
        User user = userDao.getUserById(userId);
        user.setPassword(newPassword);
        return userDao.updateUser(user);
    }

    // : 1/20/2016Done
    /*
    获取某种类型的所有用户
    如果没有值则返回长度为0 的list，不要返回null,都使用vo.user
    货主需要设置的信息有:id,手机号,昵称，积分,金币,注册时间
    司机需要设置的信息有:id,手机号,昵称,积分,金币,注册时间,待审核车辆数(不包括未提交审核，审核成功，审核失败的车辆)
     */
    public List getAllUsersByType(Byte type) {
        List listTemp = userDao.getAllUsersByType(type);
        List list = new ArrayList();
        for(int i = 0;i<listTemp.size();i++){
            model.User userTemp = (model.User) listTemp.get(i);
            int typeTemp = userTemp.getType();
            vo.User user = new vo.User();

            user.setPhoneNum(userTemp.getPhone_number());
            user.setNickName(userTemp.getNick_name());
            user.setId(userTemp.getId());
            user.setPoint(userTemp.getPoint());
            user.setMoney(userTemp.getMoney());
            user.setRegisterTime(userTemp.getRegister_time().toString());
            list.add(user);
        }

        return list;
    }
}
