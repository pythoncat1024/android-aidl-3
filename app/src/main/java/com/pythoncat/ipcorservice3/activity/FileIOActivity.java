package com.pythoncat.ipcorservice3.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.ipcorservice3.R;
import com.pythoncat.ipcorservice3.base.BaseActivity;
import com.pythoncat.ipcorservice3.utils.CopyApi;
import com.pythoncat.ipcorservice3.utils.Path;
import com.pythoncat.service.IOInterface;
import com.pythoncat.service.callback.IOCallback;
import com.pythoncat.service.domain.FileInfo;
import com.pythoncat.service.service.FileService;

public class FileIOActivity extends BaseActivity {

    private IOInterface mFileBinder;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io);
        init();
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtils.e(name + "\n  connected ...");
                mFileBinder = IOInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mFileBinder = null;
                LogUtils.e(name + "\n disconnected ...");
            }
        };
        bindService(new Intent(get(), FileService.class), conn, Context.BIND_AUTO_CREATE);
    }

    private void init() {
        TextView tvIOprogress = (TextView) findViewById(R.id.tv_show_io_progress);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar_file_io);
        pb.setMax(100);
        tvIOprogress.setText(getString(R.string.io_progress, ""));
        findViewById(R.id.btn_start_io).setOnClickListener(v -> {
            // todo 开始复制
            LogUtils.w("todo 开始复制.........");
            CopyApi.copy(Path.src, Path.dest, mFileBinder, new IOCallback.Stub() {
                @Override
                public void callback(FileInfo fo) throws RemoteException {
                    runOnUiThread(() -> {
                        LogUtils.e(fo);
                        int progress = (int) (fo.progress * 1f / fo.total * 100f);
                        pb.setProgress(progress);
                        String pro = progress + "%";
                        LogUtils.d(pro);
                        tvIOprogress.setText(getString(R.string.io_progress, pro));
                    });
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
