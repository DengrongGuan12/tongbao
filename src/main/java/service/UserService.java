package service;

import model.User;
import vo.ContactDetail;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by cg on 2015/12/29.
 */
public interface UserService {
    public boolean register(String phoneNumber, String password,Byte type);
    public User getUserByPhoneNumber(String  phoneNumber);
    public User getUserById(int id);
    public vo.User login(String phoneNumber, String password, HttpSession session);
    public int hasLogin(String token);
    public boolean changeNickName(int id, String nickName);
    public boolean changePassword(int id, String oldPassword, String newPassword);
    public boolean changeIconUrl(int id, String url);
    public List getUserAccount(int userId);
    public List getContacts(int userId);
    public ContactDetail getContactDetail(int contactId);
    public List getTruckTypes();
    public List getMessagesByUserId(int userId);
    public boolean recharge(int userId, double money);
    public int getTotalShipperNum();
    public int getTotalDriverNum();
    public int getTotalAccountNum();

}
