package com.aiguibin.android.bridge;

import com.aiguibin.android.native_modules.LoginBridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import java.util.HashMap;
import java.util.Map;

public class BridgeHandlerRegistry {

    private  final Map<String, BridgeHandler> bridgeHandlers = new HashMap<>();

    public BridgeHandlerRegistry() {
        // 初始化并注册预定义的处理器
        initializeHandlers();
    }

    /**
     * 注册一个新的处理器
     *
     * @param handlerName 处理器名称
     * @param handler     实现了BridgeHandler接口的处理器对象
     */
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null && !handlerName.isEmpty()) {
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
     * 初始化并注册三个预定义的处理器
     */
    private void initializeHandlers() {
        // 预定义处理器
        registerHandler("login", new LoginBridgeHandler());

    }

    /**
     * 将所有处理器注册到BridgeWebView
     *
     * @param webView The BridgeWebView instance to register handlers to.
     */
    public void registerAllHandlers(BridgeWebView webView) {
        bridgeHandlers.forEach(webView::registerHandler);
    }
}
