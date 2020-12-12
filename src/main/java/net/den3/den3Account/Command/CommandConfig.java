package net.den3.den3Account.Command;

import net.den3.den3Account.Config;

import java.util.Optional;

public class CommandConfig implements ICommand{

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public boolean run(String[] option) {
        if (option.length == 0){
            System.out.println("config edit menu");
            System.out.println("config url [SELF_URL] 自分自身のURLを入力します");
            System.out.println("config mail_address [MAIL_ADDRESS] お知らせメールに使う送信元アドレスを入力します");
            System.out.println("config mail_pass [MAIL_PASS] お知らせメールに使うメールアカウントのパスワードを入力します");
            System.out.println("config jwt_secret [key] JWT署名に用いられるシークレットキーを設定します");
            System.out.println("-- END --");
        } else{
            Optional<String> value = Config.get().setValue(option);
            if (!value.isPresent() && option.length <= 1){
                System.out.println("config->存在しないコマンド "+option[0]);
            }
            if(option.length == 1 && value.isPresent()){
                System.out.println(value.get());
            }
        }
        return false;
    }
}
