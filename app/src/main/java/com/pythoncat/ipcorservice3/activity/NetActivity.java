package com.pythoncat.ipcorservice3.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.ipcorservice3.R;
import com.pythoncat.ipcorservice3.base.BaseActivity;
import com.pythoncat.service.NetInterface;
import com.pythoncat.service.callback.NetCallback;
import com.pythoncat.service.domain.Download;
import com.pythoncat.service.service.NetService;

public class NetActivity extends BaseActivity {

    private ProgressBar pbNet;
    private TextView tvNet;
    private Button btnNet;
    private ServiceConnection conn;
    private NetInterface mNetInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        init();
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mNetInterface = NetInterface.Stub.asInterface(service);
                LogUtils.e("connected net service");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mNetInterface = null;
                LogUtils.e("disconnected net service");
            }
        };
        bindService(new Intent(get(), NetService.class), conn, Context.BIND_AUTO_CREATE);
    }

    private void init() {
        String url = "http://music.baidu.com/cms/BaiduMusic-pcwebapphomedown1.apk";
        pbNet = (ProgressBar) findViewById(R.id.pb_net);
        pbNet.setMax(100);
        tvNet = (TextView) findViewById(R.id.tv_net_show);
        tvNet.setText(getString(R.string.dow, "HA"));
        btnNet = (Button) findViewById(R.id.btn_net_download);
        btnNet.setOnClickListener(v -> {
            if (mNetInterface != null) {
                try {
                    mNetInterface.download(url, new NetCallback.Stub() {
                        @Override
                        public void progress(Download download) throws RemoteException {
                            runOnUiThread(() -> {
                                int x = (int) (download.progress * 1f / download.total * 100f);
                                pbNet.setProgress(x);
                                LogUtils.e(x);
                                tvNet.setText("download: progress==" + x + "%");
                                tvNet.setTextColor(Color.BLACK);
                            });
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                    LogUtils.e(e);
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
