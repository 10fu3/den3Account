package net.den3.den3Account.Entity;

import net.den3.den3Account.Entity.Account.IAccount;

public enum LoginResult {
    SUCCESS("SUCCESS"),
    ERROR_PARAMS("Invalid parameter"),
    ERROR_WRONGPASS("Wrong password"),
    ERROR_NOT_EXIST("Invalid name"),
    ERROR_FROZEN_ACCOUNT("Frozen account"),
    ERROR_EXCEPTION("Occurred exception");
    public IAccount account = null;
    private String mes;

    LoginResult(String mes){
        this.mes = mes;
    }

    public String getMessage() {
        return mes;
    }
}
