package net.den3.den3Account.Security;

import com.auth0.jwt.interfaces.DecodedJWT;
import net.den3.den3Account.Entity.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JWTAccessManager implements io.javalin.core.security.AccessManager{
    private static final JWTAccessManager SINGLE = new JWTAccessManager();
    //k
    private String userPermissionClaim;
    private Map<String, Permission> permissionsMapping = new HashMap<>();
    private Permission defaultPermission = Permission.NORMAL;

    public static JWTAccessManager getInstance() {
        return SINGLE;
    }

    public JWTAccessManager(){
        permissionsMapping.put("normal",Permission.NORMAL);
        permissionsMapping.put("admin",Permission.ADMIN);
    }

    private Permission extractPermission(io.javalin.http.Context context) {
        if (!JWTJavalin.containsJWT(context)) {
            return defaultPermission;
        }

        DecodedJWT jwt = JWTJavalin.getDecodedFromContext(context);
        String userLevel = jwt.getClaim(userPermissionClaim).asString();

        return Optional.ofNullable(permissionsMapping.get(userLevel)).orElse(defaultPermission);
    }

    @Override
    public void manage(@NotNull io.javalin.http.Handler handler, @NotNull io.javalin.http.Context context, @NotNull Set<io.javalin.core.security.Role> perms) throws Exception {
        Permission permission = extractPermission(context);

        if (!perms.contains(permission)){
            handler.handle(context);
        } else {
            context.status(401).result("Unauthorized");
        }
    }
}
