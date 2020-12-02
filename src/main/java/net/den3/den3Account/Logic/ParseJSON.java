package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.Account.IAccount;

import java.util.List;
import java.util.stream.Collectors;

public class ParseJSON{
    public static String parse(List<IAccount> lae){
        return new StringBuilder()
                .append("[ ")
                .append(lae.stream().map(ae-> new StringBuilder()
                .append(ae.toString())
                .toString()).collect(Collectors.joining(",")))
                .append("]")
                .toString();

    }

    public static StringBuilder buildWord(String key,String value){
        return new StringBuilder()
                .append("\"")
                .append(key)
                .append("\"")
                .append(" : ")
                .append("\"")
                .append(value)
                .append("\"");
    }
}
