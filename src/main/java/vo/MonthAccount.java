package vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I322233 on 4/10/2016.
 */
public class MonthAccount {
    public double totalIn;
    public double totalOut;

    public List getAccountList() {
        return accountList;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

    public double getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(double totalIn) {
        this.totalIn = totalIn;
    }

    public double getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(double totalOut) {
        this.totalOut = totalOut;
    }

    public List accountList = new ArrayList<Account>();

}
