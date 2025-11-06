# algo-rpc

## 模块说明

本模块实现了一个**简单的 RPC（远程过程调用）框架**，用于学习和理解 RPC 的核心原理。

## 模块位置

```
algo-test/algo-rpc/
└── src/main/java/pers/zkx/algo/rpc/
    ├── client/             # RPC 客户端
    │   └── RpcClient.java
    ├── server/             # RPC 服务端
    │   └── RpcServer.java
    ├── registry/           # 服务注册与发现
    │   ├── ServiceRegistry.java
    │   └── InMemoryServiceRegistry.java
    ├── serialization/      # 序列化组件
    │   ├── Serializer.java
    │   └── JavaSerializer.java
    ├── protocol/           # 通信协议
    │   ├── RpcProtocol.java
    │   └── SimpleRpcProtocol.java
    ├── common/             # 公共类
    │   ├── RpcRequest.java
    │   ├── RpcResponse.java
    │   └── ServiceInfo.java
    └── RpcExample.java     # 使用示例
```

## 核心组件

### 1. RPC 客户端 (client)
- **RpcClient**：RPC 客户端实现
  - 使用 Java 动态代理创建远程服务代理
  - 封装网络通信细节
  - 自动序列化请求和响应

### 2. RPC 服务端 (server)
- **RpcServer**：RPC 服务端实现
  - 基于 Socket 监听客户端连接
  - 支持多线程并发处理请求
  - 自动调用本地服务方法

### 3. 服务注册与发现 (registry)
- **ServiceRegistry**：服务注册中心接口
- **InMemoryServiceRegistry**：内存实现
  - 支持服务注册、注销、发现
  - 线程安全的服务管理

### 4. 序列化组件 (serialization)
- **Serializer**：序列化接口
- **JavaSerializer**：Java 原生序列化实现
  - 对象序列化和反序列化
  - 支持任意可序列化对象

### 5. 通信协议 (protocol)
- **RpcProtocol**：协议接口
- **SimpleRpcProtocol**：简单协议实现
  - 协议格式：`[4字节长度] + [序列化数据]`
  - 解决 TCP 粘包问题

### 6. 公共类 (common)
- **RpcRequest**：RPC 请求封装（接口名、方法名、参数）
- **RpcResponse**：RPC 响应封装（结果或异常）
- **ServiceInfo**：服务信息（主机、端口）

## 使用示例

### 1. 定义服务接口
```java
public interface HelloService {
    String sayHello(String name);
}
```

### 2. 实现服务
```java
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
```

### 3. 启动服务端
```java
ServiceRegistry registry = new InMemoryServiceRegistry();
RpcServer server = new RpcServer("localhost", 9999, registry);
server.registerService(HelloService.class, new HelloServiceImpl());
server.start();
```

### 4. 客户端调用
```java
RpcClient client = new RpcClient(registry);
HelloService proxy = client.getProxy(HelloService.class);
String result = proxy.sayHello("World"); // 返回 "Hello, World!"
```

### 5. 运行示例
```bash
cd algo-test/algo-rpc
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.rpc.RpcExample"
```

## 技术特点

1. **基于 Socket**：使用 Java 原生 Socket 实现网络通信
2. **动态代理**：使用 JDK 动态代理实现客户端透明调用
3. **多线程支持**：服务端支持并发处理多个请求
4. **服务注册**：内置服务注册与发现机制
5. **协议简单**：长度前缀协议，简单可靠
6. **可扩展性**：接口化设计，易于替换序列化和协议实现

## 依赖关系

```xml
<dependencies>
    <!-- 测试依赖 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.assertj</groupId>
        <artifactId>assertj-core</artifactId>
    </dependency>
</dependencies>
```

## 测试用例

本模块包含完整的测试用例：
- **ServiceRegistryTest**：服务注册测试
- **SerializerTest**：序列化测试
- **RpcProtocolTest**：协议测试
- **RpcIntegrationTest**：集成测试
- **RpcCommonTest**：公共类测试

运行测试：
```bash
mvn test
```

## 安全说明

⚠️ **重要提示**：本框架为学习和演示目的设计，存在以下安全风险：

1. **Java 原生序列化风险**：
   - 存在已知的反序列化漏洞
   - 可能导致远程代码执行攻击

2. **使用建议**：
   - 仅在受信任的网络环境中使用
   - 生产环境建议使用更安全的序列化方案（JSON、Protobuf、Thrift）

3. **改进方向**：
   - 添加身份认证和授权机制
   - 替换序列化方案
   - 添加加密传输

## RPC 框架对比

| 框架 | 序列化 | 传输协议 | 服务发现 | 适用场景 |
|------|--------|----------|----------|----------|
| 本框架 | Java 原生 | Socket | 内存 | 学习演示 |
| Dubbo | Hessian/JSON | Netty | ZooKeeper | 微服务 |
| gRPC | Protobuf | HTTP/2 | 可选 | 跨语言 |
| Thrift | Thrift | 多种 | 自定义 | 跨语言 |

## 优化建议

1. **模块位置**：
   - 当前在 `algo-test` 下，但实现完整度较高
   - **建议**：可以考虑提升为一级模块 `algo-rpc`，体现其独立性

2. **功能扩展**：
   - 添加更多序列化方案（JSON、Protobuf）
   - 支持异步调用和回调
   - 添加连接池管理
   - 实现负载均衡和容错机制

3. **性能优化**：
   - 使用 Netty 替换 Socket，提升 IO 性能
   - 优化序列化性能
   - 添加请求缓存

4. **监控和治理**：
   - 添加调用链追踪
   - 添加性能监控
   - 支持熔断降级

5. **代码质量**：
   - 测试覆盖率已经不错，可以继续完善边界条件测试
   - 建议添加性能基准测试
