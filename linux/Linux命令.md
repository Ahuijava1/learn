

## Linux命令

### 查询文件位置

```shell
find / -name 文件名称

locate 文件名称
```

### vim查找字符串(输入/)

```shell
/ 字符串

? 字符串
使用？可以用n向上查找，N向下查找
```



### linux开启redis

进入目录

```shell
cd /usr/local/redis-5.0.7/src
```

启动

```shell
./redis-server ./redis.conf
```

客户端连接（*）

```shell
./redis-cli

auth 密码

#退出客户端
exit 
```

或者直接：

```shell
./redis-cli -h localhost -p 6379 -a 密码
```

ok



### vim翻页

整页翻页 ctrl-f ctrl-b
f就是forword b就是backward

翻半页
ctrl-d ctlr-u
d=down u=up

滚一行
ctrl-e ctrl-y

zz 让光标所在的行居屏幕中央
zt 让光标所在的行居屏幕最上一行 t=top
zb 让光标所在的行居屏幕最下一行 b=bottom



### TCP

TCP 的连接状态查看，在 Linux 可以通过 `netstat -napt` 命令查看。

## ubantu的信息

![image-20200704234854553](C:\Users\ahui\AppData\Roaming\Typora\typora-user-images\image-20200704234854553.png)

