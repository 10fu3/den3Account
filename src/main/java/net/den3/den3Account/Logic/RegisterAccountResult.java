package net.den3.den3Account.Logic;

public enum RegisterAccountResult {
    ERROR_PARAMETER("Invalid parameter."), //パラメーター不備
    ERROR_NOT_EXIST_QUEUE("Not entry in queue."), //エントリーされていないか有効期限切れの無効なID
    SUCCESS("");//成功したのでメッセージはとくにない

    private final String text;

    RegisterAccountResult(final String text) {
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