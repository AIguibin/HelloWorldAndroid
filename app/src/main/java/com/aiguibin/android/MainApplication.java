package com.aiguibin.android;

import android.app.Application;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.databinding.library.baseAdapters.BuildConfig;

import com.didichuxing.doraemonkit.DoKit;

public class MainApplication extends Application {
    private static MainApplication instance;
    private boolean dokitEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化全局组件或设置
        initializeGlobalComponents();

        // 初始化并配置 DoKit（仅限调试模式）
        initAndConfigureDokit();
    }

    private void initializeGlobalComponents() {
        // 在这里进行全局初始化工作，例如：
        // - 设置全局异常处理器
        // - 初始化第三方库
        // - 配置日志记录器
    }

    private void initAndConfigureDokit() {
        // "package:mine"
        Log.w("MainApplication", "是否DEBUG模式: "+BuildConfig.DEBUG);
        if (BuildConfig.DEBUG) {
            // 初始化 DoKit
            dokitEnabled = true;
            new DoKit.Builder(this).debug(true).build();

            // 检查并请求悬浮窗权限（对于 API level 23 及以上）
            checkAndRequestOverlayPermission();
        }
    }

    /**
     * 检查并请求悬浮窗权限
     */
    private void checkAndRequestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Log.w("MainApplication", "请确保您的应用拥有悬浮窗权限");
                // 提示用户需要手动处理权限
                // 注意：由于 Application 类无法直接管理 Activity 生命周期，
                // 这里只能提醒开发者手动处理权限。
            } else {
                // 如果已经授予了悬浮窗权限，可以立即显示 DoKit 悬浮窗
                DoKit.show();
            }
        } else {
            // 对于低于 API level 23 的设备，直接显示悬浮窗
            DoKit.show();
        }
    }

    /**
     * 获取 DoKit 是否已启用
     */
    public boolean isDokitEnabled() {
        return dokitEnabled;
    }

    /**
     * 设置 DoKit 调试工具的可见性
     */
    public void setDokitVisibility(boolean visible) {
        if (BuildConfig.DEBUG && dokitEnabled) {
            if(visible) {
                DoKit.show();
            } else {
                DoKit.hide();
            }
        }
    }

    /**
     * 启用或禁用特定的 DoKit 工具
     */
    public void enableDokitTool(String toolName, boolean enabled) {
        if (BuildConfig.DEBUG && dokitEnabled) {
            // 注意：DoKit 并没有提供直接通过名称启用/禁用工具的方法。
            // 您可能需要遍历已注册的工具列表并手动启用或禁用指定工具。
        }
    }

    /**
     * 切换 DoKit 的开关状态
     */
    public void toggleDokitSwitch(boolean enable) {
        if (BuildConfig.DEBUG) {
            dokitEnabled = enable;
            if (dokitEnabled) {
                // 重新初始化 DoKit
                new DoKit.Builder(this).debug(true).build();
                DoKit.show();
            } else {
                // 关闭 DoKit 并移除所有 Dokit 视图
                DoKit.hide();
            }
        }
    }

    public static MainApplication getInstance() {
        return instance;
    }
}