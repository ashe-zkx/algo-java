package pers.zkx.algo.rpc.common;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC请求对象
 */
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 服务接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数值
     */
    private Object[] parameters;
}
