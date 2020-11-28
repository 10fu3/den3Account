package net.den3.den3Account.Command;

import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Logic.ParseJSON;
import net.den3.den3Account.Store.AccountStore;

import java.util.ArrayList;
import java.util.Optional;

public class CommandAccountCURD implements ICommand{

    @Override
    public String getName() {
        return "account";
    }

    @Override
    public boolean run(String[] option) {
        if(option.length == 0){
            System.out.println("--- Accounts Help ---");
            System.out.println("list :リストを表示");
            System.out.println("del [対象のUUID] :対象のUUIDを持つアカウントを削除");
            System.out.println("edit [対象のUUID] [属性] [変更する状態] :対象のUUIDを持つアカウントのデータを編集する");
            System.out.println("属性 uuid:アカウント固有のID pass:ハッシュ化されたパスワード icon:アイコンのURL nick:ニックネーム last_login_time:最終ログイン時刻(yyyy/MM/dd HH:mm:ss)");
            System.out.println("--- Accounts ヘルプ ---");
        }else if(option.length == 1 && option[0].equalsIgnoreCase("list")){
            System.out.println("--- Accounts ヘルプ ---");
            AccountStore.getInstance().getAccountsAll().orElse(new ArrayList<>())
                    .forEach(a-> System.out.println(ParseJSON.parse(AccountStore.getInstance().getAccountsAll().orElse(new ArrayList<>()))));
            System.out.println("--- Accounts リスト 終わり ---");
        }else if(option.length == 2 && option[0].equalsIgnoreCase("del")){
            Optional<IAccount> account = AccountStore.getInstance().getAccountByUUID(option[1]);
            if(account.isPresent()){
                AccountStore.getInstance().deleteAccountInSQL(account.get());
                System.out.println("指定されたユーザーを削除しました ID: "+option[1]);
            }else{
                System.out.println("指定されたユーザーが存在しません ID: "+option[1]);
            }
        }else if(option.length == 4){
            if(option[0].equalsIgnoreCase("edit")){
                switch (option[2]){
                    case "uuid":
                        
                        break;
                    case "pass":
                        break;
                    case "icon":
                        break;
                    case "nick":
                        break;
                    case "last_login_time":
                        break;
                }
            }
        }

        return true;
    }
}
