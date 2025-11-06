```markdown
# thrift-api 模块

该模块包含 Thrift IDL（api.thrift）以及通过 Maven 生成的 Java API。

目录结构（示例）：
```

thrift-api/
├── pom.xml
└── src/
└── main/
└── thrift/
└── api.thrift

```

如何生成 Java API（两种常用方式）：

方法 A：使用 Maven 插件（推荐）
1. 在项目根目录运行（会执行 generate-sources 阶段并编译、打包）：
```bash
# 生成并安装到本地 Maven 仓库
mvn -pl thrift-api -am install
```

解释：

- -pl thrift-api：只构建 thrift-api 模块
- -am：同时构建依赖的模块（如果有）
- install：完成构建并将 artifact 安装到本地仓库（~/.m2/repository）

生成的 Java 源码位置（默认）：

- target/generated-sources/thrift/ 或 target/generated-sources (取决于插件版本)
  编译时 Maven 会把生成目录加入到编译路径。

方法 B：使用 thrift CLI 手动生成（当系统安装了 thrift 可执行文件时）

```bash
# 在模块目录下执行（将生成代码放到 src/main/java）
thrift --gen java -out src/main/java src/main/thrift/api.thrift
mvn -pl thrift-api -am install
```

使用说明（示例）：

- 生成后，Java 包名为 `com.example.thrift.api`，可以在其他模块中通过依赖 thrift-api artifact 使用生成的类（User, Address, UserService 等）。
- 如果你想把生成的 API 安装到本地仓库用于其他模块依赖：`mvn install` 即可（如上示例）。

注意事项：

- 确保使用的 libthrift 版本与 thrift 编译器版本兼容（建议一致）。
- 在 CI 中，如果使用 maven-thrift-plugin，请保证 runner 环境可以找到 thrift 可执行文件，或在 CI 中通过 apt/brew 安装 thrift，或先将生成的代码 commit 到仓库（如果不能在 CI 上安装 thrift）。
- plugin 的具体行为可能基于版本有所差异，若生成目录不同，请检查 target/generated-sources 目录并适配。

示例：在另一个模块中依赖此模块（pom.xml 片段）

```xml

<dependency>
    <groupId>com.example</groupId>
    <artifactId>thrift-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

```
