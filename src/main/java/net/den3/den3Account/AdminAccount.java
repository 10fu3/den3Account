package net.den3.den3Account;

public class AdminAccount extends AccountEntity implements IAccount {
    private boolean admin = false;

    public boolean isAdmin() {
        return admin;
    }

    public AdminAccount setAdminAccount(boolean isAdmin){
        this.admin = isAdmin;
        return this;
    }
}
