package com.aiguibin.android.bridge;

import android.content.Context;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import java.util.HashMap;
import java.util.Map;

public class BridgeHandlerRegistry {

    private final Map<String, BridgeHandler> bridgeHandlers = new HashMap<>();
    private final Context context;

    public BridgeHandlerRegistry(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 添加新的处理器
     *
     * @param handlerName 处理器名称
     */
    public void setHandler(String handlerName) {
        if (!handlerName.isEmpty()) {
            BridgeHandler handler = BridgeHandlersFactory.createHandler(handlerName, context);
            bridgeHandlers.put(handlerName, handler);
        }
    }

    /**
     * 获取已注册的处理器
     *
     * @param handlerName 处理器名称
     * @return 返回对应的处理器或null如果未找到
     */
    public BridgeHandler getHandler(String handlerName) {
        return bridgeHandlers.get(handlerName);
    }

    /**
     * 移除一个处理器
     *
     * @param handlerName 处理器名称
     */
    public void removeHandler(String handlerName) {
        bridgeHandlers.remove(handlerName);
    }

    /**
     * 清空所有处理器
     */
    public void clearHandlers() {
        bridgeHandlers.clear();
    }


    /**
     * 将所有处理器注册到BridgeWebView
     *
     * @param webView The BridgeWebView instance to register handlers to.
     */
    public void registerHandler(BridgeWebView webView, String handlerName) {
        webView.registerHandler(handlerName, BridgeHandlersFactory.createHandler(handlerName, context));
    }

    /**
     * 将所有处理器注册到BridgeWebView
     *
     * @param webView The BridgeWebView instance to register handlers to.
     */
    public void registerAllHandlers(BridgeWebView webView) {
        for (Map.Entry<String, BridgeHandler> entry : BridgeHandlersFactory.createAllHandlers(context).entrySet()) {
            String key = entry.getKey();
            BridgeHandler value = entry.getValue();
            bridgeHandlers.put(key, value);
            webView.registerHandler(key, value);
        }
    }
}
