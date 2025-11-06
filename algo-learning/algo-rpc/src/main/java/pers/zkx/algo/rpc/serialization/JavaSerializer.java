package pers.zkx.algo.rpc.serialization;

import java.io.*;

/**
 * Java原生序列化实现
 * 
 * 警告：此实现使用Java原生序列化，存在已知的安全风险。
 * 在生产环境中，建议使用更安全的序列化方案，如：
 * - JSON (Jackson, Gson)
 * - Protobuf
 * - Hessian
 * 
 * 仅适用于受信任的网络环境和学习目的。
 */
public class JavaSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("序列化失败", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }
}
