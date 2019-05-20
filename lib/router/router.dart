import 'dart:convert';

import 'package:common_flutter/test/fade_app_test.dart';
import 'package:common_flutter/test/test_sample.dart';
import 'package:flutter/material.dart';

class RouterManager {
  static RouterManager mInstance = new RouterManager();

  static RouterManager getInstance() {
    return mInstance;
  }

  /*
    根据传入的协议名  找到对应跳转的路径
   */
  StatefulWidget getPageByRouter(String json) {
    String path = "";
    String param = "";
    if (json != null && json.isNotEmpty && json != "/") {
      var jsonResponse = jsonDecode(json);
      path = jsonResponse["path"];
      print("==== router === path = $path");
      param = jsonResponse["param"];
      print("==== router === param = $param");
      path = path != null && path.isNotEmpty ? path : RouterPath.TEST_SAMPLE;
      param = param != null && param.isNotEmpty ? param : "";
    }

    Widget widget = _getWidgetPage(path);

    if (widget == null) {
      debugPrint('==== 找不到协议 $path 请确保协议有注册进来 =====');
    }
    return widget;
  }

  Widget _getWidgetPage(String path) {
    Widget widget;
    switch (path) {
      case RouterPath.TEST_SAMPLE:
        widget = MyHomePage(title: 'Flutter Demo Home Page');
        break;
      case RouterPath.TEST_FADE_APP:
        widget = MyFadeTest();
        break;
    }
    return widget;
  }
}

class RouterPath {
  //================ 测试相关界面 =======================
  static const String TEST_SAMPLE = 'test_sample';
  static const String TEST_FADE_APP = 'test_fade_app';

  //================ ui相关界面 =======================
}
