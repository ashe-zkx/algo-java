package pers.zkx.algo.rpc.registry;

import pers.zkx.algo.rpc.common.ServiceInfo;

import java.util.List;

/**
 * 服务注册中心接口
 */
public interface ServiceRegistry {
    /**
     * 注册服务
     */
    void register(ServiceInfo serviceInfo);

    /**
     * 注销服务
     */
    void unregister(ServiceInfo serviceInfo);

    /**
     * 发现服务
     */
    List<ServiceInfo> discover(String serviceName);
}
