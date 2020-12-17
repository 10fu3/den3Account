package net.den3.den3Account.Router.OAuth2;

import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.Store.Service.IServiceStore;
import net.den3.den3Account.StringChecker;
import net.den3.den3Account.Util.ListUtil;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.StatusCode;

import java.util.Optional;

/**
 * 送られてきた
 */
class AuthorizeParam{
    final boolean isValidParam;
    final String responseType;
    final String clientID;
    final String redirect_uri;
    final String state;
    final String scope;
    final String nonce;
    AuthorizeParam(io.javalin.http.Context ctx){
        responseType = ctx.queryParam("");
        clientID = ctx.queryParam("");
        redirect_uri = ctx.queryParam("");
        state = ctx.queryParam("");
        scope = ctx.queryParam("");
        nonce = ctx.queryParam("");
        if(!StringChecker.hasNull(responseType,clientID,redirect_uri,state,scope,nonce)){
            if("code".equalsIgnoreCase(responseType)
                    && !state.isEmpty()
                    && !nonce.isEmpty()){
                isValidParam = true;
                return;
            }
        }
        isValidParam = false;
    }
}

public class URLAuthorize {
    private static boolean hasServicePermission(IService service,String scope){
        ListUtil<ServicePermission> checker = new ListUtil<>();
        switch (scope){
            case "openid":
                return checker.hasElement(service.getUsedPermission(),ServicePermission.READ_UUID);
            case "openid%20email":
                return checker.hasElement(service.getUsedPermission(),ServicePermission.EDIT_MAIL) && checker.hasElement(service.getUsedPermission(),ServicePermission.READ_UUID);
            default:
                return false;
        }
    }

    public static void mainFlow(io.javalin.http.Context ctx){
        AuthorizeParam param = new AuthorizeParam(ctx);
        if(!param.isValidParam){
            ctx.status(StatusCode.BadRequest.code())
                    .json(MapBuilder.New().put("status","bad parameter").build());
            return;
        }
        Optional<IService> serviceOptional = IServiceStore.get().getService(param.clientID);
        if(!serviceOptional.isPresent()){
            ctx.status(StatusCode.BadRequest.code())
                    .json(MapBuilder.New().put("status","Client ID is not valid").build());
            return;
        }
        serviceOptional.ifPresent(service->{
            if(!service.getRedirectURL().equalsIgnoreCase(param.redirect_uri)){
                ctx.status(StatusCode.BadRequest.code())
                        .json(MapBuilder.New().put("status","Redirect_URL is not valid").build());
                return;
            }
            if(!hasServicePermission(service,param.scope)){
                ctx.status(StatusCode.BadRequest.code())
                        .json(MapBuilder.New().put("status","Service hasn't permission").build());
                return;
            }
            //TODO 認可認証画面に遷移する


        });

    }
}
