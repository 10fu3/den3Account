package net.den3.den3Account.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.den3.den3Account.Entity.Account.IAccount;

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

    public static Optional<Map<String,String>> convertToStringMap(String json){
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

    public static Optional<Map<String,Object>> convertToMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map;
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
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

    public static Optional<List<String>> convertToStringList(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> convertToFromList(List<String> list){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
