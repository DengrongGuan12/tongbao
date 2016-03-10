package affairs;

import model.Account;
import model.User;

/**
 * Created by cg on 2016/3/9.
 */
public interface UserAffairs {
    public boolean register(User user);
    public boolean recharge(User user, Account account);

}
