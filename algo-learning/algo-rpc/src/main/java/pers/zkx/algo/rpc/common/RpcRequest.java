package pers.zkx.algo.rpc.common;


import java.io.Serializable;

/**
 * RPC请求对象
 */
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcRequest that = (RpcRequest) o;
        return java.util.Objects.equals(requestId, that.requestId) &&
               java.util.Objects.equals(interfaceName, that.interfaceName) &&
               java.util.Objects.equals(methodName, that.methodName) &&
               java.util.Arrays.equals(parameterTypes, that.parameterTypes) &&
               java.util.Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = java.util.Objects.hash(requestId, interfaceName, methodName);
        result = 31 * result + java.util.Arrays.hashCode(parameterTypes);
        result = 31 * result + java.util.Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
               "requestId='" + requestId + '\'' +
               ", interfaceName='" + interfaceName + '\'' +
               ", methodName='" + methodName + '\'' +
               ", parameterTypes=" + java.util.Arrays.toString(parameterTypes) +
               ", parameters=" + java.util.Arrays.toString(parameters) +
               '}';
    }
}
