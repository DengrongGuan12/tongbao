package pojo;

/**
 * Created by I322233 on 12/31/2015.
 */
public class ModifiedPassword extends TokenAuthInfo{
    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    private String newPassword;

}
