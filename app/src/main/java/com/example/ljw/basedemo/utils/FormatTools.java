package com.example.ljw.basedemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;

public class FormatTools {
    private static FormatTools tools = new FormatTools();

    private FormatTools() {
    }

    public static FormatTools getInstance() {
        if (tools == null) {
            tools = new FormatTools();
            return tools;
        }
        return tools;
    }

    // 将byte[]转换成InputStream
    public InputStream Byte2InputStream(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    // 将InputStream转换成byte[]
    public byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将Bitmap转换成InputStream
    public InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    // 将Bitmap转换成InputStream
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    // 将InputStream转换成Bitmap
    public Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    // Drawable转换成InputStream
    public InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = this.drawable2Bitmap(d);
        return this.Bitmap2InputStream(bitmap);
    }

    // InputStream转换成Drawable
    public Drawable InputStream2Drawable(Context context, InputStream is) {
        Bitmap bitmap = this.InputStream2Bitmap(is);
        return this.bitmap2Drawable(context, bitmap);
    }

    // Drawable转换成byte[]
    public byte[] Drawable2Bytes(Drawable d) {
        Bitmap bitmap = this.drawable2Bitmap(d);
        return this.Bitmap2Bytes(bitmap);
    }

    // byte[]转换成Drawable
    public Drawable Bytes2Drawable(Context context, byte[] b) {
        Bitmap bitmap = this.Bytes2Bitmap(b);
        return this.bitmap2Drawable(context, bitmap);
    }

    // Bitmap转换成byte[]
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // byte[]转换成Bitmap
    public Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    // Drawable转换成Bitmap
    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap转换成Drawable
    public Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * 截取小数点
     *
     * @param data  需要截取的数据
     * @param scale 保留几位
     * @param isTop true:向上取整 false:向下取整
     * @return
     */
    public String formatDecimalPoint(String data, int scale, boolean isTop) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        BigDecimal bd = new BigDecimal(data);
        BigDecimal bd1 = bd.setScale(scale, isTop ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_HALF_DOWN);
        return bd1.toString();
    }

    /**
     * 判断数据是否等于0
     */
    public boolean compareTo(String data) {
        if (TextUtils.isEmpty(data)) {
            return false;
        }
        BigDecimal data1 = new BigDecimal("0.0");
        BigDecimal data2 = new BigDecimal(data);
        if (0 != data1.compareTo(data2)) {//@return {@code 1} if {@code this > val}, {@code -1} if {@code this < val}, {@code 0} if {@code this == val}.
            return false;
        }
        return true;
    }

    /**
     * 处理数据精度
     *
     * @param value
     * @param scale 精度
     * @return
     */
    public static float getFormatFloat(float value, int scale) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 去掉小数点后面的0
     *
     * @param number
     */
    public static String formatDouble(double number) {
        String s = number + "";
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}