# algo-thread

## 模块说明

本模块包含**线程和并发编程**相关的实现和示例，用于学习和实践 Java 并发编程。

## 模块位置

```
algo-test/algo-thread/
└── src/main/java/pers/zkx/algo/
    ├── thread/         # 并发编程示例
    └── string/         # 字符串性能测试
```

## 功能模块

### 1. 并发编程 (thread)

#### PrintOddEven.java
- **功能**：多线程交替打印奇偶数
- **知识点**：线程同步、wait/notify 机制
- **用途**：经典的多线程协作问题

#### AtomicIntegerAddition.java
- **功能**：使用原子类进行并发累加
- **知识点**：AtomicInteger、无锁编程
- **用途**：高性能并发计数

#### SynchronizedAddition.java
- **功能**：使用 synchronized 进行并发累加
- **知识点**：synchronized 关键字、锁机制
- **用途**：对比不同同步方式的性能

### 2. 性能测试 (string)

#### StringConcatBenchmark.java
- **功能**：字符串拼接性能基准测试
- **知识点**：String、StringBuilder、StringBuffer 性能对比
- **用途**：了解不同字符串操作的性能差异

#### Item.java
- **功能**：基准测试的辅助类

## 依赖关系

本模块没有外部依赖，使用 Java 标准库实现。

## 使用示例

### 运行奇偶数打印
```bash
cd algo-test/algo-thread
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.thread.PrintOddEven"
```

### 运行性能测试
```bash
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.string.StringConcatBenchmark"
```

## 学习重点

1. **线程同步**：wait/notify、synchronized 的使用
2. **原子操作**：AtomicInteger 等原子类的应用
3. **性能对比**：不同并发方案的性能差异
4. **字符串优化**：String 操作的性能陷阱

## 优化建议

1. **代码结构**：
   - `string` 包放在 `algo-thread` 模块下略显不妥
   - **建议**：将 string 相关代码移到 `algo-base` 模块或独立模块

2. **测试完善**：
   - 建议添加 JUnit 单元测试，验证并发正确性
   - 可以使用 JMH (Java Microbenchmark Harness) 进行更专业的性能测试

3. **示例扩展**：
   - 可以添加更多并发编程模式：生产者-消费者、读写锁等
   - 可以添加线程池使用示例
