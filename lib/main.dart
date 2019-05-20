import 'package:common_flutter/router/router.dart';
import 'package:flutter/material.dart';
import 'dart:ui' as ui;

//void main() => runApp(MyApp());
void main() => runApp(new MaterialApp(
  home: _widgetRouter(ui.window.defaultRouteName),
));


Widget _widgetRouter(String json){
  print("==== main === json = $json");
  return RouterManager.getInstance().getPageByRouter(json);
}
