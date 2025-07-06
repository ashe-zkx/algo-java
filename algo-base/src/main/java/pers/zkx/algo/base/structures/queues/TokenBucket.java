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

    /**
     * 尝试允许一个请求。如果有令牌可用，则减少令牌数并允许请求。
     * 否则，拒绝请求。
     *
     * @return 如果请求被允许则返回 true，否则返回 false。
     */
    public synchronized boolean allowRequest() {
        refillTokens();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    /**
     * 补充令牌，根据自上次补充以来经过的时间和补充速率计算要添加的令牌数。
     * 确保总令牌数不超过最大令牌数。
     *
     *
     */
    private void refillTokens() {
        long now = System.nanoTime();
        long timeDiff = now - lastRefill;
        long tokensToAdd = (timeDiff * refillRate / TimeUnit.SECONDS.toNanos(1));
        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + (int) tokensToAdd);
            // 只有实际添加令牌时才更新时间戳
            long nanosPerToken = TimeUnit.SECONDS.toNanos(1) / refillRate;
            lastRefill += tokensToAdd * nanosPerToken;
        }
    }
}
