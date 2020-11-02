package net.den3.den3Account.Logic;

public enum CheckAccountResult {
    ERROR_MAIL("Invalid e-address"), //メールアドレスではない
    ERROR_PASSWORD_LENGTH("Need 8 characters or more"), //パスワードが基準
    ERROR_SAME("Already registered e-address"),
    SUCCESS("");

    private final String text;

    private CheckAccountResult(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }
}
