package net.den3.den3Account.Router.Service;

import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Store.Auth.ITokenStore;
import net.den3.den3Account.Store.Service.IServiceStore;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class URLGetService {
    public static void mainFlow(io.javalin.http.Context ctx) {
        Optional<Map<String, Object>> j = ParseJSON.convertToMap(ctx.body());
        if((!j.isPresent())){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        String uuid = ITokenStore.get().getAccountUUID(String.valueOf(j.get().get("token"))).orElse("");

        if(!j.get().containsKey("service-id")){
            ctx.json(ParseJSON.convertToFromList(
                    IServiceStore.get().getServices
                            (uuid).orElse(new ArrayList<>())
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
