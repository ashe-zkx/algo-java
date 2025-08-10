package pers.zkx.algo.raft.common;

import lombok.Data;

/**
 * @author: zhangkuixing
 * @date: 2025/7/31 23:53
 */
@Data
public class RaftLogEntry {
    // 日志条目的任期号
    private int term;
    // 日志条目的命令
    private String command;
}
