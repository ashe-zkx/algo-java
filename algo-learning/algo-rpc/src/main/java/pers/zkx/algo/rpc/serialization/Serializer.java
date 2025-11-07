package pers.zkx.algo.rpc.serialization;

/**
 * 序列化接口
 */
public interface Serializer {
    /**
     * 序列化
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
