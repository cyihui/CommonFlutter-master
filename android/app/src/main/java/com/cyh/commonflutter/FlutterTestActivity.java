package com.cyh.commonflutter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

public class FlutterTestActivity extends FlutterActivity {

    private String TAG = "FlutterTestActivity";

    private String METHOD_CHANNEL = "common.flutter/battery";
    private String EVENT_CHANNEL = "common.flutter/message";
    private String BASIC_CHANNEL = "common.flutter/basic";
    private String GET_BATTERY_LEVEL = "getBatteryLevel";
    private MethodChannel methodChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        initMethodChannel();
        getFlutterView().postDelayed(() ->
                methodChannel.invokeMethod("get_message", null, new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object o) {
                        Log.d(TAG, "get_message:" + o.toString());
                    }

                    @Override
                    public void error(String s, @Nullable String s1, @Nullable Object o) {

                    }

                    @Override
                    public void notImplemented() {

                    }
                }), 5000);


        initEventChannel();

        initBasicMessageChannel();

    }

    @Override
    public FlutterView createFlutterView(Context context) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("version", "1.0.0");
        params.put("name", "test");
        map.put("path", "test_fade_app");
        map.put("param", JSON.toJSONString(params));
        WindowManager.LayoutParams matchParent = new WindowManager.LayoutParams(-1, -1);
        FlutterNativeView nativeView = this.createFlutterNativeView();
        FlutterView flutterView = new FlutterView(FlutterTestActivity.this, null, nativeView);
        flutterView.setInitialRoute(JSON.toJSONString(map));
        flutterView.setLayoutParams(matchParent);
        flutterView.enableTransparentBackground();
        this.setContentView(flutterView);
        return flutterView;
    }

    private void initBasicMessageChannel() {
        BasicMessageChannel<Object> basicMessageChannel = new BasicMessageChannel<>(getFlutterView(), BASIC_CHANNEL, StandardMessageCodec.INSTANCE);
        //主动发送消息到flutter 并接收flutter消息回复
        getFlutterView().postDelayed(()->{
            basicMessageChannel.send("send basic message", (object)-> {
                Log.e(TAG, "receive reply msg from flutter:" + object.toString());
            });
        },6000);

        //接收flutter消息 并发送回复
        basicMessageChannel.setMessageHandler((object, reply)-> {
            Log.e(TAG, "receive msg from flutter:" + object.toString());
            reply.reply("reply：got your message");

        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int type = msg.arg1;
            EventChannel.EventSink events = (EventChannel.EventSink) msg.obj;
            if (type == 1) {
                if (events != null) {
                    events.success("当前时间:" + System.currentTimeMillis());
                }
            }
        }
    };

    private int count = 10;

    private void initEventChannel() {
        new EventChannel(getFlutterView(), EVENT_CHANNEL).setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                for (int i = 0; i < count; i++) {
                    Message msg = new Message();
                    msg.arg1 = 1;
                    msg.obj = events;
                    handler.sendMessageDelayed(msg, 1000*i);
                }
            }

            @Override
            public void onCancel(Object o) {

            }
        });
    }

    private void initMethodChannel() {
        methodChannel = new MethodChannel(getFlutterView(), METHOD_CHANNEL);
        methodChannel.setMethodCallHandler(
                (methodCall, result) -> {
                    if (methodCall.method.equals(GET_BATTERY_LEVEL)) {
                        int batteryLevel = getBatteryLevel();

                        if (batteryLevel != -1) {
                            result.success(batteryLevel);
                        } else {
                            result.error("UNAVAILABLE", "Battery level not available.", null);
                        }
                    } else {
                        result.notImplemented();
                    }
                });
    }

    private int getBatteryLevel() {
        return 90;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
