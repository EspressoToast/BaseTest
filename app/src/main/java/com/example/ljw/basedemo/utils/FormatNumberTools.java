package com.example.ljw.basedemo.utils;

import java.text.DecimalFormat;

/**
 * Created by chendiyou on 2015/6/4.
 *
 */
public class FormatNumberTools {

    public static String formatNumberUnit(int number){
        String result = "";
        if(number >= 100000000){
            result = String.format("%.2f", number / 100000000f) + "亿";
        }else if(number >= 10000000){
            result = String.format("%.2f", number / 10000000f) + "千万";
        }else if(number >= 10000){
            result = String.format("%.2f", number / 10000f) + "万";
        }else{
            result = "" + number;
        }
        return result;
    }


    public static String formatThousandsAry(double number){
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");
        return myformat.format(number);
    }
}
