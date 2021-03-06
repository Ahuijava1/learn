# 复制

步骤：

1. 在主库上把数据更改记录到二进制日志（Binary Log）中。
2. 从库将主库上的日志复制到自己的中继日志（Relay Log）中。
3. 从库读取中继日志的事件，将其重放到从库数据之上。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200928172017133.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

详细过程：

​	第一步是在主库上记录二进制日志。在每次准备提交事务完成数据更新前，主库将数据更新的事件记录到二进制日志中。MySQL会按事务提交的顺序而非每条语句执行的顺序来记录二进制日志。在记录二进制日志后，主库会告诉存储引擎可以提交事务了。

​	下一步，从库将主库的二进制日志复制到其本地的中继日志中。首先，从库会启动一个工作线程，称为I/O线程，I/O线程跟主库建立一个普通的客户端连接，然后在主库上启动一个特殊的二进制转储（binlog dump）线程，这个线程会读取主库上的二进制日志中的事件。它不会对事件进行轮询。如果该线程追赶上了主库，它将进入休眠状态，直到主库发送信号量通知其有新的事件产生时才会被唤醒，从库I/O线程会将接收到的事件记录到中继日志中。

​	从库的SQL线程执行最后一步，该线程从中继日志中读取事件并在从库中执行，从而实现从库数据的更新。当SQL线程追赶上I/O线程时，中继日志通常已经在系统缓存中，所以中继日志的开销很低。SQL线程执行的事件也可以通过配置选项来决定是否写入其自己的二进制日志中。