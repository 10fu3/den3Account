package net.den3.den3Account;

import net.den3.den3Account.Entity.MethodPair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class Config {
    private final static Config config = new Config();

    private Map<String,String> store = new HashMap<>();
    private final Map<String, MethodPair<String,String>> methods = new HashMap<>();

    public static Config get(){
        return config;
    }

//    private void setupStoreAndMethods(String field,String firstValue){
//        store.put(field,firstValue)
//        MethodPair<String,String> method = new MethodPair<>(
//                ()-> store.get(field),
//                (v)-> store.put(field,v));
//        methods.put(field,method);
//    }

    public Config(){

        store.put("DockerMODE",
                (System.getenv("D3A_ISDOCKER") != null) && System.getenv("D3A_ISDOCKER").equalsIgnoreCase("DOCKER") ? "true" : "false");
        store.put("DBAccountName" , System.getenv("D3A_DBACCOUNT") != null ? System.getenv("D3A_DBACCOUNT") : "user");
        store.put("DBAccountPassword" , System.getenv("D3A_DBPASSWORD") != null ? System.getenv("D3A_DBPASSWORD") : "password");
        store.put("mail_address", System.getenv("D3A_MAIL_ADDRESS") != null ? System.getenv("D3A_MAIL_ADDRESS") : "");
        store.put("mail_pass" , System.getenv("D3A_MAIL_PASS") != null ? System.getenv("D3A_MAIL_PASS") : "");
        store.put("url" , System.getenv("D3A_SELF_URL") != null ? System.getenv("D3A_SELF_URL") : "");
        store.put("loginURL" , System.getenv("D3A_LOGIN_URL") != null ? System.getenv("D3A_LOGIN_URL") : "");
        store.put("jwt_secret" , System.getenv("D3A_JWT_SECRET") != null ? System.getenv("D3A_JWT_SECRET") : UUID.randomUUID().toString());
    }

    public Optional<String> getValue(String field){
        return Optional.ofNullable(store.get(field));
    }

    public Optional<String> setValue(String[] arg){
        String beforeValue = null;
        if(arg.length == 1){
            if(store.get(arg[0]) == null){
                return Optional.empty();
            }
            beforeValue = store.get(arg[0]);
        }else if(arg.length == 2){
            store.put(arg[0],arg[1]);
        }
        return Optional.ofNullable(beforeValue);
    }

    public String getDBAccountName() {
        return store.get("DBAccountName");
    }

    public String getDBAccountPassword() {
        return store.get("DBAccountPassword");
    }

    public String getDBURL(){
        return store.get("true".equalsIgnoreCase(store.get("DockerMODE")) ? "jdbc:mariadb://db:3306/den3_account" : "jdbc:mariadb://localhost:3306/den3_account");
    }

    public String getRedisURL(){
        return store.get("true".equalsIgnoreCase(store.get("DockerMODE")) ? "redis" : "localhost");
    }

    public String getEntryMailAddress() {
        return store.get("EntryMailAddress");
    }

    public Config setEntryMailAddress(String entryMailAddress) {
        store.put("EntryMailAddress" , entryMailAddress);
        return this;
    }

    public String getEntryMailPassword() {
        return store.get("EntryMailPassword");
    }

    public Config setEntryMailPassword(String entryMailPassword) {
        store.put("EntryMailPassword" , entryMailPassword);
        return this;
    }

    public String getSelfURL() {
        return store.get("url");
    }

    public Config setSelfURL(String selfURL) {
        store.put("url",selfURL);
        return this;
    }

    public String getLoginURL() {
        return store.get("loginURL");
    }

    public Config setLoginURL(String loginURL) {
        store.put("loginURL",loginURL);
        return this;
    }

    public String getJwtSecret(){
        return store.get("jwt_secret");
    }

    public Config setJwtSecret(String pass){
        store.put("jwt_secret", pass);
        return this;
    }
}
