package net.den3.den3Account.Store.Service;

import net.den3.den3Account.Entity.Service.IService;

import java.util.List;
import java.util.Optional;

public interface IServiceStore {
    static IServiceStore get() {
        return ServiceStore.getInstance();
    }

    Optional<List<IService>> getServices();

    Optional<IService> getService(String id);

    boolean updateService(IService service);

    boolean deleteService(String id);

    boolean addService(IService service);

    Optional<List<IService>> getServices(String adminID);


}
