package com.aiguibin.android.bridge;

import android.content.Context;

import com.aiguibin.android.native_modules.LaunchTansunBridgeHandler;
import com.aiguibin.android.native_modules.LoginBridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BridgeHandlersFactory {
    public static BridgeHandler createHandler(String handlerName, Context context) {
        switch (handlerName.toLowerCase()) {
            case "login":
                return new LoginBridgeHandler();
            // 这里可以添加更多处理器类型
            default:
                throw new IllegalArgumentException("Unknown handler name: " + handlerName);
        }
    }
    public static Map<String,BridgeHandler>  createAllHandlers(Context context) {
        Map<String, BridgeHandler> bridgeHandlers = new HashMap<>();
        bridgeHandlers.put("login",new LoginBridgeHandler());
        bridgeHandlers.put("launchTansun",new LaunchTansunBridgeHandler(context));
       return bridgeHandlers;
    }
}
