package com.pythoncat.ipcorservice3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.ipcorservice3.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            //创建okHttpClient对象
            OkHttpClient mOkHttpClient = new OkHttpClient();
            //创建一个Request
            final Request request = new Request.Builder()
                    .url("https://github.com/hongyangAndroid")
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
                    String htmlStr = response.body().string();
                    LogUtils.i(htmlStr);
                    runOnUiThread(()-> tvShow.setText(htmlStr));
                }
            });
        });

        btnPost.setOnClickListener(v -> {

        });
    }

    private void init() {
        tvShow = (TextView) findViewById(R.id.tv_test_ok);
        btnGet = (Button) findViewById(R.id.test_btn_get);
        btnPost = (Button) findViewById(R.id.test_btn_post);
    }
}
