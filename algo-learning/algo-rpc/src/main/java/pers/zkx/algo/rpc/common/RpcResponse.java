package pers.zkx.algo.rpc.common;


import java.io.Serializable;

/**
 * RPC响应对象
 */
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 响应结果
     */
    private Object result;

    /**
     * 异常信息
     */
    private Exception exception;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * 判断是否成功
     */
    public boolean hasException() {
        return exception != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcResponse that = (RpcResponse) o;
        return java.util.Objects.equals(requestId, that.requestId) &&
               java.util.Objects.equals(result, that.result) &&
               java.util.Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(requestId, result, exception);
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
               "requestId='" + requestId + '\'' +
               ", result=" + result +
               ", exception=" + exception +
               '}';
    }
}
