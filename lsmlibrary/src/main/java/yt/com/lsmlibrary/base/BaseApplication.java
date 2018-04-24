package yt.com.lsmlibrary.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.crashreport.CrashReport;

import yt.com.lsmlibrary.common.SkinConfig;
import yt.com.lsmlibrary.loader.SkinManager;

/**
 * author  : LSM
 * time    : 2017/12/08
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */

public class BaseApplication extends Application {
    public static final String TAG=BaseApplication.class.getName();
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    private static BaseApplication mApp;

    public static synchronized BaseApplication getInstance() {
        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        //工具类初始化
        Utils.init(this);
        LogUtils.getConfig().setLogSwitch(true);
        //bugly初始化
        initBugly();
        //hotfix热修复初始化
        initHotfix();
        //换肤
        changeSkin();
        //内存泄露
        LeakCanary.install(this);
    }
    public void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(),"2eac631676",true);
        //自定义标示，方便后期的Bug修复跟进
        CrashReport.setUserId(this, PhoneUtils.getIMEI());
    }
    public void initHotfix() {
        SophixManager.getInstance().setContext(this)
                .setAppVersion(AppUtils.getAppVersionName())
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            LogUtils.iTag(TAG,"补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            LogUtils.iTag(TAG,"补丁生效需要重启");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            LogUtils.iTag(TAG,"加载补丁结果码:"+code);
                        }
                    }
                }).initialize();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
    public void changeSkin() {
        SkinManager.getInstance().init(this);
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(true);
        SkinConfig.enableGlobalSkinApply();
    }


    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }
}
