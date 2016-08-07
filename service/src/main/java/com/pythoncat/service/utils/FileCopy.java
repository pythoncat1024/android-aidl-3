package com.pythoncat.service.utils;

import android.os.RemoteException;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.service.callback.IOCallback;
import com.pythoncat.service.domain.FileInfo;
import com.pythoncat.service.i.IOState;

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
    public static void copy(String from, String dest, IOCallback callback) throws IOException, RemoteException {

        FileInputStream in = null;
        FileOutputStream out = null;
        BufferedInputStream buffIn = null;
        BufferedOutputStream buffOut = null;
        try {
            FileInfo info = new FileInfo();
            info.src = from;
            info.dest = dest;
            info.state = IOState.unStated;
            // ```````````````````````````..........
            if (TextUtils.isEmpty(from) || TextUtils.isEmpty(dest)) {
                LogUtils.e("\" path is empty\"");
                return;
            }

            File srcF = new File(from);
            File destF = new File(dest);
            if (!srcF.isFile()) {
                LogUtils.e(srcF + "\nsrcF is not a file!");
                return;
            }

            in = new FileInputStream(srcF);
            out = new FileOutputStream(destF);
            buffIn = new BufferedInputStream(in);
            buffOut = new BufferedOutputStream(out);

            byte[] buffer = new byte[1024];
            long len = 0;
            int c;
            LogUtils.w("copy started....progress ==" + len + ", total==" + srcF.length());

            info.state = IOState.start;
            info.progress = 0;
            info.total = srcF.length();
            int index = (int) (info.progress * 1f / info.total * 100f);
            callback.callback(info);
            while ((c = buffIn.read(buffer)) != -1) {
                buffOut.write(buffer, 0, c);
                len += c;
                info.state = IOState.doing;
                info.progress = len;
                if (info.progress == info.total) {
                    info.state = IOState.finished;
                }
                if (index % 5 == 0) {
                    callback.callback(info);
                }
                if (len == srcF.length()) {
                    LogUtils.w("copy finished....progress ==" + len + ", total==" + srcF.length());
                } else if (len < srcF.length()) {
                    LogUtils.w("copying....progress ==" + len + ", total==" + srcF.length());
                } else {
                    LogUtils.e("copy error !!!, progress> total ! ### " + "...progress ==" + len + ", total==" + srcF.length());
                }
            }
        } finally {
            if (buffOut != null) {
                buffOut.close();
            }
            if (buffIn != null) {
                buffIn.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
