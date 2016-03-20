package manager;

import com.google.common.util.concurrent.AbstractScheduledService;
import manager.job.AutoFinishOrderJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by cg on 2016/3/20.
 */
public class QuartzManager {
    public void run() throws Exception{
        Logger log = LoggerFactory.getLogger(QuartzManager.class);
        log.info("-------Initializing-------");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");
        Date startTime = DateBuilder.nextGivenSecondDate(null, 5);
        JobDetail job =newJob(AutoFinishOrderJob.class).withIdentity("AutoFinishOrderJob", "group1").build();
        SimpleTrigger trigger = newTrigger().withIdentity("AutoFinishOrderTrigger", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever()).build();
        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");
        sched.start();
    }
}
