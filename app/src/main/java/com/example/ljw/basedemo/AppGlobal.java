
package com.example.ljw.basedemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.ljw.basedemo.utils.Logger;
import com.example.ljw.basedemo.utils.Utils;

import java.io.File;


public class AppGlobal extends MultiDexApplication {
    private static AppGlobal application;

    private ActManager actManager;

    public static final String androidFileName_ = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/.e5";

    /**
     * 屏幕参数
     */
    private DisplayMetrics displayMetrics = null;
    private int titleBarHeight = 0;
    private int tabBarHeight = 0;

    private String appPath = null;

    private String localAppVersion = null;// 本地App版本

    /**
     * 渠道 或者 appstore 名字
     */
    private String channelName;

    private final String BAICHUAN_APP_KEY = "23362595";


    private boolean isApkDebug = false;
    private boolean isDebug = false;

    private String unionId = null;


    public static AppGlobal getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //bugly
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = BuildConfig.APPLICATION_ID;
        // 获取当前进程名
        String processName = Utils.getProcessName(android.os.Process.myPid());

        init();
    }

    private void init() {
        // 设置当前app路径
        setAppPath();
        // 设置当前app版本
        setAppVersion();
        setChannelName(this);
        File f = new File(androidFileName_);
        if (!f.isFile()) {
            f.mkdir();
        }
        Log.d("AppGlobal", "init");
        //初始化litepal
    }

    /**
     * 先于oncreate执行
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void setAppVersion() {
        try {
            // 获取当前程序版本
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, 0);
            Logger.output("packageInfo.versionName:" + packageInfo.versionName);
            setLocalAppVersion(packageInfo.versionName);
        } catch (NameNotFoundException e) {
            Logger.output("Exception:" + e.getMessage());
        }
    }

    /**
     * 设置App相对路径
     */

    private void setAppPath() {
        AppGlobal.getInstance().setAppPath(
                getApplicationContext().getFilesDir().getAbsolutePath());
    }

    /**
     * 获取Activity管理器
     *
     * @return actManager
     */
    public ActManager getActManager() {
        return actManager;
    }

    public void setActManager(ActManager actManager) {
        this.actManager = actManager;
    }


    /**
     * 获取网络类型
     *
     * @return ConnectivityManager.TYPE_WIFI :wifi otherwise:手机网络 -1:error
     */
    public int checkNetWorkType() {
        ConnectivityManager connectMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (null != info) {
            return info.getType();
        }
        return -1;
    }

    /**
     * 检查是否有网络连接
     */
    public boolean isNetworkConnected() {
        ConnectivityManager connectMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return null != info && info.isAvailable();
    }

    /**
     * 获取BaseOTA context
     *
     * @return
     */
    public Context getContext() {
        if (null == getActManager()) {
            return this;
        }

        Activity currentActivity = getActManager().currentActivity();
        if (null == currentActivity) {
            return this;
        }
        return currentActivity;
    }


    /**
     * 获取webview user agent
     */
    public String getWebViewUserAgent() {
        return String.format("yht-app/%s (Android:%s; appSource:%s; unionId:%s; type:%s; PhoneModel:%s Build:%S; Version:%s)",
                getLocalAppVersion(), Build.VERSION.SDK_INT, getChannelName(), getUnionId(), getType(),
                Build.MODEL, Build.ID, Build.VERSION.RELEASE);
    }

    /**
     * 获取user agent
     */
    public String getUserAgent() {
        return String.format("yht-app/%s (Android:%s; appSource:%s; unionId:%s; type:%s; PhoneModel:%s Build:%S; Version:%s)",
                getLocalAppVersion(), Build.VERSION.SDK_INT, getChannelName(), getUnionId(), getType(),
                Build.MODEL, Build.ID, Build.VERSION.RELEASE);
    }

    /**
     * 获取当前App版本
     */
    public String getLocalAppVersion() {
        Logger.output("localAppVersion:" + localAppVersion);
        return localAppVersion;
    }

    public void setLocalAppVersion(String localAppVersion) {
        this.localAppVersion = localAppVersion;
    }

    public String getUnionId() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (TextUtils.isEmpty(unionId)) {
            unionId = preference.getString("unionId", null);
        }

        if (TextUtils.isEmpty(unionId)) {//如果唯一识别码为空则尝试获取mac地址
            unionId = getMacAddress();
        }

        if (TextUtils.isEmpty(unionId) || "02:00:00:00:00:00".equals(unionId)) {//如果唯一识别码为空或者为无效mac地址则尝试获取手机序列号
            unionId = Build.SERIAL;
        }

        if (TextUtils.isEmpty(unionId) || "02:00:00:00:00:00".equals(unionId)) {//如果唯一识别为空或者为无效mac地址则获取UUID
            unionId = getUUID();
        }

        preference.edit().putString("unionId", unionId);
        return unionId;
    }

    public String getType() {
        return BuildConfig.APPLICATION_ID.replace("yht", "1haitao");
    }

    private String getMacAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    public String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }


    /**
     * 获取当前程序路径
     */
    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    /**
     * 检查SDcard是否存在
     *
     * @return boolean
     */
    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
        titleBarHeight = (int) (50 * displayMetrics.density);
        tabBarHeight = (int) (55 * displayMetrics.density);
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度，单位px
     */
    public int getScreenWidth() {
        if (displayMetrics == null) {
            DisplayMetrics display = this.getResources().getDisplayMetrics();
            setDisplayMetrics(display);
        }
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度，单位px
     */
    public int getScreenHeight() {
        if (displayMetrics == null) {
            DisplayMetrics display = this.getResources().getDisplayMetrics();
            setDisplayMetrics(display);
        }
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public float getScreenDensity() {
        if (displayMetrics == null) {
            DisplayMetrics display = this.getResources().getDisplayMetrics();
            setDisplayMetrics(display);
        }
        return displayMetrics.density;
    }

    /**
     * 获取标题栏高度
     *
     * @return 标题栏高度，单位px
     */
    public int getTitleBarHeight() {
        return titleBarHeight;
    }

    /**
     * 获取TabBar高度
     *
     * @return TabBar高度，单位px
     */
    public int getTabBarHeight() {
        return tabBarHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度, 单位px
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean isApkDebug() {
        return isApkDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public int dp2px(int dp) {
        return (int) (dp * getScreenDensity());
    }

    public String getChannelName() {
        return channelName;
    }

    private void setChannelName(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    BuildConfig.APPLICATION_ID, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                channelName = appInfo.metaData.getString("UMENG_CHANNEL");
            }
        } catch (NameNotFoundException e) {
            // if we can’t find it in the manifest, just return null
        }

    }

    public String getBAICHUAN_APP_KEY() {
        return BAICHUAN_APP_KEY;
    }
}
