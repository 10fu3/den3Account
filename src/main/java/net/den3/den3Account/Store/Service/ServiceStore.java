package net.den3.den3Account.Store.Service;

import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.External.IService;
import net.den3.den3Account.External.Service;
import net.den3.den3Account.Store.IDBAccess;
import net.den3.den3Account.Store.IStore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceStore implements IServiceStore{
    private static final IDBAccess store = IStore.getInstance().getDB();
    private static final ServiceStore SINGLE = new ServiceStore();
    public static ServiceStore getInstance() {
        return SINGLE;
    }

    public ServiceStore(){
        store.controlSQL((con)->{
            try{
                List<PreparedStatement> createTables = new ArrayList<>();
                for(ServicePermission p : ServicePermission.values()){
                    PreparedStatement s = con.prepareStatement
                                            ("CREATE TABLE IF NOT EXISTS service_perm_"+p.getName()+
                                                    "("
                                             + "uuid VARCHAR(256) PRIMARY KEY)");
                    createTables.add(s);
                }

                //テーブルがなかったら作る仕組み
                createTables.add(con.prepareStatement("CREATE TABLE IF NOT EXISTS service ("
                        +"uuid VARCHAR(256) PRIMARY KEY,"
                        +"name VARCHAR(256),"
                        +"admin_id VARCHAR(256),"
                        +"url VARCHAR(256),"
                        +"icon VARCHAR(256),"
                        +"description VARCHAR(256)"
                        +")"));

                return Optional.of(createTables);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
            return null;
        });
    }

    /**
     * 登録された外部連携サービスをすべてDBから取得する
     * @return サービスのリスト
     */
    @Override
    public Optional<List<IService>> getServices() {
        Optional<List<Map<String, String>>> lineBySQL = store.getLineBySQL((con) -> {
            try {
                return Optional.of(con.prepareStatement("SELECT * FROM service"));
            } catch (SQLException ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
        },"service");
        return lineBySQL.map(maps -> maps
                .stream()
                .map(map -> new Service()
                        .setServiceID(map.get("uuid"))
                        .setServiceName(map.get("name"))
                        .setAdminID(map.get("admin_id"))
                        .setRedirectURL(map.get("url"))
                        .setServiceIconURL(map.get("icon"))
                        .setServiceDescription(map.get("description")))
                .collect(Collectors.toList()));
    }

    /**
     * 登録された外部連携サービスのうちIDの合致するものを返す
     * @param id 外部連携サービスのID
     * @return 外部連携サービスエンティティ
     */
    @Override
    public Optional<IService> getService(String id) {
        Optional<List<Map<String, String>>> lineBySQL = store.getLineBySQL((con) -> {
            try {
                PreparedStatement pS = con.prepareStatement("SELECT * FROM service where uuid = ?");
                pS.setString(1,id);
                return Optional.of(pS);
            } catch (SQLException ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
        },"service");
        return lineBySQL.map(maps -> maps
                .stream()
                .map(map -> (IService) new Service()
                    .setServiceID(map.get("uuid"))
                    .setServiceName(map.get("name"))
                    .setAdminID(map.get("admin_id"))
                    .setRedirectURL(map.get("url"))
                    .setServiceIconURL(map.get("icon"))
                    .setServiceDescription(map.get("description")))
                .collect(Collectors.toList()).stream().findFirst()).flatMap(l->l);
    }


    /**
     *
     * @param service
     * @return
     */
    @Override
    public boolean updateService(IService service) {
        return store.controlSQL((con)->{
            try {
                PreparedStatement pS = con.prepareStatement("UPDATE service SET name=?, admin_id=?, url=?, icon=?, description=? WHERE uuid=?;");
                pS.setString(1,service.getServiceName());
                pS.setString(2,service.getAdminID());
                pS.setString(3,service.getRedirectURL());
                pS.setString(4,service.getServiceIconURL());
                pS.setString(5,service.getServiceDescription());
                pS.setString(6,service.getServiceID());
                return Optional.of(Arrays.asList(pS));
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    @Override
    public boolean deleteService(String id) {
        return store.controlSQL((con)->{
            List<PreparedStatement> sqlRequests = new ArrayList<>();
            try {
                PreparedStatement servicePS = con.prepareStatement("DELETE FROM service WHERE uuid = ?;");
                servicePS.setString(1,id);
                sqlRequests.add(servicePS);
                PreparedStatement permPS;
                for (int i = 0; (i < ServicePermission.values().length); i++) {
                    permPS = con.prepareStatement("DELETE FROM service_perm_? WHERE uuid = ?;");
                    permPS.setString(1,ServicePermission.values()[i].getName());
                    permPS.setString(2,id);
                    sqlRequests.add(permPS);
                }
                return Optional.of(sqlRequests);
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    @Override
    public boolean addService(IService service) {
        return store.controlSQL((con)->{
            try {
                List<PreparedStatement> lps = new ArrayList<>();
                //INSET文の発行
                PreparedStatement pS = con.prepareStatement("INSERT INTO service VALUES (?,?,?,?,?,?) ;");
                //SQL文の個めの?にを代入する
                pS.setString(1, service.getServiceID());
                //SQL文の個めの?にを代入する
                pS.setString(2, service.getServiceName());
                //SQL文の個めの?にを代入する
                pS.setString(3, service.getAdminID());
                //SQL文の個めの?にを代入する
                pS.setString(4, service.getRedirectURL());
                //SQL文の個めの?にを代入する
                pS.setString(5, service.getServiceIconURL());
                //SQL文の個めの?にを代入する
                pS.setString(6, service.getServiceDescription());
                lps.add(pS);
                PreparedStatement permSQL;
                ServicePermission roopPerm;
                for (int i = 0; i < service.getUsedPermission().size(); i++) {
                    roopPerm = service.getUsedPermission().get(i);
                    permSQL =  con.prepareStatement("INSERT INTO service_perm_"+roopPerm.getName()+" VALUES (?);");
                    permSQL.setString(1,service.getServiceID());
                    lps.add(permSQL);
                }

                return Optional.of(lps);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
    }
}
