package com.aiguibin.android.bridge;


import com.aiguibin.android.native_modules.LoginBridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import java.util.HashMap;
import java.util.Map;

public class BridgeHandlerDispatch {
    private static final Map<MessageType, BridgeHandler> bridgeHandlers = new HashMap<>();

    static {
        // 初始化处理器映射表
        bridgeHandlers.put(MessageType.LOGIN, new LoginBridgeHandler() );

    }

    public static BridgeHandler getHandler(MessageType type) {
        return bridgeHandlers.getOrDefault(type, null);
    }
}
