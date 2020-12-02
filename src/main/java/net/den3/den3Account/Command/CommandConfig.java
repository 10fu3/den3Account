package net.den3.den3Account.Command;

import net.den3.den3Account.Config;

public class CommandConfig implements ICommand{

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public boolean run(String[] option) {
        if (option.length <= 1){
            System.out.println("config edit menu");
            System.out.println("config url [SELF_URL] 自分自身のバックエンドサーバのURLを入力します");
            System.out.println("config mail_address [MAIL_ADDRESS] お知らせメールに使う送信元アドレスを入力します");
            System.out.println("config mail_pass [MAIL_PASS] お知らせメールに使うメールアカウントのパスワードを入力します");
            System.out.println("-- END --");
        } else if(option.length == 2){
            switch (option[0]){
                case "url":
                    Config.get().setSelfURL(option[1]);
                    return true;
                case "mail_address":
                    Config.get().setEntryMailAddress(option[1]);
                    return true;
                case "mail_pass":
                    Config.get().setEntryMailPassword(option[1]);
                    return true;
            }
        }
        return false;
    }
}
