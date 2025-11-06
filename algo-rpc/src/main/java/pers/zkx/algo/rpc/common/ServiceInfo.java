package pers.zkx.algo.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 服务信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public String getAddress() {
        return host + ":" + port;
    }
}
