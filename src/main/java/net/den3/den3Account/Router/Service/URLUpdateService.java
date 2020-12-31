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

public class URLUpdateService {
    public static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, Object>> jsonMap = ParseJSON.convertToMap(ctx.body());
        //JSON文字列をMapにコンバートできない
        if((!jsonMap.isPresent()) || new MapUtil<>().hasKey(jsonMap.get(), "id","service-name","redirect-url","icon-url","description","permissions") ){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }

        Optional<IService> oldService = IServiceStore.get().getService(String.valueOf(jsonMap.get().get("id")));
        if(!oldService.isPresent()){
            ctx.status(StatusCode.NotFound.code());
            return;
        }

        Optional<String> uuid = ITokenStore.get().getAccountUUID(String.valueOf(jsonMap.get().get("token")));
        if(!uuid.isPresent()){
            ctx.status(StatusCode.Unauthorized.code());
            return;
        }

        if(!oldService.get().getAdminID().equalsIgnoreCase(uuid.get())){
            ctx.status(StatusCode.Forbidden.code());
            return;
        }

        IService s = readJSON(jsonMap.get());
        //登録
        if(IServiceStore.get().updateService(s)){
            ctx.status(StatusCode.OK.code());
        }else{
            //失敗
            ctx.status(StatusCode.InternalServerError.code());
        }
    }

    private static IService readJSON(Map<String,Object> json){
        ServiceBuilder builder = new ServiceBuilder();
        builder.setRedirectURL(String.valueOf(json.get("redirect-url")));
        builder.setServiceName(String.valueOf(json.get("service-name")));
        builder.setServiceIconURL(String.valueOf(json.get("icon-url")));
        List<String> perms = ParseJSON.convertToStringList(String.valueOf(json.get("permissions"))).orElse(new ArrayList<>());
        for (int i = 0; i < perms.size(); i++) {
            Optional<ServicePermission> optionalPerm = ServicePermission.getPermission(perms.get(i));
            optionalPerm.ifPresent(builder::setUsedPermission);
        }
        return builder.build();
    }
}
