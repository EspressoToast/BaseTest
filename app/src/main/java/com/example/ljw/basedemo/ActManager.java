package com.example.ljw.basedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.ljw.basedemo.utils.Logger;

import java.util.Calendar;
import java.util.Stack;

public class ActManager {
    private static Stack<Activity> activityStack;
    private static ActManager instance = new ActManager();
    private long lastForwardTime = 0;
    private Activity lastAct = null;

    private ActManager() {
    }

    public static ActManager Instance() {
        return instance;
    }





    /**
     * 防止页面短时间重复跳转
     *
     * @return true:可以跳转 false:不跳转
     */
    private boolean checkForward(Context context) {
        Activity activity = null;
        boolean flag = true;
        try {
            activity = (Activity) context;
        } catch (Exception e) {
            Logger.output("Warning!!! ApplicationContext");
            //说明是ApplicationContext
        }

        long time = Calendar.getInstance().getTimeInMillis();
        if (null != activity) {
            if (null != lastAct) {
                //如果是相同activity,并且在150ms内跳转多次,则屏蔽多次跳转
                if (time - lastForwardTime < 150 && activity == lastAct) {
                    flag = false;
                }
            }
        }
        lastForwardTime = time;
        lastAct = activity;
        return flag;
    }








    /**
     * 跳转至下一页面
     *
     * @param packageContext context
     * @param intent intent
     */
    public void forwardActivity(Context packageContext, Intent intent) {
        if (checkForward(packageContext)) {
            packageContext.startActivity(intent);
        }
    }

    /**
     * 跳转至下一页面
     *
     * @param packageContext context
     * @param cls class
     */
    public void forwardActivity(Context packageContext, Class<?> cls) {
        if (checkForward(packageContext)) {
            Intent intent = new Intent(packageContext, cls);
            packageContext.startActivity(intent);
        }
    }

    /**
     * 跳转至下一页面， 传递 String参数
     */
    public void forwardActivity(Context packageContext, Class<?> cls, String key, String extra) {
        if (checkForward(packageContext)) {
            Intent intent = new Intent(packageContext, cls);
            intent.putExtra(key, extra);
            packageContext.startActivity(intent);
        }
    }

    /**
     * 跳转至下一页面， 传递 int 参数
     */
    public void forwardActivity(Context packageContext, Class<?> cls, String key, int extra) {
        if (checkForward(packageContext)) {
            Intent intent = new Intent(packageContext, cls);
            intent.putExtra(key, extra);
            packageContext.startActivity(intent);
        }
    }

    /**
     * 跳转至下一页面， 传递 long 参数
     */
    public void forwardActivity(Context packageContext, Class<?> cls, String key, long extra) {
        if (checkForward(packageContext)) {
            Intent intent = new Intent(packageContext, cls);
            intent.putExtra(key, extra);
            packageContext.startActivity(intent);
        }
    }

    /**
     * 跳转至下一页面， 传递 double 参数
     */
    public void forwardActivity(Context packageContext, Class<?> cls, String key, double extra) {
        if (checkForward(packageContext)) {
            Intent intent = new Intent(packageContext, cls);
            intent.putExtra(key, extra);
            packageContext.startActivity(intent);
        }
    }

    /**
     * 跳转至下一页面， 传递 boolean 参数
     */
    public void forwardActivity(Context packageContext, Class<?> cls, String key, boolean extra) {
        if (checkForward(packageContext)) {
            Intent intent = new Intent(packageContext, cls);
            intent.putExtra(key, extra);
            packageContext.startActivity(intent);
        }
    }

        /**
         * 将该 activity 入画面栈
         *
         * @param activity act
         */
        public void pushActivity(Activity activity) {
            if (activityStack == null) {
                activityStack = new Stack<>();
            }
            if (activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
            activityStack.add(activity);
        }

    /**
     * 返回上一个 activity
     */
    public void popActivity() {
        if (activityStack == null) {
            activityStack = new Stack<>();
            return;
        }

        if (activityStack.isEmpty()) {
            return;
        }

        Activity activity = activityStack.pop();
        if (activity != null) {
            if (!activity.isFinishing())
                activity.finish();
        }
    }

    /**
     * 返回上一个 activity，并且上一个activity不为指定类名activity,否则继续pop
     */
    public void popActivity(Class<?> cls) {
        popActivity();
        if (isCurrentActivity(cls)) {
            popActivity();
        }
    }

    /**
     * 根据类名判断是否是当前activity
     */
    private boolean isCurrentActivity(Class<?> cls) {
        Activity currentActivity = currentActivity();
        if (null == currentActivity) {
            return false;
        }

        if (currentActivity.getClass().equals(cls)) {
            return true;
        }
        return false;
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            }
        }
    }

    /**
     * 弹出画面栈中的某一个 activity
     *
     * @param activity 要弹出的 activity 对象
     */
    public void popActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }

        if (activityStack.isEmpty()) {
            return;
        }

        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing())
                activity.finish();
        }
    }

    /**
     * 获取当前的 activity
     *
     * @return act
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 清空画面栈，退出程序
     */
    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 清空画面栈，并且进入某一个页面
     */
    public void popAllActivityNoExit() {
        while (true) {
            Activity activityTemp = currentActivity();
            if (activityTemp == null) {
                break;
            }
            popActivity(activityTemp);
        }
    }

    /**
     * 返回到指定 activity
     *
     * @param cls 指定的activity
     */
    public void backToActivity(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                return;
            }
            popActivity(activity);
        }
    }

    /**
     * 弹出所有画面栈，仅保留某一个 activity
     *
     * @param cls 要保留的 activity 类
     */
    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 判断画面栈中是否存在该 activity 对象
     *
     * @param activity act
     * @return 存在返回TRUE ，不存在返回FALSE
     */
    public boolean existActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }

        return !activityStack.isEmpty() && activityStack.contains(activity);

    }

    public boolean hasOtherActivity(Activity activity) {
        if (null == activityStack || activityStack.isEmpty()) {
            return false;
        }

        if (activityStack.size() > 1) {
            return true;
        } else if (activityStack.size() == 1) {
            return !activityStack.contains(activity);
        }
        return false;
    }

}
