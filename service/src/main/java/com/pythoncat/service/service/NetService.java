package com.pythoncat.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.service.NetInterface;
import com.pythoncat.service.callback.NetCallback;
import com.pythoncat.service.utils.NetUtils;

import java.util.concurrent.Executors;

public class NetService extends Service {
    public NetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("net service bind....");
        return new RemoteBinder();
    }

    private class RemoteBinder extends NetInterface.Stub {

        @Override
        public void download(String url, NetCallback netCall) {
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    NetUtils.downLoad(url, netCall);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e(e);
                }
            });
        }
    }
}
