# algo-api

## 模块说明

本模块包含 **Apache Thrift IDL 定义**和通过 Maven 自动生成的 Java API 代码。Thrift 用于定义跨语言的服务接口和数据结构。

## 模块位置

```
algo-api/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── thrift/
            └── testUser.thrift
```

## IDL 定义

`testUser.thrift` 定义了以下内容：

### 数据结构
- **Address**: 地址信息（街道、城市、邮编）
- **User**: 用户信息（ID、姓名、年龄、地址、爱好、元数据）

### 服务接口
- **UserService**: 用户服务 API
  - `getUserById()`: 根据 ID 查询用户
  - `upsertUser()`: 创建或更新用户
  - `listUsers()`: 分页查询用户列表

## 依赖关系

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.thrift</groupId>
        <artifactId>libthrift</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.annotation</groupId>
        <artifactId>javax.annotation-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
    </dependency>
</dependencies>
```

## 代码生成

### 方法 A：使用 Maven 插件（推荐）

在项目根目录运行：

```bash
mvn -pl algo-api -am install
```

参数说明：
- `-pl algo-api`: 只构建 algo-api 模块
- `-am`: 同时构建依赖的模块
- `install`: 安装到本地 Maven 仓库 (~/.m2/repository)

生成的代码位置：
- `target/generated-sources/thrift/` 或 `target/generated-sources/`

### 方法 B：使用 Thrift CLI 手动生成

系统需安装 thrift 可执行文件：

```bash
cd algo-api
thrift --gen java -out src/main/java src/main/thrift/testUser.thrift
mvn install
```

## 跨平台配置

本模块 `pom.xml` 包含了三个 profile，自动适配不同操作系统：

- **Windows**: `D:\Environment\thrift\thrift.exe`
- **Linux**: `/usr/local/bin/thrift`
- **macOS**: `/opt/homebrew/bin/thrift`

## 使用示例

在其他模块中依赖本模块：

```xml
<dependency>
    <groupId>pers.zkx</groupId>
    <artifactId>algo-api</artifactId>
</dependency>
```

生成的 Java 类可直接使用：

```java
import com.example.thrift.api.User;
import com.example.thrift.api.Address;
import com.example.thrift.api.UserService;

// 创建用户对象
User user = new User();
user.setId("123");
user.setName("张三");
```

## 注意事项

1. **版本兼容性**：确保 libthrift 版本与 thrift 编译器版本一致（当前为 0.22.0）
2. **CI/CD 环境**：需要在 CI runner 上安装 thrift 可执行文件，或将生成的代码提交到仓库
3. **代码生成**：Maven 会在 `generate-sources` 阶段自动生成代码

## 优化建议

1. **当前配置良好**：跨平台 profile 配置完善
2. **建议改进**：
   - 考虑将生成的代码提交到仓库，避免 CI 环境依赖 thrift 安装
   - 可以添加更多业务相关的 IDL 定义
   - 建议在 IDL 文件中添加详细的注释说明
