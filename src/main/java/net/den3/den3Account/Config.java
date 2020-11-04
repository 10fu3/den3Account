package net.den3.den3Account;

public class Config {
    private final static Config config = new Config();
    private String DBAccountName = "";
    private String DBAccountPassword = "";

    public static Config get(){
        return config;
    }

    public Config(){
        this.DBAccountName = System.getenv("D3A_DBACCOUNT");
        this.DBAccountPassword = System.getenv("D3A_DBPASSWORD");
    }

    public String getDBAccountName() {
        return DBAccountName;
    }

    public String getDBAccountPassword() {
        return DBAccountPassword;
    }
}
