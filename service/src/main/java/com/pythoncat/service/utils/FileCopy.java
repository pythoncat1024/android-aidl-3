package com.pythoncat.service.utils;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class FileCopy {

    /**
     * 文件拷贝
     *
     * @param from 源文件路径
     * @param dest 目标路径
     */
    public static void copy(String from, String dest) throws IOException {

        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(dest))
            throw new RuntimeException(" path is empty");
        File srcF = new File(from);
        File destF = new File(dest);
        if (!srcF.isFile()) throw new RuntimeException("srcF is not a file!");

        FileInputStream in = new FileInputStream(srcF);
        FileOutputStream out = new FileOutputStream(destF);
        BufferedInputStream buffIn = new BufferedInputStream(in);
        BufferedOutputStream buffOut = new BufferedOutputStream(out);

        byte[] buffer = new byte[256];
        long len = 0;
        int c;
        LogUtils.w("copy started....progress ==" + len + ", total==" + srcF.length());
        while ((c = buffIn.read(buffer)) != -1) {
            buffOut.write(buffer, 0, c);
            len += c;
            if (len == srcF.length()) {
                LogUtils.w("copy finished....progress ==" + len + ", total==" + srcF.length());
            } else if (len < srcF.length()) {
                LogUtils.w("copying....progress ==" + len + ", total==" + srcF.length());
            } else {
                LogUtils.e("copy error !!!, progress> total ! ### " + "...progress ==" + len + ", total==" + srcF.length());
            }
        }
    }

    /**
     * 文件拷贝
     *
     * @param from 源文件路径
     * @param dest 目标路径
     */
//    public static void copy(String from, String dest, CallBack callback) throws IOException, RemoteException {
//
//        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(dest))
//            throw new RuntimeException(" path is empty");
//        File srcF = new File(from);
//        File destF = new File(dest);
//        if (!srcF.isFile()) throw new RuntimeException("srcF is not a file!");
//
//        FileInputStream in = new FileInputStream(srcF);
//        FileOutputStream out = new FileOutputStream(destF);
//        BufferedInputStream buffIn = new BufferedInputStream(in);
//        BufferedOutputStream buffOut = new BufferedOutputStream(out);
//
//        byte[] buffer = new byte[256];
//        long len = 0;
//        int c;
//        LogUtils.w("copy started....progress ==" + len + ", total==" + srcF.length());
//        callback.callback(len, srcF.length());
//        while ((c = buffIn.read(buffer)) != -1) {
//            buffOut.write(buffer, 0, c);
//            len += c;
//            callback.callback(len, srcF.length());
//            if (len == srcF.length()) {
//                LogUtils.w("copy finished....progress ==" + len + ", total==" + srcF.length());
//            } else if (len < srcF.length()) {
//                LogUtils.w("copying....progress ==" + len + ", total==" + srcF.length());
//            } else {
//                LogUtils.e("copy error !!!, progress> total ! ### " + "...progress ==" + len + ", total==" + srcF.length());
//            }
//        }
//    }
}
