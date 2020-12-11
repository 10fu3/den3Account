package net.den3.den3Account.Util;

public enum ContentsType {
    HTML("text/html; charset=utf-8"),
    JSON("application/json; charset=UTF-8");

    private String value;
    ContentsType(String v){
        this.value = v;
    }

    public String get() {
        return value;
    }
}
