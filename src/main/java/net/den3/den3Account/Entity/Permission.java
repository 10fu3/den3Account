package net.den3.den3Account.Entity;

/**
 * アカウントエンティティにつくロール
 */
public enum Permission{
    ADMIN("admin"),NORMAL("normal"),DEVELOPER("developer"),SUSPEND("suspend"),ERROR("error");
    private final String name;
    Permission(final String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
