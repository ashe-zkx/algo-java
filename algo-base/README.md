# algo-base

## 模块说明

本模块包含常用的**基础算法和数据结构**实现，提供高质量、可复用的算法组件。

## 模块位置

```
algo-base/
└── src/main/java/pers/zkx/algo/base/
    ├── sorts/          # 排序算法
    ├── string/         # 字符串算法
    ├── structures/     # 数据结构
    └── utils/          # 工具类
```

## 功能模块

### 1. 排序算法 (sorts)
- `SortAlgoApi`: 排序算法接口定义
- `QuickSort`: 快速排序实现
- `SortAlgoUtils`: 排序工具类

### 2. 字符串算法 (string)
- `KMP`: KMP 字符串匹配算法

### 3. 数据结构 (structures)

#### 3.1 缓存 (caches)
- `LRUCache`: LRU (最近最少使用) 缓存实现
- `LRUCacheWithTTL`: 带过期时间的 LRU 缓存
- `OptimizedLRUCacheWithTTL`: 优化版的 TTL LRU 缓存

#### 3.2 缓冲区 (buffers)
- `CircularBuffer`: 环形缓冲区实现
- `OptimizedCircularBuffer`: 优化版环形缓冲区

#### 3.3 队列 (queues)
- `TokenBucket`: 令牌桶限流算法

#### 3.4 其他
- `BloomFilter`: 布隆过滤器实现
- `Node`: 通用节点定义

### 4. 工具类 (utils)
- `ArrayRandomUtils`: 数组随机工具
- `ListRandomUtils`: 列表随机工具

## 依赖关系

本模块没有外部依赖，是纯净的算法实现模块。

## 使用示例

### LRU 缓存
```java
LRUCache<String, Integer> cache = new LRUCache<>(100);
cache.put("key", 123);
Integer value = cache.get("key");
```

### 令牌桶限流
```java
TokenBucket bucket = new TokenBucket(100, 10); // 容量100，每秒10个令牌
boolean allowed = bucket.tryAcquire();
```

## 优化建议

1. **模块职责清晰**：当前模块专注于基础算法，职责明确
2. **建议改进**：
   - 考虑添加单元测试，提高代码质量
   - 可以添加性能基准测试（Benchmark）
   - 建议为复杂算法添加详细的注释和文档
