# algo-raft

## 模块说明

本模块包含 **Raft 分布式一致性算法**的基础数据结构定义，用于学习和实现 Raft 共识协议。

## 模块位置

```
algo-test/algo-raft/
└── src/main/java/pers/zkx/algo/raft/
    └── common/
        ├── Node.java          # Raft 节点定义
        ├── RaftLogEntry.java  # 日志条目
        └── RaftRole.java      # 节点角色枚举
```

## 核心组件

### 1. RaftRole.java
定义 Raft 节点的三种角色：
- **Leader**（领导者）：处理客户端请求，管理日志复制
- **Follower**（跟随者）：被动接收 Leader 的日志和心跳
- **Candidate**（候选者）：发起选举，竞争成为 Leader

### 2. RaftLogEntry.java
Raft 日志条目，包含：
- **term**：任期号
- **command**：状态机命令
- **index**：日志索引

### 3. Node.java
Raft 节点核心实现，包括：
- 节点状态管理
- 选举逻辑
- 日志复制
- 心跳机制

## Raft 算法简介

Raft 是一种易于理解的分布式一致性算法，主要解决分布式系统中的数据一致性问题。

### 核心机制
1. **Leader 选举**：通过随机超时和投票机制选出唯一 Leader
2. **日志复制**：Leader 将日志复制到多数节点后才提交
3. **安全性保证**：确保已提交的日志不会丢失

### 应用场景
- 分布式配置管理
- 分布式锁
- 分布式数据库
- 服务注册中心（如 etcd、Consul）

## 依赖关系

本模块没有外部依赖，是 Raft 算法的基础实现。

## 实现状态

当前模块包含 Raft 的基础数据结构和核心逻辑，但尚未完整实现以下功能：
- 网络通信层
- 持久化存储
- 完整的集群管理

## 学习资源

- [Raft 官方网站](https://raft.github.io/)
- [Raft 论文](https://raft.github.io/raft.pdf)
- [Raft 可视化演示](http://thesecretlivesofdata.com/raft/)

## 优化建议

1. **模块完整性**：
   - 当前只有基础数据结构，缺少完整的算法实现
   - **建议**：补充网络通信、RPC 调用、持久化等模块

2. **测试覆盖**：
   - 建议添加单元测试，验证状态转换逻辑
   - 可以添加集成测试，模拟多节点场景

3. **文档完善**：
   - 建议在代码中添加详细注释，解释 Raft 关键步骤
   - 可以添加使用示例和测试用例

4. **模块位置**：
   - 如果计划完整实现 Raft，建议提升为一级模块 `algo-raft`
   - 如果仅作为学习示例，保持在 `algo-test` 下合理
