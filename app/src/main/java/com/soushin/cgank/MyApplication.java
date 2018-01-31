package com.soushin.cgank;

import android.app.Application;

import com.blankj.ALog;
import com.soushin.cgank.utills.SharedUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collection;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by Administrator on 2017/11/27.
 *
 */

public class MyApplication extends Application {
    private static MyApplication INSTANCE;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initALog();
        initSwipeBack();
        SharedUtils.INSTANCE.initConfig(this);
        LitePal.initialize(this);
    }

    private void initALog() {
        ALog.Config config = ALog.init(this)
                .setLogSwitch(Configure.DEBUG_LOG)// 设置log总开关，包括输出到控制台和文件，默认开BuildConfig.DEBUG
                .setConsoleSwitch(Configure.DEBUG_LOG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(ALog.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(ALog.V);// log文件过滤器，和logcat过滤器同理，默认Verbose
    }

    private void initSwipeBack() {
        //TODO 如果发现某些手机底部出现空白区域 打印一下:
        //TODO android.Build.VERSION.SDK_INT   android.Build.MODEL
        //TODO BGASwipeBackManager.ignoreNavigationBarModels(Arrays.asList("底部出现空白区域的手机对应的 android.Build.MODEL"))
        BGASwipeBackManager.getInstance().init(this);
        Collection<String> models=new ArrayList<>();
//        models.add("DUK-AL20");
        BGASwipeBackManager.ignoreNavigationBarModels(models);
    }

}
