package pers.zkx.algo.rpc.client;

import pers.zkx.algo.rpc.common.RpcRequest;
import pers.zkx.algo.rpc.common.RpcResponse;
import pers.zkx.algo.rpc.common.ServiceInfo;
import pers.zkx.algo.rpc.protocol.RpcProtocol;
import pers.zkx.algo.rpc.protocol.SimpleRpcProtocol;
import pers.zkx.algo.rpc.registry.ServiceRegistry;
import pers.zkx.algo.rpc.serialization.JavaSerializer;
import pers.zkx.algo.rpc.serialization.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

/**
 * RPC客户端
 */
public class RpcClient {

    private final ServiceRegistry serviceRegistry;
    private final Serializer serializer;
    private final RpcProtocol protocol;

    public RpcClient(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.serializer = new JavaSerializer();
        this.protocol = new SimpleRpcProtocol();
    }

    /**
     * 获取服务代理
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvocationHandler(interfaceClass)
        );
    }

    /**
     * RPC调用处理器
     */
    private class RpcInvocationHandler implements InvocationHandler {
        private final Class<?> interfaceClass;

        public RpcInvocationHandler(Class<?> interfaceClass) {
            this.interfaceClass = interfaceClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 构建请求
            RpcRequest request = new RpcRequest();
            request.setRequestId(UUID.randomUUID().toString());
            request.setInterfaceName(interfaceClass.getName());
            request.setMethodName(method.getName());
            request.setParameterTypes(method.getParameterTypes());
            request.setParameters(args);

            // 服务发现
            List<ServiceInfo> services = serviceRegistry.discover(interfaceClass.getName());
            if (services.isEmpty()) {
                throw new RuntimeException("未找到服务: " + interfaceClass.getName());
            }

            // 简单选择第一个服务
            ServiceInfo serviceInfo = services.get(0);

            // 发送请求并获取响应
            return sendRequest(serviceInfo, request);
        }

        private Object sendRequest(ServiceInfo serviceInfo, RpcRequest request) throws IOException {
            try (Socket socket = new Socket(serviceInfo.getHost(), serviceInfo.getPort())) {
                // 编码请求
                protocol.encode(request, socket.getOutputStream(), serializer);

                // 解码响应
                RpcResponse response = protocol.decode(socket.getInputStream(), RpcResponse.class, serializer);

                if (response.hasException()) {
                    throw new RuntimeException("远程调用异常", response.getException());
                }

                return response.getResult();
            }
        }
    }
}
