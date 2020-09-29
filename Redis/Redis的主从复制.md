# Redis的主从复制

## 旧版的复制实现

Redis的复制功能分为**同步**（sync）和**命令传播**（command propagate）两个操作。
**同步**：

> 同步操作用于将从服务器的数据库状态更新至主服务器当前所处的数据库状态。

步骤：

1. 从服务器发送sync命令到主服务器。
2. 主服务器收到sync命令后，会执行BGSAVE命令在后台生成一个RDB文件，并使用一个缓冲区记录从现在开始执行的所有写命令。
3. 当主服务器的BGSAVE命令执行完毕时，主服务器会将BGSAVE命令生成的RDB文件发送给从服务器，从服务器接收并载入这个RDB文件，将自己的数据库状态更新至于主服务器执行BGSAVE命令时的数据库状态
4. 主服务器将记录在缓冲区里面的所有写命令发送给从服务器，从服务器执行这些命令，将自己的数据库状态更新主服务器数据库状态当前所处的状态。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200831103757562.png#pic_center)

![主从服务器的同步过程](https://img-blog.csdnimg.cn/20200831110410874.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

 **命令传播**：

> 命令传播操作则用于在主服务器的数据被修改，导致主从服务器的数据库状态出现不一致时，让主从服务器的数据库重新回到一致状态。



新旧版本的差别在于“断线后重复制”的不同

**断线后重复制：**

> 处于命令传播阶段的主从服务器因为网络原因而中断了复制，但从服务器通过自动重新连接又连上了主服务器，并继续复制主服务器。

旧版在断线后，因为主从不同步，所以要先主服务器发送sync命令，重新同步。但是这样的话，之前执行命令传播的工作就没用了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200831111641668.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

**sync是一个很耗时的操作**

1. 主服务器要执行BGSAVE命令来生成RDB文件，这个操作会耗费主服务器大量的CPU、内存和磁盘IO资源。
2. 主服务器要将生成的RDB文件通过网络传输都从服务器上，这个操作会耗费主从服务器大量的网络资源
3. 接收到RDB文件的从服务器需要载入主服务器传来的RDB文件，并且在载入期间，从服务器会因为阻塞而无法处理命令请求。

## 新版的复制实现

redis2.8后添加了**部分重同步**

### 部分重同步

#### 1.复制偏移量

#### 2.复制积压缓冲区

#### 3.服务器运行ID

#### psync

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200831140038642.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

## 复制的实现

#### 1. 步骤一：设置主服务器的地址和端口

命令：

```shell
SLAVEOF <master_ip> <master_port>
```

#### 2. 步骤二：建立套接字连接

从服务器会根据命令的ip和端口号去与主服务器建立连接，创建连向主服务器的套接字连接。连接成功后，从服务器会为这个套接字关联一个专门用于处理复制工作的文件管理事件处理器。

主服务器

***从服务器是主服务器的客户端***

#### 3. 步骤三：发送PING命令

#### 4. 步骤四：身份验证

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200831171400686.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)