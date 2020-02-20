package com.pythoncat.ipcorservice3.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    public static final int REQUEST_CODE = 123;
    private IOInterface mFileBinder;
    private ServiceConnection conn;
    private TextView tvProgress;
    private ProgressBar pb;

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
        tvProgress = findViewById(R.id.tv_show_io_progress);
        pb = findViewById(R.id.progressBar_file_io);
        pb.setMax(100);
        tvProgress.setText(getString(R.string.io_progress, ""));
        findViewById(R.id.btn_start_io).setOnClickListener(v -> {
            // todo 开始复制
            LogUtils.w("todo 开始复制.........");
            int selfPermission = ActivityCompat.checkSelfPermission(get(), Manifest.permission_group.STORAGE);
            if (selfPermission == PackageManager.PERMISSION_GRANTED) {
                copy();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        });
    }

    private void copy() {
        CopyApi.copy(Path.src, Path.dest, mFileBinder, new IOCallback.Stub() {
            @Override
            public void callback(FileInfo fo) {
                runOnUiThread(() -> {
                    LogUtils.e(fo);
                    int progress = (int) (fo.progress * 1f / fo.total * 100f);
                    pb.setProgress(progress);
                    String pro = progress + "%";
                    LogUtils.d(pro);
                    tvProgress.setText(getString(R.string.io_progress, pro));
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                copy();
            } else {
                LogUtils.e(permissions);
                LogUtils.e("PackageManager.PERMISSION_DENIED !!!");
            }
        }
    }
}
