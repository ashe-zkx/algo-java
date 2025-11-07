package pers.zkx.algo.rpc;

import pers.zkx.algo.rpc.client.RpcClient;
import pers.zkx.algo.rpc.registry.InMemoryServiceRegistry;
import pers.zkx.algo.rpc.registry.ServiceRegistry;
import pers.zkx.algo.rpc.server.RpcServer;

/**
 * RPC框架使用示例
 * 
 * 演示如何使用RPC框架实现远程服务调用
 */
public class RpcExample {

    /**
     * 示例服务接口
     */
    public interface CalculatorService {
        int add(int a, int b);
        int subtract(int a, int b);
        int multiply(int a, int b);
        double divide(int a, int b);
    }

    /**
     * 示例服务实现
     */
    public static class CalculatorServiceImpl implements CalculatorService {
        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public int subtract(int a, int b) {
            return a - b;
        }

        @Override
        public int multiply(int a, int b) {
            return a * b;
        }

        @Override
        public double divide(int a, int b) {
            if (b == 0) {
                throw new IllegalArgumentException("除数不能为0");
            }
            return (double) a / b;
        }
    }

    public static void main(String[] args) throws Exception {
        // 创建服务注册中心
        ServiceRegistry registry = new InMemoryServiceRegistry();

        // 启动RPC服务器
        RpcServer server = new RpcServer("localhost", 8888, registry);
        server.registerService(CalculatorService.class, new CalculatorServiceImpl());

        Thread serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        // 等待服务器启动
        while (!server.isRunning()) {
            Thread.sleep(100);
        }

        // 创建RPC客户端
        RpcClient client = new RpcClient(registry);
        CalculatorService calculator = client.getProxy(CalculatorService.class);

        // 调用远程服务
        System.out.println("=== RPC框架使用示例 ===");
        System.out.println("10 + 5 = " + calculator.add(10, 5));
        System.out.println("10 - 5 = " + calculator.subtract(10, 5));
        System.out.println("10 * 5 = " + calculator.multiply(10, 5));
        System.out.println("10 / 5 = " + calculator.divide(10, 5));

        // 停止服务器
        server.stop();
        serverThread.interrupt();
    }
}
