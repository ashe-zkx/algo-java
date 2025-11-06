# algo-java

使用 Java 实现常用算法、数据结构和分布式系统组件的学习项目。

## 项目概述

本项目是一个综合性的算法学习和实践仓库，包含基础算法实现、LeetCode 题解、并发编程、分布式系统和 RPC 框架等多个模块。

## 模块结构

```
algo-java/
├── algo-dependencies/      # BOM 依赖管理模块
├── algo-base/              # 基础算法和数据结构
├── algo-leetcode/          # LeetCode 刷题
├── algo-api/               # Thrift API 定义
└── algo-test/              # 实践学习模块（聚合）
    ├── algo-thread/        # 线程与并发编程
    ├── algo-raft/          # Raft 一致性算法
    ├── algo-serializable/  # 序列化实验
    └── algo-rpc/           # 简单 RPC 框架
```

## 模块详细介绍

### 1. algo-dependencies - 依赖管理模块
**功能**：Bill of Materials (BOM) 模块，统一管理所有模块的依赖版本。

**位置**：`algo-dependencies/`

**依赖管理**：
- Apache Commons (Lang3, Collections4)
- Lombok、SLF4J
- Apache Thrift
- JUnit 4/5、AssertJ

**优势**：
- 集中管理版本，避免依赖冲突
- 简化子模块 POM 配置
- 便于统一升级依赖

📄 详细文档：[algo-dependencies/README.md](algo-dependencies/README.md)

---

### 2. algo-base - 基础算法模块
**功能**：包含常用的数据结构和算法实现。

**位置**：`algo-base/src/main/java/pers/zkx/algo/base/`

**内容模块**：
- **排序算法**：快速排序等
- **字符串算法**：KMP 算法
- **数据结构**：
  - 缓存：LRU、LRU with TTL
  - 缓冲区：环形缓冲区
  - 限流：令牌桶算法
  - 其他：布隆过滤器

**依赖**：无外部依赖

📄 详细文档：[algo-base/README.md](algo-base/README.md)

---

### 3. algo-leetcode - 刷题模块
**功能**：LeetCode 题解和算法练习，按难度分类。

**位置**：`algo-leetcode/src/main/java/pers/zkx/algo/leetcode/`

**目录结构**：
- `simple/` - 简单难度题目
- `mid/` - 中等难度题目
- `hard/` - 困难难度题目
- `week/` - 周赛题目

**代码量**：约 59 个题解文件

**注意**：使用 Java 21 特性，需要 JDK 21+

📄 详细文档：[algo-leetcode/README.md](algo-leetcode/README.md)

---

### 4. algo-api - Thrift API 模块
**功能**：包含 Apache Thrift IDL 定义和生成的 Java API 代码。

**位置**：`algo-api/src/main/thrift/`

**IDL 定义**：
- **数据结构**：User、Address
- **服务接口**：UserService (CRUD 操作)

**依赖**：
- Apache Thrift (0.22.0)
- javax.annotation-api
- slf4j-api

**特性**：
- 跨平台配置（Windows/Linux/macOS）
- Maven 自动生成 Java 代码

📄 详细文档：[algo-api/README.md](algo-api/README.md)

---

### 5. algo-test - 实践学习模块（聚合）
**功能**：包含多个实验性和学习性质的子模块。

**位置**：`algo-test/`

**子模块**：

#### 5.1 algo-thread - 线程与并发编程
- 多线程协作（奇偶数打印）
- 原子操作 vs synchronized 性能对比
- 字符串拼接性能测试

📄 详细文档：[algo-test/algo-thread/README.md](algo-test/algo-thread/README.md)

#### 5.2 algo-raft - Raft 一致性算法
- Raft 算法的基础数据结构
- 节点角色、日志条目、选举逻辑

📄 详细文档：[algo-test/algo-raft/README.md](algo-test/algo-raft/README.md)

#### 5.3 algo-serializable - 序列化实验
- Thrift 序列化测试
- HashMap 内部机制实验

**依赖**：algo-api

📄 详细文档：[algo-test/algo-serializable/README.md](algo-test/algo-serializable/README.md)

#### 5.4 algo-rpc - 简单 RPC 框架
完整的 RPC 框架实现，包括：
- 客户端和服务端
- 服务注册与发现
- 序列化和通信协议
- 动态代理

**特性**：
- 基于 Socket 的网络通信
- Java 动态代理
- 多线程并发支持
- 完整的单元测试

📄 详细文档：[algo-test/algo-rpc/README.md](algo-test/algo-rpc/README.md)

---

---

## 技术栈

- **Java 版本**：21
- **构建工具**：Maven 3.x
- **依赖管理**：BOM (Bill of Materials) 模式
- **RPC 框架**：Apache Thrift 0.22.0
- **测试框架**：JUnit 4/5、AssertJ

## 项目特色

1. **模块化设计**：采用 Maven 多模块结构，职责清晰
2. **依赖管理规范**：使用 BOM 统一管理版本
3. **实践导向**：不仅有算法实现，还有完整的 RPC 框架
4. **测试完善**：algo-rpc 模块包含完整的单元测试和集成测试
5. **学习友好**：每个模块都有详细的 README 文档

## 快速开始

### 环境要求

