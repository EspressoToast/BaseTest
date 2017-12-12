package com.example.ljw.basedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * 通用工具类
 */
public class Utils {
//    public static final int PACKAGE_TEST = 2;

    public static final String MD5_KEY = "yht_app_v1";
    public static final String CITYS_LOCAL_FILE_NAME = "citys.json";
    public static final String STAUTS_FILE_NAME = "status.json";
    public static final String HOME_LOCAL_FILE_NAME = "default_home.json";
    public static final String SERVICE_LOCAL_FILE_NAME = "default_service.json";
    public static final String HAITAO_JS_FILE_NAME = "haitao.js";
    public static final String WEB_CONDITION_FILE_NAME = "webcondition.js";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PWD = "^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,}$";
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((1[0-9]))\\d{9}$";

    /**
     * 加法
     *
     * @return
     */
    public static String add(String... strs) {
        String result = "0";
        for (String str : strs) {
            try {
                BigDecimal b1 = new BigDecimal(result);
                BigDecimal b2 = new BigDecimal((TextUtils.isEmpty(str) ? "0" : str));
                result = b1.add(b2).toString();
            } catch (NumberFormatException e) {
                Logger.output("NumberFormatException : Utils - > add()");
            }
        }
        return result;
    }

    /**
     * 减法
     *
     * @return
     */
    public static String subtract(String... strs) {
        String str = strs[0];
        for (int i = 1; i < strs.length; i++) {
            try {
                BigDecimal b1 = new BigDecimal((TextUtils.isEmpty(str) ? "0" : str));
                BigDecimal b2 = new BigDecimal((TextUtils.isEmpty(strs[i]) ? "0" : strs[i]));
                str = b1.subtract(b2).toString();
            } catch (NumberFormatException e) {
                Logger.output("NumberFormatException : Utils - > subtract()");
            }
        }
        return str;
    }

    /**
     * 乘法
     *
     * @return
     */
    public static String multiply(String... strs) {
        String result = "1";
        for (String str : strs) {
            try {
                BigDecimal b1 = new BigDecimal(result);
                BigDecimal b2 = new BigDecimal((TextUtils.isEmpty(str) ? "0" : str));
                result = b1.multiply(b2).toString();
            } catch (NumberFormatException e) {
                Logger.output("NumberFormatException : Utils - > multiply()");
            }
        }
        return result;
    }

    /**
     * 除法
     */
    public static String division(String... strs) {
        String result = "0";
        try {
            if (strs.length < 2) {
                return result;
            }
            result = strs[0];
            for (int i = 1; i < strs.length; i++) {
                BigDecimal b1 = new BigDecimal(result);
                BigDecimal b2 = new BigDecimal((TextUtils.isEmpty(strs[i]) ? "1" : strs[i]));
                result = b1.divide(b2).toString();
            }
        } catch (Exception e) {
            Logger.output("Exception : Utils - > division()");
        }
        return result;
    }


    public static String division(boolean isTop, int scale, String... strs) {
        String result = "0";
        try {
            if (strs.length < 2) {
                return result;
            }
            result = strs[0];
            for (int i = 1; i < strs.length; i++) {
                BigDecimal b1 = new BigDecimal(result);
                BigDecimal b2 = new BigDecimal((TextUtils.isEmpty(strs[i]) ? "1" : strs[i]));
                result = b1.divide(b2, scale, isTop ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_HALF_DOWN).toString();
            }
        } catch (Exception e) {
            Logger.output("Exception : Utils - > division()");
        }
        return result;
    }

    /**
     * 比较版本
     *
     * @param version1 本地版本
     * @param version2 服务器版本
     * @return 【如果本地版本 > 服务器版本，返回1】【如果一样，返回0】【其它，返回-1】
     */
    public static int compare(String version1, String version2) {
        if (TextUtils.isEmpty(version1) || TextUtils.isEmpty(version2)) {
            Logger.output("version is invalidate! version1:" + version1 + "version2:" + version2);
            return -1;
        }

        int index1 = 0;
        int index2 = 0;
        assert version1 != null;
        assert version2 != null;
        while (index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            int[] number2 = getValue(version2, index2);

            //如果version1版本小于version2，那么version2的版本大
            if (number1[0] < number2[0]) {
                return -1;
            }
            //如果version1版本大于version2，那么version1的版本大
            else if (number1[0] > number2[0]) {
                return 1;
            }
            //如果上一个版本比较不出来大小的话，继续比较下一个
            else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }

        //如果version1与version2没有比较出版本的大小，并且两个版本的长度一致
        if (version1.length() == version2.length()) {
            return 0;
        }
        //如果version1版本号比version版本号长
        if (version1.length() > version2.length()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 截取小数点前的版本号
     *
     * @param version 版本号
     * @param index   开始位置
     * @return 从开始位置到下一个小数点之间的版本号
     */
    public static int[] getValue(String version, int index) {
        Logger.output("test :" + version + "," + index);
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        Logger.output("test :" + version.charAt(index));
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        Logger.output("test :" + sb.toString());
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;

        return value_index;
    }

    /**
     * 安装APK
     *
     * @return
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive"); //此处Android应为android，否则造成安装不了
        context.startActivity(intent);
    }

    /**
     * 计算当前时间与所给的时间差
     */
    public static String getTimeDistance(String time) {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        long temp = now - date.getTime();
        long years = temp / 1000 / 3600 / 24 / 30 / 12; // 相差年数
        long monthes = temp / 1000 / 3600 / 24 / 30; //相差月数
        long days = temp / 1000 / 3600 / 24;  // 相差天数
        long hours = temp / 1000 / 3600;      // 相差小时数
        long temp2 = temp % (1000 * 3600);
        long mins = temp2 / 1000 / 60;        // 相差分钟数
        if (years > 0) {
            return years + "年前";
        } else if (monthes > 0) {
            return monthes + "个月前";
        } else if (days > 0) {
            return days + "天前";
        } else if (hours > 0) {
            return hours + "小时前";
        } else {
            return mins + "分钟前";
        }
    }

    /**
     * 价格最多显示小数点后面两位
     */
    public static String filterPrice(String price) {
        if (TextUtils.isEmpty(price)) {
            return "";
        }

        double doublePrice = 0;
        try {
            doublePrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return price;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(doublePrice);
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }


    /**
     * 比较器类
     */
    public static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕高度
     */
    public static int getWindowHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWindowWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
