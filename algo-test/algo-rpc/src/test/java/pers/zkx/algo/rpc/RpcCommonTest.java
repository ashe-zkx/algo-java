package pers.zkx.algo.rpc;

import org.junit.jupiter.api.Test;
import pers.zkx.algo.rpc.common.RpcRequest;
import pers.zkx.algo.rpc.common.RpcResponse;

import static org.assertj.core.api.Assertions.assertThat;

class RpcCommonTest {

    @Test
    void testRpcRequest() {
        RpcRequest request = new RpcRequest();
        request.setRequestId("123");
        request.setInterfaceName("com.example.Service");
        request.setMethodName("testMethod");
        request.setParameterTypes(new Class<?>[]{String.class});
        request.setParameters(new Object[]{"test"});

        assertThat(request.getRequestId()).isEqualTo("123");
        assertThat(request.getInterfaceName()).isEqualTo("com.example.Service");
        assertThat(request.getMethodName()).isEqualTo("testMethod");
        assertThat(request.getParameterTypes()).hasSize(1);
        assertThat(request.getParameters()).hasSize(1);
    }

    @Test
    void testRpcResponse() {
        RpcResponse response = new RpcResponse();
        response.setRequestId("123");
        response.setResult("success");

        assertThat(response.getRequestId()).isEqualTo("123");
        assertThat(response.getResult()).isEqualTo("success");
        assertThat(response.hasException()).isFalse();

        response.setException(new RuntimeException("test error"));
        assertThat(response.hasException()).isTrue();
    }
}
