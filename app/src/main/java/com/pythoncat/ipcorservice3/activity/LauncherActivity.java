package com.pythoncat.ipcorservice3.activity;

import android.content.Intent;
import android.os.Bundle;

import com.pythoncat.ipcorservice3.R;
import com.pythoncat.ipcorservice3.base.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.btn_file_copy).setOnClickListener(v -> {
            startActivity(new Intent(get(), FileIOActivity.class));
        });

        findViewById(R.id.btn_file_download).setOnClickListener(v -> {
            startActivity(new Intent(get(), NetActivity.class));
        });


        findViewById(R.id.btn_test_okhttp).setOnClickListener(v -> {
            startActivity(new Intent(get(), TestOkhttpActivity.class));
        });
    }

}
