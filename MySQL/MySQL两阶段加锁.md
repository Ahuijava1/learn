## 两阶段加锁

参考文章：[**MySql-两阶段加锁协议**](https://yq.aliyun.com/articles/626848)

```
2PL,两阶段加锁协议:主要用于单机事务中的一致性与隔离性。
2PC,两阶段提交协议:主要用于分布式事务。
```

### 什么时候会加锁

​	在对记录更新操作或者(select for update、lock in share model)时，会对记录加锁(有共享锁、排它锁、意向锁、gap锁、nextkey锁等等),本文为了简单考虑，不考虑锁的种类。

### 什么是两阶段加锁

> 在一个事务里面，分为加锁(lock)阶段和解锁(unlock)阶段,也即所有的lock操作都在unlock操作之前

### 工程实践中

```txt
在事务中只有提交(commit)或者回滚(rollback)时才是解锁阶段，
其余时间为加锁阶段。
```

### 例子

```mysql
方案1:
begin;
// 扣减库存
update t_inventory set count=count-5 where id=${id} and count >= 5;
// 锁住用户账户表
select * from t_user_account where user_id=123 for update;
// 插入订单记录
insert into t_trans;
commit;
```

```mysql
方案2:
begin;
// 锁住用户账户表
select * from t_user_account where user_id=123 for update;
// 插入订单记录
insert into t_trans;
// 扣减库存
update t_inventory set count=count-5 where id=${id} and count >= 5;
commit;
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020092918083411.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

注意：

```txt
在更新到数据库的那个时间点才算锁成功
提交到数据库的时候才算解锁成功
这两个round_trip的前半段是不会计算在内的
```

<img src="https://img-blog.csdnimg.cn/20200929180937486.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center" alt="在这里插入图片描述" style="zoom: 80%;" />