# flutter 公共库

地址:[https://github.com/cyihui/CommonFlutter-master](https://github.com/cyihui/CommonFlutter-master)

## 项目结构

```
.
├── test                       # 单元测试
├── android                    # android原生代码入口
├── ios                        # ios原生代码入口
├── lib                        # flutter代码入口
├── pubspec.yaml               # flutter项目配置
└── README.md                  # 项目说明
```

### 编译项目命令
    flutter build apk

### Android 模块

```
.
├── flutter                         # flutter目录
├─────AndroidManifest.xml           # AndroidManifest.xml
├── main                            # main目录
├─────assets                        # assets
├───────flutter_assets              # flutter产物
├── java                            # 业务代码
├── res                             # 项目res资源
├── AndroidManifest.xml             # 运行android对应的AndroidManifest.xml
├── maven                           # maven打包文件
├─────AndroidManifest.xml           # 运行maven对应的AndroidManifest.xml
├── build.gradle                    # build.gradle
└── gradle.properties               # gradle.properties
```

### aar打包
   首先，将flutter的release产物<`build/app/intermediates/flutter_assets/`目录下需要的文件>拷贝到`android/main/assets/`目录下
   然后，将`common_flutter/android/gradle.properties`中的`PACKAGE_MODE`设置为`aar`
   最后，执行 flutter build apk 即可打包aar


