# algo-dependencies

## 模块说明

本模块是一个 **BOM (Bill of Materials)** 模块，用于统一管理整个项目的依赖版本。

## 功能

- 集中管理所有第三方依赖的版本号
- 确保所有子模块使用相同版本的依赖
- 简化依赖管理，避免版本冲突
- 便于统一升级依赖版本

## 依赖管理

本模块管理以下依赖：

### 核心工具库
- **Apache Commons Lang3** (3.18.0): 提供常用的工具类
- **Apache Commons Collections4** (4.5.0): 提供增强的集合框架
- **Lombok** (1.18.32): 减少样板代码

### RPC 和序列化
- **Apache Thrift** (0.22.0): 跨语言 RPC 框架
- **javax.annotation-api** (1.3.2): Java 注解 API

### 日志
- **SLF4J** (2.0.9): 日志门面框架

### 测试框架
- **JUnit 4** (4.13.2): 经典测试框架
- **JUnit Jupiter** (5.13.2): JUnit 5 测试框架
- **AssertJ** (3.27.3): 流式断言库

## 使用方式

在根 `pom.xml` 中导入此 BOM：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>pers.zkx</groupId>
            <artifactId>algo-dependencies</artifactId>
            <version>${project.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

子模块无需指定版本号，直接使用：

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
```

## 优化建议

当前 BOM 设计合理，建议：
- 定期更新依赖版本以获取安全修复和新特性
- 考虑添加版本属性说明文档，方便维护
