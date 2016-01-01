package manager;

/**
 * Created by cg on 2015/12/30.
 */
public class Test {
    public static void main(String []args){
        UserManager userManager=UserManager.getInstance();
        String t=userManager.addToOnlineList(1,1);
        userManager.addToOnlineList(1,1);
        userManager.addToOnlineList(1,1);
        userManager.addToOnlineList(1,1);
//        System.out.print(System.currentTimeMillis());
    }
}
