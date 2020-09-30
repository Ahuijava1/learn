## Redis持久化

### RDB持久化

#### 实现

RDB文件保存的是二进制类型的数据。

命令：

`SAVE`：阻塞Redis进程，直至RDB文件生成完成

`BGSAVE`：派生出一个子进程，由子进程复制创建RDB文件

伪代码：

```c
def SAVE():
    # 创建RDB
    rdbSave();

def BGSAVE():
	# 创建子进程
	pid = fork();
	if pid == 0;
		# 子进程创建RDB文件
		rdbSave();
		# 完成后向父进程发送信号
		signal_parent()
    elif pid > 0:
		# 父进程继续处理命令请求，并通过轮询等待子进程的信号
		handle_request_and_wait_signal()
    else
        # 处理出错
        handler_fork_error();
```

因为AOF文件的更新频率通常比RDB文件高，所以默认使用AOF。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200930110629982.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

在执行`SAVE`执行期间，拒绝客户端的所有请求。

在执行`BGSAVE`期间，客户端的`SAVE`和`BGSAVE`命令会被拒绝，避免父进程和子进程竞争资源。

<img src="https://img-blog.csdnimg.cn/20200930111026956.png#pic_center" alt="在这里插入图片描述" style="zoom:80%;" />

#### 间隔保存

服务器默认配置：

```redis
# 900s（15分钟） 修改1次
save 900 1
# 300s（5分钟） 修改10次
save 300 10
# 60s（1分钟） 修改10000次
save 60 10000
```

### AOF持久化

#### 实现

​	AOF是通过保存执行的写命令来记录数据库状态的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020093013585867.png#pic_center)

​	AOF持久化功能可以分为命令追加（append）、文件写入、文件同步（sync）三个步骤。

##### 命令追加

​	当一条命令执行之后，会以协议格式将被执行的写命令追加到服务器状态的`aof_buf`缓冲区的末尾。

##### 文件写入和同步

​	在服务器每次结束一个事件循环之前，它都会调用`flushAppendOnlyFile`函数，考虑是否需要将aof_buf缓冲区中的内容写入和保存到AOF文件里面，这个过程可以用伪代码表示：

```c
def eventLoop()
    while True
        # 处理文件事件，接收命令请求以及发送命令回复
        # 处理命令请求时可能会有新的内容被追加到aof_buf缓冲区中
        processFileEvents()
        # 处理时间事件
        processTimeEvents()
        # 考虑是否将aof_buf中的内容写入和保存到AOF文件中
        flushAppendOnlyFile()
        
```

`appendfsync`默认为`everysec`

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200930140953337.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

​	当距离上次同步时间已经超过1s时，并且此时有写命令，那么先将`aof_buf`中的内容写入到AOF文件中，然后在同步AOF。

### AOF文件载入与数据还原

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200930141447432.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

### AOF重写

`BGREWRITEAOF`

​	为了解决AOF文件体积膨胀的问题，Redis提供了AOF重写的功能。采用一个新的、精炼后的AOF文件代替旧的AOF文件，二者所保存的数据库状态一致。

#### 实现

​	事实上，新的AOF文件不会对现有的AOF文件进行任何读取、分析或者写入操作，这个功能主要是通过读取服务器当前的数据库状态来实现的。

#### 后台重写

​	后台重写是Redis开启一个子进程来执行的。

**如何解决重写时新命令对数据库的修改导致数据不一致？**

​	Redis服务器设置了一个AOF重写缓冲区，这个缓冲区在服务器创建子进程之后开始使用，当Redis服务器执行完一个命令后，它会同时将这个写命令发送给AOF缓冲区和AOF重写缓冲区。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200930142337848.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

​	在子进程执行重写期间，服务器进程需要执行三个工作：

1. 执行客户端发来的命令。
2. 将执行后的写命令追加到AOF缓冲区。
3. 将执行后的写命令追加到AOF重写缓冲区。

当子进程完成AOF重写后，它会向父进程发送一个信号，父进程在接到该信号之后，会调用一个信号处理函数（此时会阻塞其它命令）：

1. 将AOF重写缓冲区中所有的内容写入到新AOF文件中，这时新AOF文件所保存的数据库状态与服务器当前的数据库状态一致
2. 对新的AOF改名，原子地覆盖旧AOF文件。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200930143130982.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)