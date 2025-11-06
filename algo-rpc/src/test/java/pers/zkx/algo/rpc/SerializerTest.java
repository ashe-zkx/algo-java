package pers.zkx.algo.rpc;

import org.junit.jupiter.api.Test;
import pers.zkx.algo.rpc.serialization.JavaSerializer;
import pers.zkx.algo.rpc.serialization.Serializer;

import static org.assertj.core.api.Assertions.assertThat;

class SerializerTest {

    @Test
    void testJavaSerializer() {
        Serializer serializer = new JavaSerializer();
        
        String original = "Hello, RPC!";
        byte[] bytes = serializer.serialize(original);
        String deserialized = serializer.deserialize(bytes, String.class);
        
        assertThat(deserialized).isEqualTo(original);
    }

    @Test
    void testSerializeInteger() {
        Serializer serializer = new JavaSerializer();
        
        Integer original = 42;
        byte[] bytes = serializer.serialize(original);
        Integer deserialized = serializer.deserialize(bytes, Integer.class);
        
        assertThat(deserialized).isEqualTo(original);
    }
}
