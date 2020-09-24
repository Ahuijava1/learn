volatile语义：

1. 保证该变量对所有线程都可见
2. 禁止指令重排序优化

DCL（Double Check Lock）

```java
public class Singleton { 
    private volatile static Singleton instance; 
    public static Singleton getInstance() { 
        if (instance == null) { 
            synchronized (Singleton.class) { 
                if (instance == null) { 
                    instance = new Singleton(); 
                } 
            } 
        }return instance; 
    }public static void main(String[] args) { 
        Singleton.getInstance(); 
    } 
}
```



#### 先行发生原则（happens before）

> 先行发生是Java内存模型中定义的两项操作之间的偏序关系，比如说操作A先行发生于操作B，其实就是说在发生操作B之前，操作A产生的影响能被操作B观察到，“影响”包括修改了内存中共享变量的值、发送了消息、调用了方法等。

*它是判断数据是否存在竞争，线程是否安全的非常有用的手段*

“天然的”先行发生关系:

1. **程序次序规则**: 在一个线程内，按照控制流顺序，书写在前面的操作先行发生于书写在后面的操作。注意，这里说的是控制流顺序而不是程序代码顺序，因为要考虑分支、循环等结构。 
2. **管程锁定规则**: 一个unlock操作先行发生于后面对同一个锁的lock操作。这里必须强调的是“同一个锁”，而“后面”是指时间上的先后。
3. **volatile变量规则**：对一个volatile变量的写操作先行发生于后面对这个变量的读操作，这里的“后面”同样是指时间上的先后。
4. **线程启动规则**（Thread Start Rule）：Thread对象的start()方法先行发生于此线程的每一个动作
5. **线程终止规则**（Thread Termination Rule）：线程中的所有操作都先行发生于对此线程的终止检测，我们可以通过Thread::join()方法是否结束、Thread::isAlive()的返回值等手段检测线程是否已经终止执行。
6. **线程中断规则**（Thread Interruption Rule）：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过Thread::interrupted()方法检测到是否有中断发生。
7. **对象终结规则**（Finalizer Rule）：一个对象的初始化完成（构造函数执行结束）先行发生于它的finalize()方法的开始。
8. **传递性**（Transitivity）：如果操作A先行发生于操作B，操作B先行发生于操作C，那就可以得出操作A先行发生于操作C的结论。 

#### 线程的状态

1. 新建（New）：创建后尚未启动的线程处于这种状态
2. 运行（Runnable）：包括操作系统线程状态中的Running和Ready，也就是处于此状态的线程有可能正在执行，也有可能正在等待着操作系统为它分配执行时间。
3. 无限期等待（Waiting）：处于这种状态的线程不会被分配处理器执行时间，它们要等待被其他线程显式唤醒。
   1. 没有设置Timeout参数的Object::wait()方法； 
   2. 没有设置Timeout参数的Thread::join()方法
   3. LockSupport::park()方法
4. **限期等待**（Timed Waiting）：处于这种状态的线程也不会被分配处理器执行时间，不过无须等待被其他线程显式唤醒，在一定时间之后它们会由系统自动唤醒。
   1. Thread::sleep()方法
   2. 设置了Timeout参数的Object::wait()方法；
   3. 设置了Timeout参数的Thread::join()方法
   4. LockSupport::parkNanos()方法
   5. LockSupport::parkUntil()方法
5. **阻塞**（Blocked）：线程被阻塞了，“阻塞状态”与“等待状态”的区别是“阻塞状态”在等待着获取到一个排它锁，这个事件将在另外一个线程放弃这个锁的时候发生；而“等待状态”则是在等待一段时间，或者唤醒动作的发生。在程序等待进入同步区域的时候，线程将进入这种状态。
6. **结束**（Terminated）：已终止线程的线程状态，线程已经结束执行

![image-20200427205857620](C:\Users\ahui\AppData\Roaming\Typora\typora-user-images\image-20200427205857620.png)

### 线程安全的实现方法

1. 互斥同步
2. 非阻塞同步（乐观锁，，，）
3. 无同步方案



可重入锁**ReentrantLock**：

1. 等待可中断
2. 可实现公平锁
3. 锁可以绑定多个条件

![image-20200427220119542](C:\Users\ahui\AppData\Roaming\Typora\typora-user-images\image-20200427220119542.png)

