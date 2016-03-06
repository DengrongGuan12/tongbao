package dao;

import model.Message;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
public interface MessageDao {
    public List getMyMessage(int userId);
    public Message getMessageById(int id);
    public boolean updateMessage(Message message);
}
