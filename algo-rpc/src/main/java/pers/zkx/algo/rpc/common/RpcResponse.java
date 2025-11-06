package pers.zkx.algo.rpc.common;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC响应对象
 */
@Data
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

    /**
     * 判断是否成功
     */
    public boolean hasException() {
        return exception != null;
    }
}
