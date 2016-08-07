package com.pythoncat.ipcorservice3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.ipcorservice3.R;
import com.pythoncat.ipcorservice3.utils.Progress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class TestOkhttpActivity extends AppCompatActivity {

    private TextView tvShow;
    private Button btnGet;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp);
        init();
        execute();
    }

    private void execute() {
        btnGet.setOnClickListener(v -> {
//            get01();
            get02();
        });

        btnPost.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url("https://github.com/hongyangAndroid")
                    .method("post", new RequestBody() {
                        @Override
                        public MediaType contentType() {
                            return null;
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {

                        }
                    })
                    .build();
        });
    }

    private void get02() {
        String url = "http://music.baidu.com/cms/BaiduMusic-pcwebapphomedown1.apk";
        new Thread(()->{
            try {
                new Progress().run(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void get01() {
        // todo 任何的又返回的http请求都是一个下载！！！
        //todo  如果你把返回的数据当成字符串，显示在界面上，那么久看不到下载的文件
        //todo 如果你把返回的数据当成一个byte[] 或者 inputStream，就可以存放到一个文件中，并在硬盘上找到他！
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
//        http://music.baidu.com/cms/BaiduMusic-pcwebapphomedown1.apk
        final Request request = new Request.Builder()
                .url("http://music.baidu.com/cms/BaiduMusic-pcwebapphomedown1.apk")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileOutputStream out = null;
                byte[] bytes = response.body().bytes();
                LogUtils.e(">>>>>>>>>>>>>> == " + bytes.length);
//                >>>>>>>>>>>>>> == 13496684
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

    private void init() {
        tvShow = (TextView) findViewById(R.id.tv_test_ok);
        btnGet = (Button) findViewById(R.id.test_btn_get);
        btnPost = (Button) findViewById(R.id.test_btn_post);
    }
}
