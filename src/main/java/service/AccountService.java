package service;

import java.util.List;

/**
 * Created by I322233 on 1/27/2016.
 */
public interface AccountService {
    public List getAllAccounts();
    public boolean deleteAccount(int id);
}
