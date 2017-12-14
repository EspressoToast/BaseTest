package com.example.ljw.basedemo.utils;


import com.example.ljw.basedemo.AppGlobal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetsTools {

    //从assets 文件夹中获取文件并读取数据
    public static String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = AppGlobal.getInstance().getActManager().currentActivity().getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取本地文件中JSON字符串
     *
     * @param fileName
     * @return
     */
    public static String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    AppGlobal.getInstance().getActManager().currentActivity().getResources().getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
