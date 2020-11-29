package net.den3.den3Account.Router;

import net.den3.den3Account.Logic.EntryAccount;
import net.den3.den3Account.Store.AccountStore;

public class URLEntryAccount {
    /**
     * HTTPリクエストに仮登録に必要なパラメーターの有無を返す
     * @param ctx io.javalin.http.Context
     * @return 仮登録に必要なパラメーターがある→true ない→false
     */
    public static Boolean containsNeedKey(io.javalin.http.Context ctx){
        return ctx.req.getParameter("mail") != null ||  ctx.req.getParameter("pass") != null || ctx.req.getParameter("nick") != null;
    }

    /**
     * HTTPリクエストを受け取って仮登録をする
     * @param ctx io.javalin.http.Context
     */
    public static void EntryFlow(io.javalin.http.Context ctx){
        ctx.res.setContentType("application/json; charset=UTF-8");
        //そもそもリクエストにmail/passパラメータが含まれてない可能性を排除する
        if(containsNeedKey(ctx)){
            ctx.status(400).result("{ 'status' : \"Client Error\" }");
        }
        //仮登録処理をぶん投げる
        ctx.status(200).result(
                EntryAccount.entryFlow(ctx.req.getParameter("mail"),
                ctx.req.getParameter("pass"),
                ctx.req.getParameter("nick"),
                AccountStore.getInstance()));
    }
}
