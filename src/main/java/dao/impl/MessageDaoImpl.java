package dao.impl;

import dao.MessageDao;
import model.Message;

import java.util.List;

/**
 * Created by cg on 2016/1/9.
 */
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {
    public List getMyMessage(int userId) {
        return super.getList(Message.class,"user_id",userId+"");
    }
}
