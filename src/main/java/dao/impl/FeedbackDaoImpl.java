package dao.impl;

import dao.FeedbackDao;
import model.Feedback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cg on 2016/4/16.
 */
@Repository
public class FeedbackDaoImpl extends BaseDaoImpl implements FeedbackDao{
    @Override
    public boolean createFeedback(Feedback feedback) {
        try {
            super.save(feedback);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateFeedback(Feedback feedback) {
        try {
            super.update(feedback);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteFeedback(Feedback feedback) {
        try {
            super.delete(feedback);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Feedback getFeedBack(int id) {
        try {
            Feedback feedback = (Feedback)super.load(Feedback.class, id);
            return feedback;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Feedback> getAllFeedBack() {
        List<Feedback> list = super.getAllList(Feedback.class);
        return list;
    }
}
