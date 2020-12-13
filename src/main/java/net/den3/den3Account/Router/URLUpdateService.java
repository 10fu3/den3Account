package net.den3.den3Account.Router;

import net.den3.den3Account.Entity.CSRFResult;
import net.den3.den3Account.Entity.Service.Service;
import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.Logic.CSRF;
import net.den3.den3Account.Store.Service.IServiceStore;
import net.den3.den3Account.Util.MapUtil;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.*;

public class URLUpdateService {
    public static void mainFlow(io.javalin.http.Context ctx){
        CSRFResult csrfResult = CSRF.mainFlow(ctx);
        //CSRF攻撃?
        if(csrfResult != CSRFResult.SUCCESS){
            ctx.status(StatusCode.Unauthorized.code());
            return;
        }
        Optional<Map<String, Object>> j = ParseJSON.convertToMap(ctx.body());
        //JSON文字列をMapにコンバートできない
        if((!j.isPresent()) || MapUtil.hasKey(j.get(), "service-name","redirect-url","icon-url","description","permissions") ){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        //登録しようとしたアカウントのUUID取得
        String uuid = csrfResult.getJWT().get().getSubject();
        Service s = new Service();
        s.setAdminID(uuid);
        s.setServiceID(UUID.randomUUID().toString());
        s = readJSON(s, j.get());
        //登録
        if(IServiceStore.get().updateService(s)){
            ctx.status(StatusCode.OK.code());
        }else{
            //失敗
            ctx.status(StatusCode.InternalServerError.code());
        }
    }

    private static Service readJSON(Service service, Map<String,Object> json){
        service.setRedirectURL(String.valueOf(json.get("redirect-url")));
        service.setServiceName(String.valueOf(json.get("service-name")));
        service.setServiceIconURL(String.valueOf(json.get("icon-url")));
        List<String> perms = ParseJSON.convertToStringList(String.valueOf(json.get("permissions"))).orElse(new ArrayList<>());
        for (int i = 0; i < perms.size(); i++) {
            Optional<ServicePermission> optionalPerm = ServicePermission.getPermission(perms.get(i));
            optionalPerm.ifPresent(service::setUsedPermission);
        }
        return service;
    }
}
