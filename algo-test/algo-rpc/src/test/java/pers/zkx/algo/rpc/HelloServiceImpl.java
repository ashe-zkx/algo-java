package pers.zkx.algo.rpc;

/**
 * 测试服务实现
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
    
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
