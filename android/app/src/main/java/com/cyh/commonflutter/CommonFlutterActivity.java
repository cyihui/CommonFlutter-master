package com.cyh.commonflutter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterView;

/**
 * File: CommonFlutterActivity.java
 * Author: chenyihui
 * Date: 2019/4/29
 */
public class CommonFlutterActivity extends AppCompatActivity {

    private AppCompatActivity mActivity;
    private FlutterView mFlutterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        initFlutterMain();
        setContentView(R.layout.activity_common_flutter);

        mFlutterView = findViewById(R.id.flutter);

        enableTransparentBackground();
    }

    private void initFlutterMain(){
        FlutterMain.startInitialization(mActivity);
        FlutterMain.ensureInitializationComplete(mActivity, null);
    }

    /**
     * 去除Flutter黑影
     */
    public void enableTransparentBackground() {
        mFlutterView.enableTransparentBackground();
    }
}
