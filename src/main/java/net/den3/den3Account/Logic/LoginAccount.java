package net.den3.den3Account.Logic;

import java.util.Map;
import java.util.Optional;

public class LoginAccount {
    public Optional<Boolean> containsStore(Map<String,String> json){
        String mail = json.get("mail");
        String pass = json.get("pass");

        return Optional.of(false);
    }
}
