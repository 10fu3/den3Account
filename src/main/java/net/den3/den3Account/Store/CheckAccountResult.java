package net.den3.den3Account.Store;

public enum CheckAccountResult {
    ERROR_MAIL("Invalid e-address"), //メールアドレスではない
    ERROR_PASSWORD_LENGTH("Need 8 characters or more"), //パスワードが基準
    ERROR_SAME("Already registered e-address"), //8文字以上ではない
    SUCCESS("");//成功したのでメッセージはとくにない

    private final String text;

    CheckAccountResult(final String text) {
        this.text = text;
    }

    /**
     * メッセージの取得
     * @return 列挙体に振られたメッセージ
     */
    public String getString() {
        return this.text;
    }
}
