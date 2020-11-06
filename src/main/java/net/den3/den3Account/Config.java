package net.den3.den3Account;

public class Config {
    private final static Config config = new Config();
    private String DBAccountName;
    private String DBAccountPassword;
    private Boolean DockerMODE;

    public static Config get(){
        return config;
    }

    public Config(){
        this.DBAccountName = System.getenv("D3A_DBACCOUNT");
        this.DBAccountPassword = System.getenv("D3A_DBPASSWORD");
        this.DockerMODE = (System.getenv("D3A_ISDOCKER") != null) && System.getenv("D3A_ISDOCKER").equalsIgnoreCase("DOCKER");
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
}
