package pers.zkx.algo.rpc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pers.zkx.algo.rpc.client.RpcClient;
import pers.zkx.algo.rpc.registry.InMemoryServiceRegistry;
import pers.zkx.algo.rpc.registry.ServiceRegistry;
import pers.zkx.algo.rpc.server.RpcServer;

import static org.assertj.core.api.Assertions.assertThat;

class RpcIntegrationTest {

    private RpcServer server;
    private RpcClient client;
    private ServiceRegistry registry;
    private Thread serverThread;

    @BeforeEach
    void setUp() throws Exception {
        registry = new InMemoryServiceRegistry();
        
        // 启动服务器
        server = new RpcServer("localhost", 9999, registry);
        server.registerService(HelloService.class, new HelloServiceImpl());
        
        serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception e) {
                // Server stopped
            }
        });
        serverThread.start();
        
        // 等待服务器启动，使用轮询检查
        int maxRetries = 50;
        int retryCount = 0;
        while (!server.isRunning() && retryCount < maxRetries) {
            Thread.sleep(100);
            retryCount++;
        }
        
        if (!server.isRunning()) {
            throw new RuntimeException("服务器启动超时");
        }
        
        // 创建客户端
        client = new RpcClient(registry);
    }

    @AfterEach
    void tearDown() {
        if (server != null) {
            server.stop();
        }
        if (serverThread != null) {
            serverThread.interrupt();
        }
    }

    @Test
    void testRpcCall() {
        HelloService proxy = client.getProxy(HelloService.class);
        
        String result = proxy.sayHello("World");
        assertThat(result).isEqualTo("Hello, World!");
    }

    @Test
    void testRpcCallWithMultipleParameters() {
        HelloService proxy = client.getProxy(HelloService.class);
        
        int result = proxy.add(10, 20);
        assertThat(result).isEqualTo(30);
    }
}
