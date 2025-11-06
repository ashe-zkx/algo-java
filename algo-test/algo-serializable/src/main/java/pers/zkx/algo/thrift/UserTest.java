package pers.zkx.algo.thrift;


import com.example.thrift.api.User;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import java.util.HashMap;

/**
 * @author: zhangkuixing
 * @date: 2025/10/20 23:39
 */
public class UserTest {
    public static void main(String[] args) {
        User user = new User();
        user.setMetadata(new HashMap<>());

        // 指定序列化为 Tbinary 格式
    }
}
