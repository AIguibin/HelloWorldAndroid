package com.aiguibin.android;



import android.app.Application;
public class Applications extends Application{
    private static Applications instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化全局组件或设置
        initializeGlobalComponents();
    }

    private void initializeGlobalComponents() {
        // 在这里进行全局初始化工作，例如：
        // - 设置全局异常处理器
        // - 初始化第三方库
        // - 配置日志记录器
    }

    public static Applications getInstance() {
        return instance;
    }
}
