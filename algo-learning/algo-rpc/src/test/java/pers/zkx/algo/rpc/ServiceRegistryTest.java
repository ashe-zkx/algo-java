package pers.zkx.algo.rpc;

import org.junit.jupiter.api.Test;
import pers.zkx.algo.rpc.common.ServiceInfo;
import pers.zkx.algo.rpc.registry.InMemoryServiceRegistry;
import pers.zkx.algo.rpc.registry.ServiceRegistry;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceRegistryTest {

    @Test
    void testRegisterAndDiscover() {
        ServiceRegistry registry = new InMemoryServiceRegistry();
        
        ServiceInfo serviceInfo = new ServiceInfo("TestService", "localhost", 8080);
        registry.register(serviceInfo);
        
        List<ServiceInfo> services = registry.discover("TestService");
        assertThat(services).hasSize(1);
        assertThat(services.get(0).getServiceName()).isEqualTo("TestService");
        assertThat(services.get(0).getHost()).isEqualTo("localhost");
        assertThat(services.get(0).getPort()).isEqualTo(8080);
    }

    @Test
    void testUnregister() {
        ServiceRegistry registry = new InMemoryServiceRegistry();
        
        ServiceInfo serviceInfo = new ServiceInfo("TestService", "localhost", 8080);
        registry.register(serviceInfo);
        registry.unregister(serviceInfo);
        
        List<ServiceInfo> services = registry.discover("TestService");
        assertThat(services).isEmpty();
    }

    @Test
    void testMultipleServices() {
        ServiceRegistry registry = new InMemoryServiceRegistry();
        
        ServiceInfo service1 = new ServiceInfo("TestService", "localhost", 8080);
        ServiceInfo service2 = new ServiceInfo("TestService", "localhost", 8081);
        
        registry.register(service1);
        registry.register(service2);
        
        List<ServiceInfo> services = registry.discover("TestService");
        assertThat(services).hasSize(2);
    }
}
