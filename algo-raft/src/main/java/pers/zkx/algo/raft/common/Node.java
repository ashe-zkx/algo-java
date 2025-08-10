package pers.zkx.algo.raft.common;

import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangkuixing
 * @date: 2025/7/31 23:51
 */
@Data
public class Node {
    private String id;
    private RaftRole role;
    private List<Node> cluster;


    private int currentTerm;
    private String votedFor;
    private int votesReceived;

    private List<RaftLogEntry> log;


    // 用于生成随机选举超时时间
    private final Random random;
    // 定时任务调度器
    private final ScheduledExecutorService scheduler;


    // 最近一次选举的时间
    private long lastHeartbeat;


    public Node(String id, List<Node> cluster) {
        this.id = id;
        this.role = RaftRole.FOLLOWER; // 默认角色为追随者
        this.cluster = cluster;
        // 初始化最近一次心跳时间为当前时间
        this.lastHeartbeat = System.currentTimeMillis();
        // 初始化随机数生成器
        this.random = new Random();
        // 初始化定时任务调度器
        this.scheduler = java.util.concurrent.Executors.newScheduledThreadPool(1);

        // 启动选举定时器
        startElectionTimer();
    }

    // 启动选举定时器
    private void startElectionTimer() {
        int timeout = 150 + random.nextInt(150); // 150-300ms
        // 安排一个新的任务在指定的超时时间后执行
        scheduler.schedule(this::checkElectionTimeout, timeout, TimeUnit.MILLISECONDS);
    }

    // 检查选举超时
    private void checkElectionTimeout() {
        long now = System.currentTimeMillis();
        // 如果当前角色是领导者，直接返回
        if (role != RaftRole.LEADER) {
            return;
        }
        // 如果当前时间距离上次心跳超过150毫秒加上一个随机值，则开始选举
        if (now - lastHeartbeat > 150 + random.nextInt(150)) {
            startElection();
        } else {
            startElectionTimer();
        }
    }

    // 开始选举
    private void startElection() {
        // 如果当前角色不是候选人，则转换为候选人角色
        role = RaftRole.CANDIDATE;
        // 重置当前任期号
        currentTerm++;
        // 重置投票信息
        votedFor = id;
        // 重置收到的投票数
        votesReceived = 1;
        // 更新最近一次心跳时间
        lastHeartbeat = System.currentTimeMillis();

        // 设置请求投票的参数
        RequestVoteArgs args = new RequestVoteArgs();
        // 当前任期号
        args.term = currentTerm;
        // 候选人ID
        args.candidateId = id;
        // 最后日志索引
        args.lastLogIndex = log.size() - 1;
        // 如果日志为空，则最后日志任期号为0，否则为最后一条日志的任期号
        args.lastLogTerm = log.isEmpty() ? 0 : log.get(log.size() - 1).getTerm();


        // 向集群中的其他节点发送请求投票 RPC
        for (Node node : cluster) {
            if (!node.id.equals(id)) { // 不向自己发送请求
                RequestVoteReply reply = node.requestVote(args);
                handleVoteReply(reply);
            }
        }


    }

    /**
     * 处理投票回复
     * 逻辑：
     * 1. 如果回复的任期号大于当前任期号，更新
     * 2. 如果回复的任期号等于当前任期号且同意投票，增加收到的投票数
     * 3. 如果收到的投票数超过半数，转换为领导者角色
     *
     * @param args
     * @return
     */
    public RequestVoteReply requestVote(RequestVoteArgs args) {
        RequestVoteReply reply = new RequestVoteReply();
        reply.term = currentTerm;
        reply.voteGranted = false;

        // 如果请求的任期号小于当前任期号，拒绝投票
        // Raft 协议要求节点只能投票给当前任期号的候选人
        if (args.term < currentTerm) {
            return reply;
        }
        // 如果请求的任期号大于当前任期号，更新当前任期号和投票信息
        if (args.term > currentTerm) {
            currentTerm = args.term;
            votedFor = null; // 重置投票信息
            role = RaftRole.FOLLOWER; // 转换为追随者角色
        }

        // 如果当前节点没有投票或者已经投给了该候选人
        if (votedFor == null || votedFor.equals(args.candidateId)) {

            // 检查候选人的日志是否至少与当前节点的日志一样新
            int lastLogTerm = log.isEmpty() ? 0 : log.get(log.size() - 1).getTerm();
            if (args.lastLogTerm >= lastLogTerm && args.lastLogIndex >= log.size() - 1) {
                votedFor = args.candidateId; // 投票给候选人
                reply.voteGranted = true; // 同意投票
                lastHeartbeat = System.currentTimeMillis(); // 更新最近一次心跳时间
            }
        }
        return reply;
    }

    // 处理投票响应
    private void handleVoteReply(RequestVoteReply reply) {
        if (reply.term > currentTerm) {
            // 如果回复的任期号大于当前任期号，更新当前任期号和角色
            currentTerm = reply.term;
            role = RaftRole.FOLLOWER; // 转换为追随者角色
            votedFor = null; // 重置投票信息
            votesReceived = 0; // 重置收到的投票数
            return;
        }

        if (reply.voteGranted && role == RaftRole.CANDIDATE) {
            // 如果同意投票且当前角色是候选人，增加收到的投票数
            votesReceived++;
            // 检查是否超过半数
            if (votesReceived > cluster.size() / 2) {
                becomeLeader()
            }

        }
    }

    // 成为领导者
    private void becomeLeader() {
        role = RaftRole.LEADER;
        votesReceived = 0;
        for (Node node : cluster) {
            nextIndex.put(node.id, log.size());
            matchIndex.put(node.id, 0);
        }
        scheduler.scheduleAtFixedRate(this::sendHeartbeats, 0, 50, TimeUnit.MILLISECONDS);
    }

    // 请求投票 RPC
    static class RequestVoteArgs {
        int term;
        String candidateId;
        int lastLogIndex;
        int lastLogTerm;
    }

    static class RequestVoteReply {
        // 响应的任期号
        int term;
        // 是否同意投票
        boolean voteGranted;
    }
}
