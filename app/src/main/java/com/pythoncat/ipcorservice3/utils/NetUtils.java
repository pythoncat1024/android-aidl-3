package com.pythoncat.ipcorservice3.utils;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class NetUtils {
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 异步get
     * <p>
     * 在一个工作线程中下载文件，当响应可读时回调Callback接口。读取响应时会阻塞当前线程。OkHttp现阶段不提供异步api来接收响应体。
     *
     * @throws Exception
     */
    public static void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                LogUtils.e(response.body().bytes());
                FileOutputStream out = null;
                byte[] bytes = response.body().bytes();
                try {
                    out = new FileOutputStream(new File("/sdcard/pythonCatDir/cc.apk"));
                    out.write(bytes);
                    out.flush();
                } finally {
                    out.close();
                }
            }
        });
    }
}
