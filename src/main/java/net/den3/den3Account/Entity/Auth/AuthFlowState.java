package net.den3.den3Account.Entity.Auth;

public enum AuthFlowState {
    //認可前
    BEFORE_ACCEPT("BEFORE"),
    //認可後
    AFTER_ACCEPT("AFTER"),
    //なにも操作されてない
    INIT("INIT");
    public final String name;

    AuthFlowState(String name){
        this.name = name;
    }
}