- JDK 21+（algo-leetcode 模块需要）
- Maven 3.6+
- Apache Thrift 0.22.0（algo-api 模块生成代码需要）

### 构建项目

```bash
# 克隆仓库
git clone https://github.com/ashe-zkx/algo-java.git
cd algo-java

# 编译整个项目
mvn clean install -DskipTests

# 只编译特定模块
mvn -pl algo-base -am clean install
```

### 运行示例

#### RPC 框架示例
```bash
cd algo-test/algo-rpc
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.rpc.RpcExample"
```

#### 并发编程示例
```bash
cd algo-test/algo-thread
mvn compile exec:java -Dexec.mainClass="pers.zkx.algo.thread.PrintOddEven"
```

## 依赖关系图

```
algo-java (root)
├── algo-dependencies (BOM)
├── algo-base (独立)
├── algo-leetcode (独立)
├── algo-api (Thrift, slf4j)
└── algo-test (聚合模块)
    ├── algo-thread (独立)
    ├── algo-raft (独立)
    ├── algo-serializable (依赖: algo-api)
    └── algo-rpc (依赖: junit, assertj)
```

---

---

## 模块优化建议

基于对项目结构的深入分析，提出以下优化建议：

### 1. 模块命名优化

#### 当前问题
- `algo-test` 命名不够准确，实际包含完整功能实现，而非仅测试代码

#### 建议方案
- **方案 A**（保守）：保持 `algo-test` 命名，在文档中明确说明其用途
- **方案 B**（推荐）：重命名为 `algo-practice` 或 `algo-learning`，更准确反映其学习实践性质

### 2. 模块位置优化

#### algo-rpc 模块
**现状**：位于 `algo-test/algo-rpc`，作为子模块存在

**分析**：
- ✅ 实现完整度高（包含客户端、服务端、注册中心、序列化、协议）
- ✅ 测试覆盖率好（单元测试 + 集成测试）
- ✅ 文档完善
- ⚠️ 实际上是一个独立的 RPC 框架，而非简单的测试代码

**建议**：
- **方案 A**（推荐）：提升为一级模块 `algo-rpc`，与 `algo-base`、`algo-leetcode` 同级
  - 优点：体现其独立性和重要性，便于单独维护和演进
  - 缺点：需要调整目录结构
- **方案 B**（保守）：保持当前位置，但在文档中强调其完整性

#### algo-thread 模块
**现状**：`algo-test/algo-thread` 中包含 `string` 包（字符串性能测试）

**问题**：`string` 包与线程并发主题不符

**建议**：
- 将 `string` 包迁移到 `algo-base/utils` 或创建独立的性能测试模块
- `algo-thread` 专注于并发编程

#### algo-serializable 模块
**现状**：包含 `thrift` 和 `map` 两个不相关的包

**问题**：
- `map` 包的 HashMap 测试与序列化无关
- 模块职责不够单一

**建议**：
- 将 `map` 包迁移到 `algo-base` 或独立模块
- `algo-serializable` 专注于序列化对比和测试

### 3. 依赖优化

#### 当前依赖状况
✅ **做得好的地方**：
- 使用 BOM 统一管理版本，避免冲突
- 大部分模块无外部依赖，保持轻量
- 测试依赖隔离合理

⚠️ **可以改进**：
- `algo-rpc` 的 `assertj-core` 依赖未设置 `<scope>test</scope>`
- 建议在 BOM 中明确说明每个依赖的用途

#### 建议的依赖结构

```xml
<!-- algo-rpc/pom.xml -->
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
        <scope>test</scope> <!-- 应添加此行 -->
    </dependency>
</dependencies>
```

### 4. 文档优化

✅ **已完成**：
- 所有模块都有详细的 README.md
- 包含功能说明、使用示例、优化建议

✅ **建议保持**：
- 定期更新文档与代码同步
- 为复杂算法添加注释和图示

### 5. 测试覆盖

**现状**：
- ✅ `algo-rpc` 测试覆盖良好
- ⚠️ `algo-base`、`algo-leetcode` 缺少单元测试
- ⚠️ `algo-thread` 的并发代码未做验证测试

**建议**：
- 为 `algo-base` 的核心算法添加单元测试
- 为 `algo-thread` 添加并发正确性验证测试
- 使用 JMH 添加性能基准测试

### 6. Java 版本兼容性

**现状**：
- `algo-leetcode` 使用 Java 21 特性（`List.getFirst()`、`List.getLast()`）
- 其他模块兼容 Java 17

**建议**：
- **方案 A**：统一使用 Java 21，享受新特性
- **方案 B**：`algo-leetcode` 使用 Java 21，其他模块保持 Java 17 兼容
- **方案 C**：修改 `algo-leetcode` 代码，全部兼容 Java 17

---

## 优化实施优先级

### 🔴 高优先级（建议立即处理）
1. ✅ 为所有模块创建 README.md（已完成）
2. 修复 `algo-rpc` 的 assertj 依赖 scope
3. 清理不合理的代码放置（string、map 包）

### 🟡 中优先级（建议近期处理）
1. 为核心算法添加单元测试
2. 统一 Java 版本策略
3. 考虑是否提升 `algo-rpc` 为一级模块

### 🟢 低优先级（长期优化）
1. 添加性能基准测试
2. 考虑重命名 `algo-test` 模块
3. 添加 CI/CD 流程

---
