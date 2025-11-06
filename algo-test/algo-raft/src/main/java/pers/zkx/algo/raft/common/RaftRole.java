package pers.zkx.algo.raft.common;

/**
 * @author: zhangkuixing
 * @date: 2025/7/31 23:50
 */
public enum RaftRole {
    FOLLOWER,  // 追随者
    CANDIDATE, // 候选人
    LEADER;    // 领导者

    public boolean isLeader() {
        return this == LEADER;
    }

    public boolean isCandidate() {
        return this == CANDIDATE;
    }

    public boolean isFollower() {
        return this == FOLLOWER;
    }
}
