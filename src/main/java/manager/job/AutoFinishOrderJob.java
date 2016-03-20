package manager.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.slf4j.Logger;
import java.sql.Timestamp;


/**
 * Created by cg on 2016/3/20.
 */
public class AutoFinishOrderJob implements Job {
    private static Logger _log = LoggerFactory.getLogger(AutoFinishOrderJob.class);
    public AutoFinishOrderJob(){}
    public void execute(JobExecutionContext context){
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("Auto finish order at "+ new Timestamp(System.currentTimeMillis()));


    }

}
