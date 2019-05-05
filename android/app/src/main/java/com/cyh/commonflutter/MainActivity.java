package com.cyh.commonflutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private AppCompatActivity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.mBtnFlutter, R.id.mBtnFlutterTest})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.mBtnFlutter:
                startActivity(new Intent(mActivity, CommonFlutterActivity.class));
                break;


            case R.id.mBtnFlutterTest:
                startActivity(new Intent(mActivity, FlutterTestActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
