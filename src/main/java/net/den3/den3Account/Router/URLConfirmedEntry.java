package net.den3.den3Account.Router;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Account.ITempAccount;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Account.ITempAccountStore;
import net.den3.den3Account.Util.ContentsType;

import java.util.Optional;

class URLConfirmedEntry {
    /**
     * アカウント有効化リンクを踏むとアクセスされるメソッド
     * @param ctx io.javalin.http.Context
     */
    static void mainFlow(io.javalin.http.Context ctx){
        String key = ctx.pathParam("key");
        //有効化キーを持つアカウントの情報が仮登録アカウントストアに存在しない場合
        if(!ITempAccountStore.get().containsAccountByKey(key)){
            ctx.redirect("/account/register/invalid");
            return;
        }
        //有効化キーを持つアカウントの情報が仮登録アカウントストアに存在しない場合その2
        Optional<ITempAccount> tempAccount = ITempAccountStore.get().getAccountByKey(key);
        if(!tempAccount.isPresent()){
            ctx.redirect("/account/register/invalid");
            return;
        }

        //ここで登録処理が走り,返り値として登録されたアカウントエンティティが返される
        Optional<IAccount> optionalAccount = IAccountStore.get().addAccountInSQL(tempAccount.get(),ITempAccountStore.get());
        //正常に登録処理がされてないと空を返してくるので調べる
        if(!optionalAccount.isPresent()){
            ctx.redirect("/account/register/invalid");
            return;
        }
        //TODO 未実装
        //リダイレクトするべき?
        ctx.result("Welcome! "+optionalAccount.get().getNickName());
    }

    static void invalid(io.javalin.http.Context ctx){
        ctx.res.setContentType(ContentsType.HTML.get());
        ctx.result("<h1>エラー</h1><br>登録申請は無効化されたか、エラーが発生しています. 管理者までお問い合わせください");
    }
}
