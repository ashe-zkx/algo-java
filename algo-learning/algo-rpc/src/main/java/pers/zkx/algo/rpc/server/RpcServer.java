package pers.zkx.algo.rpc.server;

import pers.zkx.algo.rpc.common.RpcRequest;
import pers.zkx.algo.rpc.common.RpcResponse;
import pers.zkx.algo.rpc.common.ServiceInfo;
import pers.zkx.algo.rpc.protocol.RpcProtocol;
import pers.zkx.algo.rpc.protocol.SimpleRpcProtocol;
import pers.zkx.algo.rpc.registry.ServiceRegistry;
import pers.zkx.algo.rpc.serialization.JavaSerializer;
import pers.zkx.algo.rpc.serialization.Serializer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RPC服务器
 */
public class RpcServer {

    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Serializer serializer;
    private final RpcProtocol protocol;
    private final ExecutorService executor;
    private ServerSocket serverSocket;
    private volatile boolean running = false;

    public RpcServer(String host, int port, ServiceRegistry serviceRegistry) {
        this(host, port, serviceRegistry, 10);
    }

    public RpcServer(String host, int port, ServiceRegistry serviceRegistry, int threadPoolSize) {
        this.host = host;
        this.port = port;
        this.serviceRegistry = serviceRegistry;
        this.serializer = new JavaSerializer();
        this.protocol = new SimpleRpcProtocol();
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * 注册服务
     */
    public <T> void registerService(Class<T> interfaceClass, T serviceImpl) {
        String serviceName = interfaceClass.getName();
        serviceMap.put(serviceName, serviceImpl);
        
        // 向注册中心注册服务
        ServiceInfo serviceInfo = new ServiceInfo(serviceName, host, port);
        serviceRegistry.register(serviceInfo);
    }

    /**
     * 启动服务器
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        
        System.out.println("RPC服务器启动在 " + host + ":" + port);
        
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(() -> handleRequest(socket));
            } catch (IOException e) {
                if (running) {
                    System.err.println("接受连接失败: " + e.getMessage());
                }
            }
        }
    }

    /**
     * 处理请求
     */
    private void handleRequest(Socket socket) {
        try {
            // 解码请求
            RpcRequest request = protocol.decode(socket.getInputStream(), RpcRequest.class, serializer);
            
            // 处理请求
            RpcResponse response = new RpcResponse();
            response.setRequestId(request.getRequestId());
            
            try {
                Object result = invoke(request);
                response.setResult(result);
            } catch (Exception e) {
                response.setException(e);
            }
            
            // 编码响应
            protocol.encode(response, socket.getOutputStream(), serializer);
        } catch (Exception e) {
            System.err.println("处理请求失败: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * 调用服务方法
     */
    private Object invoke(RpcRequest request) throws Exception {
        Object service = serviceMap.get(request.getInterfaceName());
        if (service == null) {
            throw new RuntimeException("服务未找到: " + request.getInterfaceName());
        }
        
        return service.getClass()
                .getMethod(request.getMethodName(), request.getParameterTypes())
                .invoke(service, request.getParameters());
    }

    /**
     * 停止服务器
     */
    public void stop() {
        running = false;
        
        // 注销所有服务
        for (String serviceName : serviceMap.keySet()) {
            ServiceInfo serviceInfo = new ServiceInfo(serviceName, host, port);
            serviceRegistry.unregister(serviceInfo);
        }
        
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // ignore
            }
        }
        
        executor.shutdown();
        System.out.println("RPC服务器已停止");
    }

    /**
     * 检查服务器是否正在运行
     */
    public boolean isRunning() {
        return running && serverSocket != null && !serverSocket.isClosed();
    }
}
