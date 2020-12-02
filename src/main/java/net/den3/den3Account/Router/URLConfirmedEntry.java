package net.den3.den3Account.Router;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Account.ITempAccount;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Account.ITempAccountStore;

import java.util.Optional;

public class URLConfirmedEntry {
    public static void EntryFlow(io.javalin.http.Context ctx){
        String key = ctx.pathParam("key");
        //有効化キーを持つアカウントの情報が仮登録アカウントストアに存在しない場合
        if(!ITempAccountStore.getInstance().containsAccount(key)){
            ctx.redirect("/account/register/invalid");
        }
        //有効化キーを持つアカウントの情報が仮登録アカウントストアに存在しない場合その2
        Optional<ITempAccount> tempAccount = ITempAccountStore.getInstance().getAccount(key);
        if(!tempAccount.isPresent()){
            ctx.redirect("/account/register/invalid");
        }
        Optional<IAccount> optionalAccount = IAccountStore.getInstance().addAccountInSQL(tempAccount.get());
        if(!optionalAccount.isPresent()){
            ctx.redirect("/account/register/invalid");
        }
        //リダイレクトするべき?
        ctx.result("Welcome! "+optionalAccount.get().getNickName());
    }

    public static void invalid(io.javalin.http.Context ctx){
        ctx.result("<h1>エラー</h1> 登録申請は無効化されたか、エラーが発生しています. 管理者までお問い合わせください");
    }
}
