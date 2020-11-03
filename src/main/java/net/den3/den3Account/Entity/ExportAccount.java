package net.den3.den3Account.Entity;

/**
 * 外部サービスとやりとりするためのアカウントを表すクラス
 */
public class ExportAccount extends AccountEntity {

    /**
     * 外部サービスとやりとりするアカウントの情報をJSON出力する
     * @return JSON ユニークID(id) ニックネーム(name) アイコン画像のURL(icon)
     */
    public String getJSON(){
        return buildAccount().toString();
    }

    /**
     * 仮アカウント受付時に保存されるアカウント情報をJSON出力する
     * @return JSON ユニークID(id) ハッシュ化されたパスワード(pass_hash) メールアドレス(mail)
     */
    public String getInternalTemporaryJSON(){
        return buildTemporaryAccount().toString();
    };



    private StringBuilder buildAccount(){
        return new StringBuilder()
                .append("{")
                .append(buildWord("id",super.getUUID()))
                .append(" , ")
                .append(buildWord("name",super.getNickName()))
                .append(" , ")
                .append(buildWord("icon",super.getIconURL()))
                .append("}");
    }

    private StringBuilder buildTemporaryAccount(){
        return new StringBuilder()
                .append("{")
                .append(buildWord("id",super.getUUID()))
                .append(" , ")
                .append(buildWord("pass_hash",super.getPasswordHash()))
                .append(" , ")
                .append(buildWord("mail",super.getMailAddress()))
                .append("}");
    }

    private StringBuilder buildWord(String key,String value){
        return new StringBuilder()
                .append("\"")
                .append(key)
                .append("\"")
                .append(" : ")
                .append("\"")
                .append(value)
                .append("\"");
    }
}
