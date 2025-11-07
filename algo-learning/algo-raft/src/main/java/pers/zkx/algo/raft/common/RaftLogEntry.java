package pers.zkx.algo.raft.common;


/**
 * @author: zhangkuixing
 * @date: 2025/7/31 23:53
 */
public class RaftLogEntry {
    // 日志条目的任期号
    private int term;
    // 日志条目的命令
    private String command;

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaftLogEntry that = (RaftLogEntry) o;
        return term == that.term &&
               java.util.Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(term, command);
    }

    @Override
    public String toString() {
        return "RaftLogEntry{" +
               "term=" + term +
               ", command='" + command + '\'' +
               '}';
    }
}
