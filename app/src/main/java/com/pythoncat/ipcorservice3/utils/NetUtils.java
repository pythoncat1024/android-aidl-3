package com.pythoncat.ipcorservice3.utils;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.ipcorservice3.bean.Download;

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
}
