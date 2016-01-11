package manager;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import net.sf.ehcache.util.counter.sampled.TimeStampedCounterValue;

import java.util.*;

/**
 * Created by cg on 2015/12/30.
 * 单例，用于维护用户登录状态
 *
 */

public class UserManager {
    private static final int UNIQUE_CODE_LENGTH=16;//token长度
    private static final long DEFAULT_TIMEOUT=30000;//设置定时清理过期用户时间间隔
    private static final long OVERDUE_TIME=1800000;//设置用户过期时长为30分钟
    private static Map<String,Object> all_user_online=new HashMap<String, Object>();
    private static Map<Integer,String>user_auth_code=new HashMap<Integer,String>();

    private static class SingletonHolder {
        private static final UserManager INSTANCE = new UserManager();
    }
    private UserManager(){
        new clearThread().start();
    }
    public static final UserManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 把用户加入在线列表，返回key值
     * @param userId
     * @param userType
     * @ return key
     */
    public String addToOnlineList(int userId,int userType){

        boolean isContain=true;
        String key="";
        while (isContain){
            key=getRandomString(UNIQUE_CODE_LENGTH);
            isContain=all_user_online.containsKey(key);
        }
        //删除之前all_user_online表中之前的数据,保证每个用户在online map中是唯一的
        if(user_auth_code.containsKey(userId)){
            all_user_online.remove(user_auth_code.get(userId));
            user_auth_code.replace(userId,key);
        }else{
            user_auth_code.put(userId,key);
        }

        all_user_online.put(key,new user_online(userId,userType));
        return key;
    }
    public int getUserType(int id){
        user_online user_online=(user_online) all_user_online.get(user_auth_code.get(id));
        return user_online.userType;
    }
    public int getUserIdByKey(String key){
        user_online temp=(user_online)all_user_online.get(key);
        if(temp!=null){
            temp.setAddTime(System.currentTimeMillis());
            return temp.userId;
        }else{
            return 0;
        }
    }
    //生成定长度的自由字符串
    private String getRandomString(int length){
        String base="abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb =  new StringBuffer();
        for(int i=0;i<length;i++){
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    //测试用
    private String showAllOnlineUser(){
        Iterator iterator=all_user_online.keySet().iterator();
        while (iterator.hasNext()){
            String t=(String)iterator.next();
            System.out.println(t);
            user_online user_online=(user_online)all_user_online.get(t);
            System.out.println(user_online.userId);

        }
        return null;
    }

    /**
     * 内部类，用来保存在线用户状态
     */
    private class user_online{
        int userId;
        private long addTime;
        int userType;
        user_online(int userId,int userType){
            this.userId=userId;
            this.userType=userType;
            this.addTime=System.currentTimeMillis();
        }
        public void setAddTime(long time){
            this.addTime = time;
        }
    }

    /**
     * 内部类，定时清理过期线程
     *
     */
    private class clearThread extends Thread{
        clearThread(){
            setName("clear cache thread");
            System.out.print("定时清理过期在线用户线程开始");
        }
        int i=1;
        public void run(){
            while(true){
                try{
                    long now =System.currentTimeMillis();
                     Set<String>keys=all_user_online.keySet();
                    for(String key : keys){
                        user_online user_temp=(user_online)all_user_online.get(key);
                        if(now-user_temp.addTime>=OVERDUE_TIME){
                            synchronized (all_user_online){
                                all_user_online.remove(key);
                            }
                        }
                    }
                    System.out.println("第" + i++ + "遍清理完毕。");
                    showAllOnlineUser();
                    Thread.sleep(DEFAULT_TIMEOUT);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}
