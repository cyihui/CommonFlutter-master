package com.cyh.commonflutter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class FlutterTestActivity extends FlutterActivity {

    private String TAG = "FlutterTestActivity";

    private String METHOD_CHANNEL = "common.flutter/battery";
    private String EVENT_CHANNEL = "common.flutter/message";
    private String BASIC_CHANNEL = "common.flutter/basic";
    private String GET_BATTERY_LEVEL = "getBatteryLevel";
    private MethodChannel methodChannel;
    private Timer timer;

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

        timer = new Timer();

        initEventChannel();

        initBasicMessageChannel();

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

    private int count = 0;

    private void initEventChannel() {
        new EventChannel(getFlutterView(), EVENT_CHANNEL).setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (count < 10) {
                            count++;
                            events.success("当前时间:" + System.currentTimeMillis());
                        } else {
                            timer.cancel();
                        }
                    }
                }, 1000, 1000);
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
        if (timer != null) {
            timer.cancel();
        }
    }
}
