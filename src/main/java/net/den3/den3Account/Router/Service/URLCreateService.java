package net.den3.den3Account.Router.Service;

import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Entity.Service.ServiceBuilder;
import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.Store.Auth.ITokenStore;
import net.den3.den3Account.Store.Service.IServiceStore;
import net.den3.den3Account.Util.MapUtil;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.*;

public class URLCreateService {
    public static void mainFlow(io.javalin.http.Context ctx){

        Optional<Map<String, Object>> j = ParseJSON.convertToMap(ctx.body());
        Optional<Map<String, String>> jsonString = ParseJSON.convertToStringMap(ctx.body());
        //JSON文字列をMapにコンバートできない
        if((!j.isPresent()) || new MapUtil<>().hasKey(j.get(), "token","service-name","redirect-url","icon-url","description","permissions") ){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        //登録しようとしたアカウントのUUID取得
        String uuid = ITokenStore.get().getAccountUUID(jsonString.get().get("token")).orElse("");
        IService s = readJSON(j.get());
        //登録
        if(IServiceStore.get().addService(s)){
            ctx.status(StatusCode.OK.code());
        }else{
            //失敗
            ctx.status(StatusCode.InternalServerError.code());
        }
    }

    private static IService readJSON(Map<String,Object> json){
        ServiceBuilder builder = new ServiceBuilder();
        builder.setRedirectURL(String.valueOf(json.get("redirect-url")))
                .setServiceName(String.valueOf(json.get("service-name")))
                .setServiceIconURL(String.valueOf(json.get("icon-url")));
        List<String> perms = ParseJSON.convertToStringList(String.valueOf(json.get("permissions"))).orElse(new ArrayList<>());
        for (int i = 0; i < perms.size(); i++) {
            Optional<ServicePermission> optionalPerm = ServicePermission.getPermission(perms.get(i));
            optionalPerm.ifPresent(builder::setUsedPermission);
        }
        return builder.build();
    }
}
