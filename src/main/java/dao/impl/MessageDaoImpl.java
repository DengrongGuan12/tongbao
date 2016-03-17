package dao.impl;

import dao.MessageDao;
import model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {
    public Message getMessageById(int id) {
        try{
            Message message = (Message)super.load(Message.class,id);
            return message;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    public boolean updateMessage(Message message) {
        try {
            super.update(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean addMessage(Message message) {
        try{
            super.save(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteMessage(int id) {
        try {
            super.delete(Message.class,id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List getMyMessage(int userId) {
        return super.getList(Message.class,"user_id",userId+"");
    }
}
