# Java高级特性

## 1. 反射

## 2. Logger

### Logger的级别：

|         |                      |
| ------- | -------------------- |
| SEVERE  | 严重                 |
| WARNING | 警告                 |
| INFO    | 信息                 |
| CONFIG  | 配置                 |
| FINE    | 良好                 |
| FINER   | 较好                 |
| FINEST  | 最好                 |
| ALL     | 开启所有级别日志记录 |
| OFF     | 关闭所有级别日志记录 |

logger默认的级别是INFO，比INFO更低的日志将不显示。

Logger的默认级别定义是在jre安装目录的lib下面的logging.properties。

## 3. 泛型

> 我们现在可以下结论了，在泛型类被类型擦除的时候，之前泛型类中的类型参数部分如果没有指定上限，如 `<T>` 则会被转译成普通的 Object 类型，如果指定了上限如 `<T extends String>` 则类型参数就被替换成类型上限。

参考文章：[揭开Java 泛型类型擦除神秘面纱](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247486903&idx=2&sn=2a22f3b39ad9474199fcc1b4b28493eb&chksm=eb538881dc240197276892c2247c6240d9b9054e7df9e365e1dece09313ff5aedcc3050bce22&scene=21#wechat_redirect)

## 4. 引用

![image-20200630090013841](C:\Users\ahui\AppData\Roaming\Typora\typora-user-images\image-20200630090013841.png)

​		利用软引用和弱引用解决OOM问题：假如有一个应用需要读取大量的本地图片，如果每次读取图片都从硬盘读取，则会严重影响性能，但是如果全部加载到内存当中，又有可能造成内存溢出，此时使用软引用可以解决这个问题。

​		设计思路是：用一个HashMap来保存图片的路径和相应图片对象关联的软引用之间的映射关系，在内存不足时，JVM会自动回收这些缓存图片对象所占用的空间，从而有效地避免了OOM的问题。

参考文章：[Java基础篇 - 强引用、弱引用、软引用和虚引用](https://blog.csdn.net/baidu_22254181/article/details/81979663)