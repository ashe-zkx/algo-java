package pers.zkx.algo.rpc.protocol;

import pers.zkx.algo.rpc.serialization.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * RPC协议接口
 */
public interface RpcProtocol {
    /**
     * 编码请求
     */
    void encode(Object message, OutputStream out, Serializer serializer) throws IOException;

    /**
     * 解码请求
     */
    <T> T decode(InputStream in, Class<T> clazz, Serializer serializer) throws IOException;
}
