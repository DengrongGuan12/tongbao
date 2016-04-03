package affairs;

import model.UserToken;

import java.util.List;

/**
 * Created by cg on 2016/3/27.
 */
public interface UserTokenAffairs {
    public boolean addAllNewUserTokens(List<UserToken> allNewUserTokens);
    public boolean updateAllNewUserTokens(List<UserToken> allNewUserTokens);
    public List<UserToken> getAllUserToken();
}
