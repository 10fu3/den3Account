package net.den3.den3Account.Router;

import net.den3.den3Account.Entity.CSRFResult;
import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Logic.CSRF;
import net.den3.den3Account.Store.Service.IServiceStore;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;

public class URLDeleteService {
    public static void mainFlow(io.javalin.http.Context ctx){
        CSRFResult csrfResult = CSRF.mainFlow(ctx);
        //CSRF攻撃?
        if(csrfResult != CSRFResult.SUCCESS){
            ctx.status(StatusCode.Unauthorized.code());
            return;
        }
        Optional<Map<String, Object>> j = ParseJSON.convertToMap(ctx.body());
        //JSON文字列をMapにコンバートできない
        if((!j.isPresent()) || !j.get().containsKey("service-id")){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        Optional<IService> s = IServiceStore.get().getService(String.valueOf(j.get().get("service-id")));
        //削除要求のあったサービスIDが存在しない場合
        if(!s.isPresent()){
            ctx.status(StatusCode.NotFound.code());
            return;
        }
        //削除しようとしたアカウントのUUID取得
        String uuid = csrfResult.getJWT().get().getSubject();
        s.ifPresent(service -> {
            //権限なしユーザーが削除を試そうとした場合
            if(!service.getAdminID().equalsIgnoreCase(uuid)){
                //サービスの存在の有無にかかわらず404を返しておく
                ctx.status(StatusCode.NotFound.code());
                return;
            }
            //ここでストアの削除処理を呼び出す
            if(IServiceStore.get().deleteService(service.getServiceID())){
                ctx.status(StatusCode.OK.code());
            }else{
                ctx.status(StatusCode.InternalServerError.code());
            }
        });
    }
}
