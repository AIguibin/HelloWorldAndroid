package com.aiguibin.android.native_modules;

import static androidx.compose.ui.text.input.RecordingInputConnection_androidKt.TAG;

import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginBridgeHandler implements BridgeHandler {
    @Override
    public  void handler(String data, CallBackFunction function){
        try {
            // 假设传入的数据是一个 JSON 字符串
            JSONObject jsonData = new JSONObject(data);

            // 提取用户名和密码（假设它们存在于 JSON 中）
            String username = jsonData.optString("username", "");
            String password = jsonData.optString("password", "");

            // 注意：不要在日志中记录敏感信息！
            Log.d(TAG, "Received login request for user: " + username+password);

            // 这里应该进行实际的登录逻辑，例如调用服务器 API 验证用户凭证
            // 为了简化示例，我们只是模拟成功响应
            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("message", "登录成功");

            // 返回结构化的 JSON 响应给 H5 页面
            function.onCallBack(response.toString());

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON data", e);
            function.onCallBack("{\"success\": false, \"message\": \"数据解析失败\"}");
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error occurred", e);
            function.onCallBack("{\"success\": false, \"message\": \"发生未知错误\"}");
        }
    };
}
