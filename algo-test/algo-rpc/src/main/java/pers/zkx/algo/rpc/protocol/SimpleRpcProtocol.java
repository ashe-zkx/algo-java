package pers.zkx.algo.rpc.protocol;

import pers.zkx.algo.rpc.serialization.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 简单的RPC协议实现
 * 格式: [4字节长度][消息体]
 */
public class SimpleRpcProtocol implements RpcProtocol {

    @Override
    public void encode(Object message, OutputStream out, Serializer serializer) throws IOException {
        byte[] data = serializer.serialize(message);
        // 写入消息长度
        out.write((data.length >>> 24) & 0xFF);
        out.write((data.length >>> 16) & 0xFF);
        out.write((data.length >>> 8) & 0xFF);
        out.write(data.length & 0xFF);
        // 写入消息体
        out.write(data);
        out.flush();
    }

    @Override
    public <T> T decode(InputStream in, Class<T> clazz, Serializer serializer) throws IOException {
        // 读取消息长度
        int length = (in.read() << 24) | (in.read() << 16) | (in.read() << 8) | in.read();
        // 读取消息体
        byte[] data = new byte[length];
        int totalRead = 0;
        while (totalRead < length) {
            int read = in.read(data, totalRead, length - totalRead);
            if (read == -1) {
                throw new IOException("流已关闭");
            }
            totalRead += read;
        }
        return serializer.deserialize(data, clazz);
    }
}
