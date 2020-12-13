package net.den3.den3Account.Router.Service;

import net.den3.den3Account.Entity.CSRFResult;
import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Logic.CSRF;
import net.den3.den3Account.Store.Service.IServiceStore;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class URLGetService {
    public static void mainFlow(io.javalin.http.Context ctx) {
        CSRFResult csrfResult = CSRF.mainFlow(ctx);
        //CSRF攻撃?
        if (csrfResult != CSRFResult.SUCCESS) {
            ctx.status(StatusCode.Unauthorized.code());
            return;
        }
        Optional<Map<String, Object>> j = ParseJSON.convertToMap(ctx.body());
        if((!j.isPresent())){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        if(!j.get().containsKey("service-id")){
            ctx.json(ParseJSON.convertToFromList(
                    IServiceStore.get().getServices
                            (csrfResult.getJWT().get().getSubject()).orElse(new ArrayList<>())
                            .stream()
                            .map(s->ParseJSON.convertToJSON(s.toMap()).orElse("")).collect(Collectors.toList())
            ));
        }else{
            Optional<IService> target = IServiceStore.get().getService(String.valueOf(j.get().get("service-id")));
            if(!target.isPresent()){
                ctx.status(StatusCode.NotFound.code());
                return;
            }
            ctx.status(StatusCode.OK.code()).json(ParseJSON.convertToJSON(target.get().toMap()).orElse(""));
        }
    }
}
