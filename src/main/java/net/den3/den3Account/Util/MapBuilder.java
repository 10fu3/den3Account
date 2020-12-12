package net.den3.den3Account.Util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
    private Map<String,Object> map = new HashMap<>();
    public static MapBuilder New(){
        return new MapBuilder();
    }

    public MapBuilder put(String key,Object obj){
        map.put(key,obj);
        return this;
    }

    public Map<String, Object> build(){
        return this.map;
    }
}
