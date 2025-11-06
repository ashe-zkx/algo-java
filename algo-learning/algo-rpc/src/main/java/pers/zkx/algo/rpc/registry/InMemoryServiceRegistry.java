package pers.zkx.algo.rpc.registry;

import pers.zkx.algo.rpc.common.ServiceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存的简单服务注册中心实现
 */
public class InMemoryServiceRegistry implements ServiceRegistry {

    private final Map<String, List<ServiceInfo>> registry = new ConcurrentHashMap<>();

    @Override
    public void register(ServiceInfo serviceInfo) {
        String serviceName = serviceInfo.getServiceName();
        registry.computeIfAbsent(serviceName, k -> new ArrayList<>()).add(serviceInfo);
    }

    @Override
    public void unregister(ServiceInfo serviceInfo) {
        String serviceName = serviceInfo.getServiceName();
        List<ServiceInfo> services = registry.get(serviceName);
        if (services != null) {
            services.removeIf(info -> info.getAddress().equals(serviceInfo.getAddress()));
            if (services.isEmpty()) {
                registry.remove(serviceName);
            }
        }
    }

    @Override
    public List<ServiceInfo> discover(String serviceName) {
        return registry.getOrDefault(serviceName, new ArrayList<>());
    }
}
