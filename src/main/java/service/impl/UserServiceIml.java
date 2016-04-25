package service.impl;

import affairs.UserAffairs;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import dao.*;
import manager.UserManager;
import model.*;
import model.Message;
import model.User;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderService;
import service.UserService;
import vo.*;
import vo.Account;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private FileDao fileDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Order_state_name_t_Dao order_state_name_t_dao;
    @Autowired
    private OrderTruckTypeDao orderTruckTypeDao;
    @Autowired
    private Account_type_name_t_Dao account_type_name_t_dao;
    @Autowired
    private UserAffairs userAffairs;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private BannerDao bannerDao;

    public static final int order_grabbed = 0;
    public static final int order_finished = 1;
    public static final int order_cancel_request = 2;
    public static final int order_canceled = 3;
    public static final int truck_auth_pass = 4;
    public static final int truck_auth_fail = 5;

    public static String userType_driver = "driver";
    public static String userType_shipper = "shipper";

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
        int length = password.length();
        if(length<8){
            return false;
        }
        User user=new User(phoneNumber,password,type);
        user.setRegister_time(new Timestamp(System.currentTimeMillis()));
        user.setIcon("http://120.27.112.9:8080/tongbao/user/picture?id=35");
        if(type == 0){
            user.setNick_name("我是货主SAMA");
        }else{
            user.setNick_name("我是司机SAMA");
        }

        if(userAffairs.register(user)){
            return true;
        }

        return false;
    }

    public boolean phoneNumExist(String phoneNum) {
        User user =  userDao.getUserByPhoneNumber(phoneNum);
        if(user==null){
            return false;
        }
        return true;
    }

    /*
    登录,登录成功返回用户信息,否则返回null
     */
    public vo.User login(String phoneNumber, String password, Byte type, HttpSession session) {
        User userTemp=userDao.getUserByPhoneNumber(phoneNumber);
        if(userTemp == null){
            return null;
        }
        Byte t = userTemp.getType();
        if(t.equals(type)){
            if(userTemp.getPassword().equals(password)){
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
        }else{
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
        int length = newPassword.length();
        if(length<8){
            return false;
        }
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
     *
     * @param userId
     * @return
     */
    public List getUserAccount(int userId) {
        Map<Byte,String> allStateName = order_state_name_t_dao.getAllOrderStateName();
        Map<Byte,String> allAccountType = account_type_name_t_dao.getAllAccountTypes();
        List listTemp=accountDao.getAccounts(userId);
        List list = new ArrayList();
        for(int i=0;i<listTemp.size();i++){
            model.Account accountTemp=(model.Account)listTemp.get(i);
            Byte type = accountTemp.getType();
            Account account = new Account();
            account.setId(accountTemp.getId());
            account.setTypeStr(allAccountType.get(type));
            account.setType(type);
            account.setMoney(accountTemp.getMoney());
            account.setTime(accountTemp.getBuildTime().toString());
            account.setOrderId(accountTemp.getOrderId());
            account.setUserId(accountTemp.getUserId());
            account.setUserPhoneNum(userDao.getUserById(accountTemp.getUserId()).getPhone_number());
            int orderId = account.getOrderId();
            OrderSimple orderSimple = new OrderSimple();
            if(type.equals(new Byte("2"))||type.equals(new Byte("3"))||type.equals(new Byte("4"))){
                Order order = orderDao.showOrderDetail(account.getOrderId());
                orderSimple.setId(order.getId());
                orderSimple.setTime(order.getBuildTime().toString());
                orderSimple.setAddressFrom(order.getAddressFrom());
                orderSimple.setAddressTo(order.getAddressTo());
                orderSimple.setMoney(order.getPrice());
                orderSimple.setTruckTypes(orderTruckTypeDao.getTruckTypesByOrderId(orderId));
                orderSimple.setFromContactPhone(order.getFrom_contact_phone());
                orderSimple.setFromContactName(order.getFrom_contact_name());
                orderSimple.setToContactPhone(order.getTo_contact_phone());
                orderSimple.setToContactName(order.getTo_contact_name());
                orderSimple.setState(order.getState());
                orderSimple.setLoadTime(order.getLoadTime().toString());
                orderSimple.setStateStr(allStateName.get(order.getState()));
            }
            account.setOrder(orderSimple);

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
                 userTemp=this.getUserById(contactsTemp.getDriverId());
            }else{
                 userTemp=this.getUserById(contactsTemp.getShipperId());
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
        //truck_type 表加了一列base_distance, TruckType 加了一个baseDistance ,model也要改一下
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
            truckType.setBaseDistance(trucksTemp.getBase_distance());
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

    public boolean readMessage(int userId, int id) {
        Message message = (Message) messageDao.getMessageById(id);
        if(message==null||message.getUser_id()!=userId){
            return false;
        }
        message.setHas_read(new Byte("1"));

        return messageDao.updateMessage(message);
    }

    public boolean deleteMessage(int userId, int id) {
        Message message = messageDao.getMessageById(id);
        if(message!=null&&message.getUser_id()==userId){
            return messageDao.deleteMessage(id);
        }
        return false;
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
        return userAffairs.recharge(user, account);
    }

    public boolean withdraw(int userId, double money) {
        if(money==0){
            return false;
        }
        User user = userDao.getUserById(userId);
        double moneyTemp = user.getMoney()-money;
        if(moneyTemp<0){
            return false;
        }
        model.Account account=new model.Account();
        account.setBuildTime(new Timestamp(System.currentTimeMillis()));
        account.setUserId(userId);
        //0代表充值
        account.setType(new Byte("1"));
        account.setMoney(money);
        user.setMoney(moneyTemp);
        return userAffairs.withdraw(user,account);
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
        int length = newPassword.length();
        if(length<8){
            return false;
        }
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
        List <vo.User> list = new ArrayList();
        if(listTemp==null){
            return list;
        }
        for(int i = 0;i<listTemp.size();i++){
            model.User userTemp = (model.User) listTemp.get(i);
            int typeTemp = userTemp.getType();
            vo.User user = new vo.User();
            if(typeTemp==1){
                user.setWaitingAuthNum(driver_auth_dao.getWaitingExamineDriverNum(userTemp.getId()));
            }
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

    /**
     *
     * @param path 文件路径
     * @return 成功添加返回id，添加失败返回-1
     */
    public int addFile(String path) {
        File file = new File();
        file.setPath(path);
        if(fileDao.uploadFile(file)){
            return file.getId();
        }else {
            return -1;
        }
    }

    /**
     *
     * @param id
     * @return 成功返回目标路径，失败返回空
     */
    public String getFilePathById(int id) {
        File file = fileDao.getFileById(id);
        if(file!=null){
            return file.getPath();
        }else {
            return "";
        }
    }

    public UserSimple getUserSimpleById(int id) {
        User user = userDao.getUserById(id);
        UserSimple userSimple = new UserSimple();
        userSimple.setId(id);
        userSimple.setMoney(user.getMoney());
        userSimple.setPhoneNum(user.getPhone_number());
        userSimple.setNickName(user.getNick_name());
        userSimple.setRegisterTime(user.getRegister_time().toString());
        return userSimple;
    }

    public PushPayload buildPushObject_all_all_alert(String tag) {
        Map<String,String> extras = new HashMap<String, String>();
        extras.put("type", "0");
        extras.put("id", "1");
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android("内容", "标题",extras))
                .build();
    }

    public PushResult push(String alias, String title, String content, Map<String,String> extras,String userType) {
        PushPayload pushPayload =  PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder().
                        addPlatformNotification(AndroidNotification.
                                newBuilder().
                                setAlert(content).
                                setTitle(title).
                                addExtras(extras)
                                .build()).addPlatformNotification(IosNotification.newBuilder().setAlert(content).addExtras(extras).build()).build())
//                .setMessage(cn.jpush.api.push.model.Message.newBuilder()
//                        .setTitle(title)
//                        .setMsgContent(content)
//                        .addExtras(extras)
//                        .build())
//                .setMessage(cn.jpush.api.push.model.Message.content(content))
                .build();

        JPushClient jPushClient = null;
        if(userType.equals(userType_shipper)){
            jPushClient = new JPushClient("f3c822aa7d23171dc35e351a","0bfdf19308d2b4265b5d467d",3);
        }else{
            jPushClient = new JPushClient("9f5b375a48f78a79f18aaa0c","12be19e543158d0d057b2d09",3);
        }
        try {
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            return pushResult;
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addFeedback(String feedback) {
        Feedback fb = new Feedback();
        fb.setContent(feedback);
        fb.setState(0);
        return feedbackDao.createFeedback(fb);
    }

    public MonthAccount getAccountByMonth(int userId, int year, int month) {
        //0:充值/1:提现/2:支付/3:退款/4:到帐
        // 根据年月获取某个用户的账单列表，需要这个月的总收入（type为0，3，4的和）和总支出（type为1，2的和）

        List<model.Account>allMonthAccount = accountDao.getAccountsByMonth(userId,year,month);
        List<vo.Account>outputAccounts = new ArrayList<Account>();
        Map <Integer,String>map = new HashMap<Integer,String>();
        map.put(0,"充值");map.put(1,"提现");map.put(2,"支付");map.put(3,"退款");map.put(4,"到账");
        double output=0,input=0;
        for(model.Account account:allMonthAccount){
            int type = account.getType();
            if (type == 0 || type == 3 || type == 4) {
                input+=account.getMoney();
            }
            else if(type ==1 || type ==2){
                output+=account.getMoney();
            }
            Account accountTemp = new Account();
            accountTemp.setId(account.getId());
            accountTemp.setOrderId(account.getOrderId());
            Order order = orderDao.showOrderDetail(account.getOrderId());
            if(order == null){
                accountTemp.setOrder(null);
            }else {
                accountTemp.setOrder(orderService.genOrderSimpleFromOrder(order));
            }
            accountTemp.setTime(account.getBuildTime().toString());
            accountTemp.setType(type);
            accountTemp.setTypeStr(map.get(type));
            accountTemp.setMoney(account.getMoney());
            accountTemp.setUserId(account.getUserId());
            User user = userDao.getUserById(account.getUserId());
            accountTemp.setUserPhoneNum(user.getPhone_number());
            outputAccounts.add(accountTemp);
        }
        MonthAccount monthAccount = new MonthAccount();
        monthAccount.setTotalIn(input);
        monthAccount.setTotalOut(output);
        monthAccount.setAccountList(outputAccounts);
        return monthAccount;
    }

    public List getBannerInfoList() {
        // 需要新建一张表来存imgUrl和targetUrl
        List<Banner> listTemp = bannerDao.getAllBanners();
        List<BannerInfo> list = new ArrayList<>();
        for(int i = 0;i<listTemp.size();i++){
            Banner banner = listTemp.get(i);
            BannerInfo bannerInfo = new BannerInfo();
//            bannerInfo.setImgUrl("http://ww4.sinaimg.cn/large/610dc034jw1f2munu5gmfj20sg0e80v7.jpg");
//            bannerInfo.setTargetUrl("https://github.com/Yalantis/Horizon");
            bannerInfo.setImgUrl(banner.getImgUrl());
            bannerInfo.setTargetUrl(banner.getTargetUrl());
            list.add(bannerInfo);
        }
        return list;
    }

    public boolean tokenValid(String token) {
        // 验证某token的有效性，这是因为客户端有些对401错误的支持不太好才加上去的，已登录返回true否则返回false
        int userId =  userManager.getUserIdByKey(token);
        if(userId ==0){
            return false;
        }
        return true;
    }
}
