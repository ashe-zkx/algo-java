package pers.zkx.algo.rpc.common;


import java.io.Serializable;

/**
 * 服务信息
 */
public class ServiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务地址
     */
    private String host;

    /**
     * 服务端口
     */
    private int port;

    public ServiceInfo() {
    }

    public ServiceInfo(String serviceName, String host, int port) {
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return host + ":" + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceInfo that = (ServiceInfo) o;
        return port == that.port &&
               java.util.Objects.equals(serviceName, that.serviceName) &&
               java.util.Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(serviceName, host, port);
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
               "serviceName='" + serviceName + '\'' +
               ", host='" + host + '\'' +
               ", port=" + port +
               '}';
    }
}
