package dao;

import model.Feedback;

import java.util.List;

/**
 * Created by cg on 2016/4/16.
 */
public interface FeedbackDao {
    public boolean createFeedback(Feedback feedback);
    public boolean updateFeedback(Feedback feedback);
    public boolean deleteFeedback(Feedback feedback);
    public Feedback getFeedBack(int id);
    public List<Feedback> getAllFeedBack();
}
