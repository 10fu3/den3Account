package net.den3.den3Account.Util;

import java.util.Map;

public class MapUtil {
    public static boolean hasKey(Map<String,Object> map, String... needParams){
        for (int i = 0; i < needParams.length; i++) {
            if(!map.containsKey(needParams[i])){
                return false;
            }
        }
        return true;
    }
}
