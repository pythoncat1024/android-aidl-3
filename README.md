# android-aidl-3
* 介绍
  * aidl 的多`module`的使用案例
  * 使用`aidl`实现多`module`之间进行 文件的拷贝，及进度的回调
  * 使用`aidl`实现多`module`之间进行 文件的下载，及进度的回调
  
  > 下载使用的是`okhttp`库。

* 实现
  * `aidl`实现文件的拷贝，及进度的回调 看`tag v1.0`
  * `aidl`实现文件的下载，及进度的回调 看`tag v2.0`
  
  > 逻辑的实现:`service`所在`module`;调用:`app`所在`module`
