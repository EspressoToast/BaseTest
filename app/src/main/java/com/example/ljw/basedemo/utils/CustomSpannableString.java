package com.example.ljw.basedemo.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;


/**
 * Created by chendy on 16/11/1.
 * 自定义view spannable
 */

public class CustomSpannableString extends SpannableString {

    public CustomSpannableString(CharSequence source) {
        super(source);
    }

    /**
     * 设置字体颜色以及字体大小样式
     *
     * @param mContext   上下文
     * @param startIndex 开始下标
     * @param endIndex   结束下标
     * @param color      颜色
     * @param textSize   字体大小
     */
    public void setColorAndSizeSpan(Context mContext, int startIndex, int endIndex, @ColorRes int color, int textSize) {
        setColorSpan(mContext, startIndex, endIndex, color);
        setSizeSpan(startIndex, endIndex, textSize);
    }

    /**
     * 设置颜色样式
     *
     * @param mContext      上下文
     * @param startIndex    开始下标
     * @param endIndex      结束下标
     * @param color         颜色
     */
    public void setColorSpan(Context mContext, int startIndex, int endIndex, @ColorRes int color) {
        setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, color)),
                startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置字体样式
     *
     * @param startIndex    开始下标
     * @param endIndex      结束下标
     * @param textSize      字体大小
     */
    public void setSizeSpan(int startIndex, int endIndex, int textSize) {
        setSpan(new AbsoluteSizeSpan(textSize, true), startIndex, endIndex,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
