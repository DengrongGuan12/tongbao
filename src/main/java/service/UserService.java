package service;

import cn.jpush.api.push.model.PushPayload;
import model.User;
import vo.ContactDetail;
import vo.UserSimple;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by cg on 2015/12/29.
 */
public interface UserService {
    public boolean register(String phoneNumber, String password,Byte type);
    public boolean phoneNumExist(String phoneNum);
    public User getUserByPhoneNumber(String  phoneNumber);
    public User getUserById(int id);
    public vo.User login(String phoneNumber, String password, Byte type, HttpSession session);
    public int hasLogin(String token);
    public boolean changeNickName(int id, String nickName);
    public boolean changePassword(int id, String oldPassword, String newPassword);
    public boolean changeIconUrl(int id, String url);
    public List getUserAccount(int userId);
    public List getContacts(int userId);
    public ContactDetail getContactDetail(int contactId);
    public List getTruckTypes();
    public List getMessagesByUserId(int userId);
    public boolean readMessage(int userId, int id);
    public boolean deleteMessage(int userId, int id);
    public boolean recharge(int userId, double money);
    public boolean withdraw(int userId, double money);
    public int getTotalShipperNum();
    public int getTotalDriverNum();
    public int getTotalAccountNum();
    public List getRecentRegUsers(Byte type, int num);
    public List getRecentAccounts(int num);

    public boolean deleteUser(int userId);
    public boolean resetPassword(int userId, String newPassword);
    public List getAllUsersByType(Byte type);
    public int addFile(String path);
    public String getFilePathById(int id);
    public UserSimple getUserSimpleById(int id);

    public PushPayload buildPushObject_all_all_alert();
    public void push(String alias, String title, String content, Map<String,String> extras);
}
