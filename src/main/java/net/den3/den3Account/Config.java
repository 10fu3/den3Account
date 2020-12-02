package net.den3.den3Account;

public class Config {
    private final static Config config = new Config();
    private String DBAccountName;
    private String DBAccountPassword;
    private Boolean DockerMODE;
    private String EntryMailAddress;
    private String EntryMailPassword;
    private String selfURL;
    private String loginURL;

    public static Config get(){
        return config;
    }

    public Config(){
        this.DockerMODE = (System.getenv("D3A_ISDOCKER") != null) && System.getenv("D3A_ISDOCKER").equalsIgnoreCase("DOCKER");
        this.DBAccountName = System.getenv("D3A_DBACCOUNT") != null ? System.getenv("D3A_DBACCOUNT") : "user";
        this.DBAccountPassword = System.getenv("D3A_DBPASSWORD") != null ? System.getenv("D3A_DBPASSWORD") : "password";
        this.EntryMailAddress = System.getenv("D3A_MAIL_ADD") != null ? System.getenv("D3A_MAIL_ADD") : "";
        this.EntryMailPassword = System.getenv("D3A_MAIL_PASS") != null ? System.getenv("D3A_MAIL_PASS") : "";
        this.selfURL = System.getenv("D3A_SELF_URL") != null ? System.getenv("D3A_SELF_URL") : "";
        this.loginURL = System.getenv("D3A_LOGIN_URL") != null ? System.getenv("D3A_LOGIN_URL") : "";
    }

    public String getDBAccountName() {
        return DBAccountName;
    }

    public String getDBAccountPassword() {
        return DBAccountPassword;
    }

    public String getDBURL(){
        return this.DockerMODE ? "jdbc:mariadb://db:3306/den3_account" : "jdbc:mariadb://localhost:3306/den3_account";
    }

    public String getRedisURL(){
        return this.DockerMODE ? "redis" : "localhost";
    }

    public String getEntryMailAddress() {
        return EntryMailAddress;
    }

    public Config setEntryMailAddress(String entryMailAddress) {
        EntryMailAddress = entryMailAddress;
        return this;
    }

    public String getEntryMailPassword() {
        return EntryMailPassword;
    }

    public Config setEntryMailPassword(String entryMailPassword) {
        EntryMailPassword = entryMailPassword;
        return this;
    }

    public String getSelfURL() {
        return selfURL;
    }

    public Config setSelfURL(String selfURL) {
        this.selfURL = selfURL;
        return this;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public Config setLoginURL(String loginURL) {
        this.loginURL = loginURL;
        return this;
    }
}
