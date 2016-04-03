package dao.impl;

import dao.UserTokenDao;
import model.UserToken;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/3/27.
 */
@Repository
public class UserTokenDaoImpl extends BaseDaoImpl implements UserTokenDao {
    public List<UserToken> getAllUserToken() {
        return super.getAllList(UserToken.class);
    }
}
