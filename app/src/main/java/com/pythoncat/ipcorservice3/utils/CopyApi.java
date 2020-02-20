package com.pythoncat.ipcorservice3.utils;

import android.os.RemoteException;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.service.IOInterface;
import com.pythoncat.service.callback.IOCallback;
import com.pythoncat.service.domain.FileInfo;
import com.pythoncat.service.i.IOState;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class CopyApi {
    public static void copy(IOInterface binder, IOCallback.Stub callback) throws RemoteException {
        binder.copy("/sdcard/pythoncatDir/aaa.mp3", "/sdcard/pythoncatDir/bb.mp3", callback);
    }

    public static Observable<FileInfo> copy(String src, String dest, IOInterface binder) {

        return Observable.create(subscriber -> {
            try {
                subscriber.onStart();
                binder.copy(src, dest, new IOCallback.Stub() {
                    @Override
                    public void callback(FileInfo fo) throws RemoteException {
                        LogUtils.d("fo.progress===" + fo.progress);
                        if (fo.progress == fo.total || fo.state == IOState.finished) {
                            LogUtils.d("FFFFFFFFFFFFFFFFFFFFF");
                            subscriber.onCompleted();
                        } else {
                            LogUtils.d("eeeeeeeeeeeeeeeeeeeeeeeeee");
                            subscriber.onNext(fo);
                        }
                    }
                });
                int x = 9 / 0;
            } catch (RemoteException e) {
                LogUtils.e("dddddddddddddddddd");
                e.printStackTrace();
                LogUtils.e(e);
                subscriber.onError(e);
            }
        });
    }

    public static void copy(String src, String dest, IOInterface binder, IOCallback.Stub callback) {
        new Thread(() -> {
            try {
                binder.copy(src,dest,callback);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtils.e(e);
            }
        }).start();
    }
}
