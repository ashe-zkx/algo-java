# algo-serializable

## 模块说明

本模块包含**序列化相关的实验和测试**，探索不同序列化方案的使用和特性。

## 模块位置

```
algo-test/algo-serializable/
└── src/main/java/pers/zkx/algo/
    ├── thrift/              # Thrift 序列化测试
    │   └── UserTest.java
    └── map/                 # HashMap 相关实验
        └── HashMapSizeMismatchDemo.java
```

## 功能模块

### 1. Thrift 序列化测试 (thrift)

#### UserTest.java
- **功能**：测试 Thrift 序列化和反序列化
- **知识点**：Apache Thrift 的使用
- **用途**：验证跨语言数据交换能力

**依赖**：本模块依赖 `algo-api` 模块中定义的 Thrift 数据结构

### 2. HashMap 实验 (map)

#### HashMapSizeMismatchDemo.java
- **功能**：演示 HashMap 容量和实际大小不匹配的场景
- **知识点**：HashMap 内部机制、扩容原理
- **用途**：深入理解 HashMap 的工作原理

## 依赖关系

```xml
<dependency>
    <groupId>pers.zkx</groupId>
    <artifactId>algo-api</artifactId>
</dependency>
```

本模块依赖 `algo-api` 模块，使用其中定义的 Thrift 数据结构进行序列化测试。

## 序列化方案对比

### 常见序列化方案
1. **Java 原生序列化**
   - 优点：简单易用，JDK 内置
   - 缺点：性能较差，安全风险，不跨语言

2. **Thrift**
   - 优点：高性能，跨语言支持，接口定义清晰
   - 缺点：需要编写 IDL 文件，学习成本较高

3. **Protobuf**
   - 优点：性能优异，体积小，跨语言
   - 缺点：需要编译 proto 文件

4. **JSON**
   - 优点：可读性好，易于调试，跨语言
   - 缺点：体积较大，性能一般

5. **Kryo**
   - 优点：高性能，专为 Java 优化
   - 缺点：仅支持 Java，版本兼容性问题

## 使用示例

### 运行 Thrift 测试
```bash
cd algo-test/algo-serializable
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.thrift.UserTest"
```

### 运行 HashMap 演示
```bash
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.map.HashMapSizeMismatchDemo"
```

## 优化建议

1. **模块命名**：
   - 当前命名为 `algo-serializable` 不够准确（应为 `serialization`）
   - **建议**：重命名为 `algo-serialization` 或保持当前名称

2. **内容整合**：
   - `map` 包下的 HashMap 测试与序列化无关
   - **建议**：将 HashMap 相关代码移到 `algo-base` 或独立的学习模块

3. **功能扩展**：
   - 可以添加更多序列化方案的对比测试（JSON、Protobuf、Kryo）
   - 建议添加性能基准测试，对比不同方案的序列化速度和体积

4. **测试完善**：
   - 建议添加单元测试，验证序列化/反序列化的正确性
   - 可以添加边界条件测试（空对象、大对象、嵌套对象）
