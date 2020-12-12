package net.den3.den3Account.Entity;

/**
 * アカウントエンティティにつくロール
 */
public enum Permission{
    ADMIN("ADMIN"),NORMAL("NORMAL"),DEVELOPER("DEVELOPER"),SUSPEND("SUSPEND"),ERROR("ERROR");
    private final String name;
    private static Permission[] ps = Permission.values();
    Permission(final String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static Permission getRole(String tag){
        Permission p = Permission.ERROR;
        for (int i = 0; i < ps.length; i++) {
            if(ps[i].getName().equalsIgnoreCase(tag)){
                p = ps[i];
            }
        }
        return p;
    }
}
