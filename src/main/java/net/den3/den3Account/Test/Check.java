package net.den3.den3Account.Test;

import net.den3.den3Account.Entity.AccountEntity;
import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Logic.ParseJSON;

import java.util.ArrayList;
import java.util.List;

public class Check {
    public Check(){
        CheckAccountEntityToString();
    }

    public static void main(String... args){
        new Check();
    }

    //Test ParseJSON.parse
    public void CheckAccountEntityToString(){
        List<IAccount> models = new ArrayList<>();
        models.add(new AccountEntity().setNickName("A").setLastLogin("2020-12-01 11:00"));
        models.add(new AccountEntity().setNickName("C").setLastLogin("2022-11-13 19:00"));
        System.out.println("Test CheckAccountEntityToString");
        System.out.println(ParseJSON.parse(models));
        System.out.println("-----");
    }
}
