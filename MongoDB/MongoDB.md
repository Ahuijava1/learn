## MongoDB学习

### 1. 启动服务

[参考文章](https://blog.csdn.net/weixin_42651014/article/details/102391292)

### 2. 基本操作CRUD

#### 2.1 创建、删除数据库

```mysql
# 创建，没有该数据库的时候就会创建它，该数据库存在时进入该数据库
# 刚刚新建数据库时使用show dbs还不能显示，因为还存在内存中，当该数据库创建collection后自动刷新到磁盘
use db_name;

# 删除
db.dropDatabase()
```

> MongoDB 中默认的数据库为 test，如果你没有创建新的数据库，集合将存放在test 数据库中。

![image-20200505104113573](C:\Users\ahui\AppData\Roaming\Typora\typora-user-images\image-20200505104113573.png)



#### 2.2 创建删除集合

##### 2.2.1 显式创建集合

```mysql

```

##### 2.2.2 隐式创建集合

```mysql

```

##### 2.2.3 删除集合

```mysql
db.collection.drop()
```

#### 2.3  文档操作

##### 2.3.1 插入文档

```mysql
#语法
db.colletion_name.insert(document)
```

> 插入文档你也可以使用 db.col.save(document) 命令。如果不指定 _id 字段 save() 方法类似于 insert() 方法。如果指定 _id 字段，则会更新该 _id 的数据。

*也可以将文档定义为一个变量，然后将变量插入*

##### 2.3.2 查看文档

```mysql
#语法
db.colletion_name.find()
```

##### 2.3.3 更新文档

###### 2.3.3.1 update()方法

```mysql
db.collection.update({
    <query>,
    <update>,
    {
    	upsert:<boolean>,
    	multi:<boolean>,
    	writeConcern:<document>
	}
})
```

参数说明：

+ query：update的查询条件，类似sql update查询内where后面的
+ update：update的对象和一些更新的操作符（如$,$inc...）等，也可以理解为sql update查询内set后面的
+ upsert：可选，这个参数的意思是，如果不存在update的记录，是否插入objNew，true为插入，默认是false，不插入
+ multi：可选，mongodb默认是false，只更新找到的第一条记录，如果这个参数为true，就把按条件查询出来多条记录全部更新
+ writeConcern：可选，抛出异常的级别

实例:

```javascript
>db.col.insert({  
	title: 'Mongodb 教程',  
	description: 'MongoDB 是一个 Nosql 数据库',   
	by: 'Mongodb中文网',   
	url: 'http://www.mongodb.org.cn',   
	tags: ['mongodb', 'database', 'NoSQL'],  
	likes: 100  
})

#更新
db.col.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })   # 输出信息  
> db.col.find().pretty()  
{         
	"_id" : ObjectId("56064f89ade2f21f36b03136"),  
	"title" : "MongoDB",   
	"description" : "MongoDB 是一个 Nosql 数据库",  
	"by" : "Mongodb中文网",      
	"url" : "http://www.mongodb.org.cn", 
	"tags" : [   
		"mongodb",   
		"database",   
		"NoSQL"      
	],      
	"likes" : 100  
}  

#如果你要修改多条相同的文档，则需要设置 multi 参数为 true
>db.col.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}},{multi:true})
```

###### 2.3.3.2 save()方法

save() 方法通过传入的文档来替换已有文档。语法格式如下：

```mysql
db.collection.save({
	<document>,
	{
		writeConcern:<document>
	}
})
```

参数说明：

+ document：文档数据
+ writeConcern：可选，抛出异常的级别

###### 2.3.3.3 更多实例

只更新第一条记录：

```shell
db.col.update( { "count" : { $gt : 1 } } , { $set : { "test2" : "OK"} } );
```

全部更新：

```shell
db.col.update( { "count" : { $gt : 3 } } , { $set : { "test2" : "OK"} },false,true );
```

只添加第一条：

```shell
db.col.update( { "count" : { $gt : 4 } } , { $set : { "test5" : "OK"} },true,false );
```

全部添加加进去:

```shell
db.col.update( { "count" : { $gt : 5 } } , { $set : { "test5" : "OK"} },true,true );
```

全部更新：

```shell
db.col.update( { "count" : { $gt : 15 } } , { $inc : { "count" : 1} },false,true );
```

只更新第一条记录：

```shell
db.col.update( { "count" : { $gt : 10 } } , { $inc : { "count" : 1} },false,false );
```

##### 2.3.4 删除文档

```mysql
db.collection.remove({
	<query>,
	{
		justOne:<boolean>,
		writeConcern:<document>
	}
	
})
```

参数说明：

+ query：（可选），删除的文档的条件
+ justOne（可选），如果设为true或者1，则只删除一个文档。默认为false，即全部删除
+ writeConcern：（可选）抛出异常的级别

```shell
#全部删除
db.collection.remove({})
```

##### 2.3.5 查询文档

```shell
#非结构化显示
db.col.find()
#结构化显示
db.col.find().pretty()
#只返回一个结果
db.col.findOne()
```

| 操作       | 格式                     | 范例                                        | RDBMS中的类似语句       |
| :--------- | :----------------------- | :------------------------------------------ | :---------------------- |
| 等于       | `{<key>:`<value>}        | `db.col.find({"by":"菜鸟教程"}).pretty()`   | `where by = '菜鸟教程'` |
| 小于       | `{<key>:{$lt:<value>}}`  | `db.col.find({"likes":{$lt:50}}).pretty()`  | `where likes < 50`      |
| 小于或等于 | `{<key>:{$lte:<value>}}` | `db.col.find({"likes":{$lte:50}}).pretty()` | `where likes <= 50`     |
| 大于       | `{<key>:{$gt:<value>}}`  | `db.col.find({"likes":{$gt:50}}).pretty()`  | `where likes > 50`      |
| 大于或等于 | `{<key>:{$gte:<value>}}` | `db.col.find({"likes":{$gte:50}}).pretty()` | `where likes >= 50`     |
| 不等于     | `{<key>:{$ne:<value>}}`  | `db.col.find({"likes":{$ne:50}}).pretty()`  | `where likes != 50`     |

