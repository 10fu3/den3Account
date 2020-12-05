package net.den3.den3Account.Command;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Account.TemporaryAccountEntity;
import net.den3.den3Account.Logic.ParseJSON;
import net.den3.den3Account.Store.Account.AccountStore;
import net.den3.den3Account.Store.Account.IAccountStore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            System.out.println("create :アカウントの作成を始めます");
            System.out.println("edit [対象のUUID] [属性] [変更する状態] :対象のUUIDを持つアカウントのデータを編集する");
            System.out.println("属性 pass:ハッシュ化されたパスワード(オプション -i でハッシュ化せず直にDBに書き込む) icon:アイコンのURL nick:ニックネーム last_login_time:最終ログイン時刻(yyyy/MM/dd HH:mm:ss)");
            System.out.println("--- Accounts ヘルプ ---");
        }else if(option.length == 1 && option[0].equalsIgnoreCase("list")){
            System.out.println("--- Accounts ヘルプ ---");
            AccountStore.getInstance().getAccountsAll().orElse(new ArrayList<>())
                    .forEach(a-> System.out.println(ParseJSON.parse(AccountStore.getInstance().getAccountsAll().orElse(new ArrayList<>()))));
            System.out.println("--- Accounts リスト 終わり ---");
        }else if(option.length == 1 && option[0].equalsIgnoreCase("create")){
            TemporaryAccountEntity e = new TemporaryAccountEntity();
            String temp;
            System.out.println("メールアドレスを入力してください");
            temp = CommandExecutor.SINGLE.scan.nextLine();
            e.setMail(temp);
            System.out.println("パスワードを入力してください");
            temp = CommandExecutor.SINGLE.scan.nextLine();
            e.setPasswordHash(getHash(temp).orElse(temp));
            System.out.println("ニックネームを入力してください");
            temp = CommandExecutor.SINGLE.scan.nextLine();
            e.setNickName(temp);
            if(IAccountStore.getInstance().addAccountInSQL(e).isPresent()){
                System.out.println("アカウントの作成に成功しました");
            }else{
                System.out.println("アカウントの作成に失敗しました");
            }

        }else if(option.length == 2 && option[0].equalsIgnoreCase("del")){
            Optional<IAccount> account = AccountStore.getInstance().getAccountByUUID(option[1]);
            if(account.isPresent()){
                AccountStore.getInstance().deleteAccountInSQL(account.get());
                System.out.println("指定されたユーザーを削除しました ID: "+option[1]);
            }else{
                System.out.println("指定されたユーザーが存在しません ID: "+option[1]);
            }
        }else if(option.length >= 4){
            if(option[0].equalsIgnoreCase("edit")){
                Optional<IAccount> account = AccountStore.getInstance().getAccountByUUID(option[1]);
                if(!account.isPresent()){
                    System.out.println("指定されたユーザーが存在しません ID: "+option[1]);
                    return true;
                }
                switch (option[2]){
                    case "pass":
                        String pass = option.length == 4 ? option[3] : getHash(option[3]).orElse(option[3]);
                        if(AccountStore.getInstance().updateAccountInSQL(account.get().setPasswordHash(pass))){
                            System.out.println("ID: "+option[1]+" のパスワードを "+pass+" に変更しました");
                        }else{
                            System.out.println("パスワードの変更に失敗しました");
                        }
                        break;
                    case "icon":
                        if(AccountStore.getInstance().updateAccountInSQL(account.get().setIconURL(option[3]))){
                            System.out.println("ID: "+option[1]+" のアイコンURLを "+option[3]+" に変更しました");
                        }else{
                            System.out.println("アイコンURLの変更に失敗しました");
                        }
                        break;
                    case "nick":
                        if(AccountStore.getInstance().updateAccountInSQL(account.get().setNickName(option[3]))){
                            System.out.println("ID: "+option[1]+" のニックネームを "+option[3]+" に変更しました");
                        }else{
                            System.out.println("ニックネームの変更に失敗しました");
                        }
                        break;
                    case "last_login_time":
                        if(AccountStore.getInstance().updateAccountInSQL(account.get().setLastLogin(option[3]))){
                            System.out.println("ID: "+option[1]+" の最終ログイン時刻を "+option[3]+" に変更しました");
                        }else{
                            System.out.println("最終ログイン時刻の変更に失敗しました");
                        }
                        break;
                }
            }
        }

        return true;
    }

    private static Optional<String> getHash(String hash){
        try{
            // メッセージダイジェストのインスタンスを生成
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] result = md5.digest(hash.getBytes());

            // 16進数に変換して桁を整える
            int[] i = new int[result.length];
            StringBuffer sb = new StringBuffer();
            for (int j=0; j < result.length; j++){
                i[j] = (int)result[j] & 0xff;
                if (i[j]<=15){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i[j]));
            }
            return Optional.of(sb.toString());

        } catch (NoSuchAlgorithmException x){
            return Optional.empty();
        }
    }
}
