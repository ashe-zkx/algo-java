# algo-benchmark

使用 JMH 测试多种字符串拼接方式（`String.format`、`+`、`StringBuilder`、流式 `+`）。基准类：`pers.zkx.algo.benchmark.string.StringConcatJmhBenchmark`，数据量默认 1,000,000。

> 提示：JMH 是面向微基准的正式工具，仍需按需调整参数，并结合 GC/Profiler 观察。

## 运行方式（JMH）

```bash
# 生成可执行基准包
mvn -pl algo-learning/algo-benchmark -am -DskipTests clean package

# 运行全部基准（示例一次预热、五次测量，Fork=1 已在代码中配置）
java -jar algo-learning/algo-benchmark/target/benchmarks.jar StringConcatJmhBenchmark

# 或在 IDE 里直接运行 org.openjdk.jmh.Main
```

## JMH 基准测试结果 (JDK 21)

> 运行环境：JDK 21.0.9, Windows, JMH 1.37

| Benchmark | Mode | Score (ops/s) | Error |
| :--- | :--- | :--- | :--- |
| `streamPlus` | thrpt | ~39,428,940 | ± 704,283 |
| `plus` | thrpt | ~38,360,349 | ± 1,935,630 |
| `builder` | thrpt | ~28,824,358 | ± 1,794,627 |
| `stringFormat` | thrpt | ~7,121,513 | ± 407,217 |

### 📊 性能排名 (从快到慢)

1.  **`streamPlus`** (~3943 万 ops/s) - **最快**
2.  **`plus`** (~3836 万 ops/s) - **非常快** (与 `streamPlus` 处于同一梯队)
3.  **`builder`** (~2882 万 ops/s) - **中等** (比 `plus` 慢约 25%)
4.  **`stringFormat`** (~712 万 ops/s) - **最慢** (比 `plus` 慢约 5.4 倍)

### 💡 详细解析

#### 1. `+` 运算符 (Plus) 是简单拼接的王者
*   **表现**：`streamPlus` 和 `plus` 均使用 `+` 运算符进行拼接，性能遥遥领先。
*   **原因**：在现代 JDK（尤其是 JDK 9+ 及 JDK 21）中，`String a + b` 的代码会被编译为 `invokedynamic` 指令，利用 `StringConcatFactory` 进行高度优化。JVM 可以在运行时动态生成最优的拼接代码（例如预先计算所需内存大小，避免多次内存复制），其效率通常优于手动编写的 `StringBuilder`。
*   **差异**：`streamPlus` 略快于 `plus` 可能是因为 JIT 编译时的内联策略或循环展开的微小差异，但在实际应用中两者性能基本等价。

#### 2. `StringBuilder` (Builder) 在单次拼接中略逊一筹
*   **表现**：`builder` 方式显式创建了 `StringBuilder` 对象。
*   **原因**：虽然 `StringBuilder` 在**循环中**拼接字符串是最佳实践，但在**单次**拼接（如 `a + b + c`）场景下，显式创建 `StringBuilder` 实例、调用多次 `append` 方法、最后调用 `toString` 的开销，反而比 JVM 自动优化的 `+` 运算符要大。

#### 3. `String.format` 性能代价高昂
*   **表现**：吞吐量最低，仅为 `plus` 方式的 18% 左右。
*   **原因**：`String.format` 内部逻辑非常复杂。它需要：
    1.  解析格式化字符串（正则表达式匹配占位符）。
    2.  创建参数数组（Varargs）。
    3.  进行类型检查和转换。
    这些运行时开销使其不适合在高性能敏感的代码路径（如高频日志、紧密循环）中使用。

### 📝 总结建议

*   **首选 `+`**：对于大多数简单的字符串拼接（如 `return "key:" + id;`），直接使用 `+` 号，代码最简洁且在 JDK 21 下性能最优。
*   **慎用 `String.format`**：仅在需要复杂格式化（如保留小数位、日期格式、对齐）且非性能热点代码中使用。
*   **循环中使用 `StringBuilder`**：如果是跨多行循环拼接字符串，依然需要使用 `StringBuilder` 以避免产生大量临时 String 对象（本测试未涵盖此场景，但在循环体内 `+` 会导致 O(n^2) 的性能问题）。

## 历史脚本输出（非 JMH，仅供参考）

```
开始基准：size=1000000
String.format - round 1: 209.926 ms, mem delta: 164.152 MB
String.format - round 2: 161.650 ms, mem delta: 156.165 MB
String.format - round 3: 157.969 ms, mem delta: 152.243 MB
String.format - round 4: 146.672 ms, mem delta: 132.186 MB
String.format - round 5: 142.340 ms, mem delta: 128.384 MB
String.format - avg: 163.712 ms

Plus (+) - round 1: 32.117 ms, mem delta: 78.000 MB
Plus (+) - round 2: 25.898 ms, mem delta: 78.000 MB
Plus (+) - round 3: 26.340 ms, mem delta: 78.000 MB
Plus (+) - round 4: 25.995 ms, mem delta: 78.000 MB
Plus (+) - round 5: 25.691 ms, mem delta: 78.000 MB
Plus (+) - avg: 27.208 ms

StringBuilder - round 1: 39.009 ms, mem delta: 130.000 MB
StringBuilder - round 2: 38.067 ms, mem delta: 130.000 MB
StringBuilder - round 3: 37.916 ms, mem delta: 132.000 MB
StringBuilder - round 4: 38.178 ms, mem delta: 130.000 MB
StringBuilder - round 5: 38.209 ms, mem delta: 130.000 MB
StringBuilder - avg: 38.276 ms

Stream + - round 1: 37.575 ms, mem delta: 80.000 MB
Stream + - round 2: 24.717 ms, mem delta: 78.000 MB
Stream + - round 3: 24.965 ms, mem delta: 78.000 MB
Stream + - round 4: 25.207 ms, mem delta: 78.000 MB
Stream + - round 5: 25.652 ms, mem delta: 78.000 MB
Stream + - avg: 27.623 ms
```
