package net.den3.den3Account.Entity;

/**
 * アカウントエンティティにつくロール
 */
public enum Permission implements io.javalin.core.security.Role{
    ADMIN("admin"),NORMAL("normal");
    private final String name;
    Permission(final String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
