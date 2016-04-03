package manager.job;

import affairs.UserTokenAffairs;
import dao.UserTokenDao;
import manager.UserManager;
import model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by cg on 2016/4/3.
 */
@Component("autoManagerUserToken")
public class AutoManageUserToken {
    private UserManager userManager = UserManager.getInstance();
    private static boolean isInit = false;
    private  List<UserToken> newAddUserToken;
    private  List<UserToken> newUpdateUserToken;
    private Map<Integer,String> allAuthToken;
    @Autowired
    private UserTokenDao userTokenDao;
    @Autowired
    private UserTokenAffairs userTokenAffairs;
    public void autoManage(){
        if(!isInit){
            System.out.println("初始化数据库中的用户token");
            isInit = userManager.initAllUserTokenFromDatabase(userTokenDao.getAllUserToken());
        }
        newAddUserToken = userManager.getAllNewAddToken();
        newUpdateUserToken = userManager.getAllNewUpdateToken();
        allAuthToken = userManager.getAllAuthToken();
        System.out.println("内存中全部的token个数为："+allAuthToken.size());
        System.out.println("newAddUserToken中的个数为："+newAddUserToken.size());
        System.out.println("newUpdateUserToken中的个数为："+newUpdateUserToken.size());
        if(newAddUserToken.size()!=0){
            if(userTokenAffairs.addAllNewUserTokens(newAddUserToken)){
                System.out.println("新增"+newAddUserToken.size()+"个用户token");
                newAddUserToken.clear();
            }
        }
        if(newUpdateUserToken.size()!=0){
            if(userTokenAffairs.updateAllNewUserTokens(newUpdateUserToken)){
                System.out.println("新更新"+newUpdateUserToken.size()+"个用户token");
                newUpdateUserToken.clear();
            }
        }
    }
}
