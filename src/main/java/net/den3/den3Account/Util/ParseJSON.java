package net.den3.den3Account.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.den3.den3Account.Entity.Account.IAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public static Optional<Map<String,String>> convertToMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map;
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if(map.size() == 0){
            return Optional.empty();
        }else{
            return Optional.of(map);
        }
    }

    public static Optional<String> convertToJSON(Map<String,Object> map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
