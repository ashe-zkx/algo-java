package pers.zkx.algo.rpc;

import org.junit.jupiter.api.Test;
import pers.zkx.algo.rpc.common.RpcRequest;
import pers.zkx.algo.rpc.protocol.RpcProtocol;
import pers.zkx.algo.rpc.protocol.SimpleRpcProtocol;
import pers.zkx.algo.rpc.serialization.JavaSerializer;
import pers.zkx.algo.rpc.serialization.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RpcProtocolTest {

    @Test
    void testEncodeAndDecode() throws Exception {
        RpcProtocol protocol = new SimpleRpcProtocol();
        Serializer serializer = new JavaSerializer();

        RpcRequest request = new RpcRequest();
        request.setRequestId("123");
        request.setInterfaceName("TestService");
        request.setMethodName("test");
        request.setParameterTypes(new Class<?>[]{String.class});
        request.setParameters(new Object[]{"hello"});

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        protocol.encode(request, out, serializer);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        RpcRequest decoded = protocol.decode(in, RpcRequest.class, serializer);

        assertThat(decoded.getRequestId()).isEqualTo("123");
        assertThat(decoded.getInterfaceName()).isEqualTo("TestService");
        assertThat(decoded.getMethodName()).isEqualTo("test");
    }
}