###### AND

```shell
 >db.col.find({key1:value1, key2:value2}).pretty()  
 
 #for example
 >db.col.find({"by":"菜鸟教程", "title":"MongoDB 教程"}).pretty()
```

###### OR

```shell
db.col.find({$or:[{key1:value1},{key2:value2}]}).pretty()
```

###### AND与OR联合使用

```mysql
# 'where likes>50 AND (by = '菜鸟教程' OR title = 'MongoDB 教程')'
db.col.find({"like":{$gt:50},$or[{"by":"菜鸟教程"},{"title":"MongoDB教程"}]})。pretty()
```

###### BSON Types

| **类型**                | **数字** | **备注**         |
| :---------------------- | :------- | :--------------- |
| Double                  | 1        |                  |
| String                  | 2        |                  |
| Object                  | 3        |                  |
| Array                   | 4        |                  |
| Binary data             | 5        |                  |
| Undefined               | 6        | 已废弃。         |
| Object id               | 7        |                  |
| Boolean                 | 8        |                  |
| Date                    | 9        |                  |
| Null                    | 10       |                  |
| Regular Expression      | 11       |                  |
| DBPointer               | 12       | 已废弃。         |
| JavaScript              | 13       |                  |
| Symbol                  | 14       | 已废弃。         |
| JavaScript (with scope) | 15       |                  |
| 32-bit integer          | 16       |                  |
| Timestamp               | 17       |                  |
| 64-bit integer          | 18       |                  |
| Decimal 128             | 19       | 3.4版本新增      |
| Min key                 | 255      | Query with `-1`. |
| Max key                 | 127      |                  |

```mysql
#获取col集合中title为String的数据
db.col.find({"title":{$type:2}})
```

###### MongoDB Limit() 方法

```mysql
db.col.find().limit(NUMBER)

#for example
#查询2条数据，只显示title
db.col.find({<query>},{"title":1, _id:0}).limit(2)
```

###### MongoDB Skip() 方法

skip()方法参数默认为0

```mysql
db.col.find().limit(NUMBER).skip(NUMBER)

#显示第二条数据的title
db.col.find({}, {"title":1, _id:0}).limit(1).skip(1)
```

