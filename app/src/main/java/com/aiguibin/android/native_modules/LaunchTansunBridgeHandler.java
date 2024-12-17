package com.aiguibin.android.native_modules;

import static androidx.compose.ui.text.input.RecordingInputConnection_androidKt.TAG;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class LaunchTansunBridgeHandler implements BridgeHandler {

    private final Context context;

    public LaunchTansunBridgeHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public void handler(String data, CallBackFunction function) {
        try {

            // 显式 Intent 主要用于同一应用内部的组件间通信，确保了更高的安全性和控制力,可以明确包名以及Activity或Service。
            // 隐式 Intent 则提供了更广泛的灵活性，适用于跨应用的操作，但需要谨慎处理以确保有足够的应用能够处理所发出的 Intent，并考虑到可能的安全隐患。

            // 检查是否找到了对应的意图（即目标应用是否存在）---隐式
            PackageManager packageManager = context.getPackageManager();
            Intent intent =packageManager.getLaunchIntentForPackage("com.huawei.appmarket");

            // 创建意图对象，并设置要启动的应用程序包名
            // Intent intent = new Intent();
            // intent.setPackage("com.tansun.tymh");

            // 创建 Intent 并设置组件名称-----显式
            // Intent intent = new Intent();
            // intent.setComponent(new ComponentName(packageName, className));
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (intent.resolveActivity(packageManager) != null) {
                // 确保意图是明确指向一个应用的
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // 构造 JSON 字符串
                String jsonString = "{\"key\":\"value\", \"anotherKey\":123}";
                // 将 JSON 字符串作为额外数据附加到 Intent 上
                intent.putExtra("com.huawei.JSON_DATA", jsonString);
                // 启动目标应用
                context.startActivity(intent);
            } else {
                // 如果没有找到对应的应用，可以提示用户下载或者显示错误信息
                Toast.makeText(context, "目标应用未安装", Toast.LENGTH_SHORT).show();
            }


            // 返回结构化的 JSON 响应给 H5 页面
            function.onCallBack("");

        } catch (Exception e) {
            Log.e(TAG, "Unexpected error occurred", e);
            function.onCallBack("{\"success\": false, \"message\": \"发生未知错误\"}");
        }
    }

    ;
}
