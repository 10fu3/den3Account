package net.den3.den3Account.Store;

import net.den3.den3Account.External.IService;

import java.util.List;
import java.util.Optional;

public interface IServiceStore {
    static IServiceStore getInstance() {
        return ServiceStore.getInstance();
    }

    Optional<List<IService>> getServices();

    Optional<IService> getService(String id);

    boolean updateService(IService service);

    boolean deleteService(String id);

    boolean addService(IService service);



}
