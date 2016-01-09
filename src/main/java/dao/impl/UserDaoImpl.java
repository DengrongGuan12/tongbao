package dao.impl;

import dao.BaseDao;
import dao.UserDao;
import model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dengrong on 2015/12/29.
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao{


    public boolean deleteUser(int id) {
        return false;
    }

    public User getUserById(int id) {
        try {
            User userTemp=(User)super.load(User.class,id);
            return userTemp;
        }catch (Exception e){
            return null;
        }
    }

    public boolean updateUser(User user) {
        try{
            super.update(user);
            return true;
        }catch (Exception e)
        {
            return false;
        }

    }

    public boolean registerUser(User user) {
        try {
            super.save(user);
            return true;
        }catch (Exception e) {
            return false;
        }

    }
    public User getUserByPhoneNumber(String phoneNumber) {
        List list=super.getList(User.class,"phone_number",phoneNumber);
        if(list.isEmpty()){
            return null;
        }else {
            return (User)list.get(0);
        }

    }

}
