package pers.zkx.algo.base.structures.queues;

import java.util.concurrent.TimeUnit;

/**
 * 令牌桶算法（Token Bucket）是一种流量控制算法，
 * 用于限制数据传输速率。它允许突发流量，但在平均速率上进行控制。
 *
 * @author: zhangkuixing
 * @date: 2025/7/5 23:43
 */
public class TokenBucket {
    // 最大令牌数
    private final int maxTokens;
    // 每秒补充的令牌数
    private final int refillRate;
    // 当前令牌数
    private int tokens;
    // 上次补充令牌的时间戳（纳秒）
    private long lastRefill;

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefill = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        refillTokens();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long now = System.nanoTime();
        long timeDiff = now - lastRefill;
        long tokensToAdd = (timeDiff * refillRate / TimeUnit.SECONDS.toNanos(1));
        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + (int) tokensToAdd);
            // 只有实际添加令牌时才更新时间戳
            lastRefill = now;
        }
    }
}
