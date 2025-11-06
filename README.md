# algo-java
使用java实现常用算法
## 模块介绍
### base 基础算法模块
### leetcode 刷题模块
### rpc 简单RPC框架模块

实现了一个简单的RPC（远程过程调用）框架，包含以下核心功能：

#### 核心组件
- **RpcServer**: RPC服务端，负责接收和处理客户端请求
- **RpcClient**: RPC客户端，使用动态代理实现透明的远程调用
- **ServiceRegistry**: 服务注册中心，支持服务的注册、注销和发现
- **Serializer**: 序列化组件，支持对象的序列化和反序列化
- **RpcProtocol**: 通信协议，定义请求和响应的编解码规则

#### 使用示例

1. 定义服务接口：
```java
public interface HelloService {
    String sayHello(String name);
}
```

2. 实现服务：
```java
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
```

3. 启动服务端：
```java
ServiceRegistry registry = new InMemoryServiceRegistry();
RpcServer server = new RpcServer("localhost", 9999, registry);
server.registerService(HelloService.class, new HelloServiceImpl());
server.start();
```

4. 客户端调用：
```java
RpcClient client = new RpcClient(registry);
HelloService proxy = client.getProxy(HelloService.class);
String result = proxy.sayHello("World"); // 返回 "Hello, World!"
```

#### 特性
- 基于Java原生Socket实现网络通信
- 使用Java动态代理实现客户端透明调用
- 支持多线程并发处理请求
- 内置服务注册与发现机制
- 简单的协议设计（长度+内容）
- Java原生序列化支持

#### 安全说明
⚠️ **重要提示**：本框架为学习和演示目的设计，使用了Java原生序列化。在生产环境中使用时，请注意：
- Java原生序列化存在已知的安全风险（反序列化漏洞）
- 建议仅在受信任的网络环境中使用
- 对于生产环境，建议替换为更安全的序列化方案（JSON、Protobuf等）