###### MongoDB sort()方法

> 在MongoDB中使用使用sort()方法对数据进行排序，sort()方法可以通过参数指定排序的字段，并使用 1 和 -1 来指定排序的方式，其中 1 为升序排列，而-1是用于降序排列。

```mysql
db.col.find().sort({KEY:1})

#查询col集合中的数据按字段likes降序排序
db.col.find({},{"title":1, _id:0}).sort({"like": -1})

```

### 3. 索引

#### 3.1 建立索引

**ensureIndex() 方法**

```mysql
#1为升序，-1为降序
db.col.ensureIndex({KEY:1})

#for example
db.col.ensureIndex({"title":1})
# 多个索引，类似关系型数据库的复合索引
db.col.ensureIndex({"title":1, "count":-1})
```

ensureIndex() 接收可选参数，可选参数列表如下：

| Parameter          | Type          | Description                                                  |
| :----------------- | :------------ | :----------------------------------------------------------- |
| background         | Boolean       | 建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加 "background" 可选参数。 "background" 默认值为**false**。 |
| unique             | Boolean       | 建立的索引是否唯一。指定为true创建唯一索引。默认值为**false**. |
| name               | string        | 索引的名称。如果未指定，MongoDB的通过连接索引的字段名和排序顺序生成一个索引名称。 |
| dropDups           | Boolean       | 在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 **false**. |
| sparse             | Boolean       | 对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 **false**. |
| expireAfterSeconds | integer       | 指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。 |
| v                  | index version | 索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本。 |
| weights            | document      | 索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。 |
| default_language   | string        | 对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语 |
| language_override  | string        | 对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language. |

#### 3.2 索引限制

+ 索引存放在内存中，请确保索引大小不会超过内存大小
+ 如果索引大小大于内存限制，MongoDB会自动删除一些索引
+ 如果很少对集合进行读取操作，不建议使用索引
+ 索引不能被以下的查询使用：
  + 正则表达式及非操作符，如$nin, $not, 等
  + 算术运算符，如$mod，等
  + $where子句
+ 从2.6版本开始，如果现有的索引字段的值超过索引键的限制，MongoDB中不会创建索引
+ 集合中索引不能超过64个
+ 索引名的长度不能超过125个字符
+ 一个复合索引最多可以有31个字段

#### 3.3 高级索引

##### 3.3.1 索引数组字段

假设我们基于标签来检索用户，为此我们需要对集合中的数组 tags 建立索引。

在数组中创建索引，需要对数组中的每个字段依次建立索引。所以在我们为数组 tags 创建索引时，会为 music、cricket、blogs三个值建立单独的索引。

使用以下命令创建数组索引：

```mysql
  >db.users.ensureIndex({"tags":1})  
```

创建索引后，我们可以这样检索集合的 tags 字段：

```mysql
  >db.users.find({tags:"cricket"})  
```

为了验证我们使用使用了索引，可以使用 explain 命令：

```mysql
  >db.users.find({tags:"cricket"}).explain()  
```

以上命令执行结果中会显示 "cursor" : "BtreeCursor tags_1" ，则表示已经使用了索引。

##### 3.3.2 索引子文档字段

假设我们需要通过city、state、pincode字段来检索文档，由于这些字段是子文档的字段，所以我们需要对子文档建立索引。

为子文档的三个字段创建索引，命令如下：

```mysql
  >db.users.ensureIndex({"address.city":1,"address.state":1,"address.pincode":1})  
```

一旦创建索引，我们可以使用子文档的字段来检索数据：

```mysql
  >db.users.find({"address.city":"Los Angeles"})     
```

记住查询表达式必须遵循指定的索引的顺序。所以上面创建的索引将支持以下查询：

```mysql
  >db.users.find({"address.city":"Los Angeles","address.state":"California"})   
```

同样支持以下查询：

```mysql
 >db.users.find({"address.city":"LosAngeles","address.state":"California","address.pincode":"123"})  
```

![image-20200718234249873](C:%5CUsers%5Cahui%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200718234249873.png)

![image-20200718234256773](C:%5CUsers%5Cahui%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200718234256773.png)