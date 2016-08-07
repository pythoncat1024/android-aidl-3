package com.pythoncat.service.utils;

import android.os.RemoteException;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.service.callback.NetCallback;
import com.pythoncat.service.domain.Download;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class NetUtils {


    /**
     * 下载文件
     *
     * @param url 下载地址
     * @return 进度信息
     */
    public static Observable<Download> downLoad(String url) {
        /**
         * // onBackpressureBuffer()方法,防止数据源发射过快引起的MissingBackpressureException
         .onBackpressureBuffer()
         */
        return Observable.create(new Observable.OnSubscribe<Download>() {
            @Override
            public void call(Subscriber<? super Download> subscriber) {
                try {
                    new Progress().run(url,
                            (bytesRead, contentLength, done) -> {
                                if (!done) {
                                    int x = (int) (bytesRead * 1f / contentLength * 100f);
                                    if (x % 10 == 0) {  // 减少回调次数

                                    }
                                    subscriber.onNext(new Download(bytesRead, contentLength, done));
                                    LogUtils.e("当前进度=== " + x + "%");
                                } else {
                                    subscriber.onCompleted();
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url 下载地址
     * @return 进度信息
     */
    public static void downLoad(String url, NetCallback nc) {
        try {
            new Progress().run(url, (bytesRead, contentLength, done) -> {
                int x = (int) (bytesRead * 1f / contentLength * 100f);
                try {
                    nc.progress(new Download(bytesRead, contentLength, done));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                LogUtils.e("当前进度=== " + x + "%");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
