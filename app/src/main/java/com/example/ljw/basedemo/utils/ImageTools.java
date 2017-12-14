package com.example.ljw.basedemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.example.ljw.basedemo.AppGlobal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageTools {

    public static void saveBitmapImage(String path, String fileName,
                                       String fileType, Bitmap mBitmap) {
        if (null == path) {
            return;
        }
        File filePath = new File(path.substring(0, path.lastIndexOf("/")));
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        if (!TextUtils.isEmpty(fileName)) {
            File fileDire = new File(path);
            if (!fileDire.exists()) {
                fileDire.mkdirs();
            }
        }

        if (null == fileName) {
            fileName = "";
        }
        if (null == fileType) {
            fileType = "";
        }

        File f = new File(path + fileName + fileType);
        try {
            f.createNewFile();
        } catch (IOException e) {
            Logger.output("Exception:" + e.getMessage());
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            Logger.output("Exception:" + e.getMessage());
        }

        if (null == fOut) {
            Logger.output("Exception: FileOutputStream is null!");
            return;
        }

        if (null == mBitmap) {
            Logger.output("Exception: mBitmap is null!");
            return;
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            Logger.output("Exception:" + e.getMessage());
        }
        try {
            fOut.close();
        } catch (IOException e) {
            Logger.output("Exception:" + e.getMessage());
        }
    }

    public static boolean fileExits(final String filePath) {
        if (null == filePath) {
            return false;
        }

        File file = new File(filePath);
        if (file.isDirectory()) {
            return false;
        }
        return file.exists();
    }

    public static Bitmap getBitMap(long userId, String iconId) {
        final String filePath = AppGlobal.getInstance().getAppPath() + "/" + userId + iconId;
        return getBitMap(filePath);
    }

    public static Bitmap getBitMap(final String filePath) {
        if (null == filePath) {
            return null;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            Logger.output("File Not Exits:" + filePath);
            return null;
        }

        FileInputStream fis = null;
        try {
            assert file != null;
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Logger.output("Exception:" + e.getMessage());
        }

        if (null == fis) {
           return null;
        }

        return BitmapFactory.decodeStream(fis);
    }


    public static Bitmap getCompressImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是1080*720分辨率，所以高和宽我们设置为
        float hh = 1080f;//这里设置高度为1080f
        float ww = 720f;//这里设置宽度为720f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300 && options > 0) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }

}
