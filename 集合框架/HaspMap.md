## HashMap

### JDK1.7和JDK1.8的变化

1. JDK7中采用数组+链表，JDK8中采用数组+链表/红黑树（默认链表长度大于8时转为树）。
2. JDK7中扩容需要重新计算哈希值和索引位置，JDK8不重新计算哈希值，而是巧妙的采用扩容后的容量进行`&`操作来计算新的索引位置。
3. JDK7采用头插法，JDK8采用尾插法
4. JDK7采用头插法会导致扩容时有可能出现链表成环问题。JDK8则无此问题。
5. JDK7使用`Entry`，JDK8使用`Node`(`node`本质上也是继承`Entry`)

### 哈希冲突

1. 开放地址法

   + 线性探测
   + 再平方探测
   + 伪随机探测

2. 再散列法

3. 链地址法

4. 建立公共溢出区

   建立公共溢出区存储所有哈希冲突的数据。

### 默认值

```java
/**
 * 默认初始容量16(必须是2的幂次方)
 */
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

/**
 * 最大容量，2的30次方
 */
static final int MAXIMUM_CAPACITY = 1 << 30;

/**
 * 默认加载因子，用来计算threshold
 */
static final float DEFAULT_LOAD_FACTOR = 0.75f;

/**
 * 链表转成树的阈值，当桶中链表长度大于8时转成树 
   threshold = capacity * loadFactor
 */
static final int TREEIFY_THRESHOLD = 8;

/**
 * 进行resize操作时，若桶中数量少于6则从树转成链表
 */
static final int UNTREEIFY_THRESHOLD = 6;

/**
 * 桶中结构转化为红黑树对应的table的最小大小

 当需要将解决 hash 冲突的链表转变为红黑树时，
 需要判断下此时数组容量，
 若是由于数组容量太小（小于　MIN_TREEIFY_CAPACITY　）
 导致的 hash 冲突太多，则不进行链表转变为红黑树操作，
 转为利用　resize() 函数对　hashMap 扩容
 */
static final int MIN_TREEIFY_CAPACITY = 64;
/**
 保存Node<K,V>节点的数组
 该表在首次使用时初始化，并根据需要调整大小。 分配时，
 长度始终是2的幂。
 */
transient Node<K,V>[] table;

/**
 * 存放具体元素的集
 */
transient Set<Map.Entry<K,V>> entrySet;

/**
 * 记录 hashMap 当前存储的元素的数量
 */
transient int size;

/**
 * 每次更改map结构的计数器
 */
transient int modCount;

/**
 * 临界值 当实际大小(容量*填充因子)超过临界值时，会进行扩容
 */
int threshold;

/**
 * 负载因子：要调整大小的下一个大小值（容量*加载因子）。
 */
final float loadFactor;
```

### put

事实上，`new HashMap();`完成后，如果没有`put`操作，是不会分配存储空间的。

1. 当桶数组 table 为空时，通过扩容的方式初始化 table
2. 查找要插入的键值对是否已经存在，存在的话根据条件判断是否用新值替换旧值
3. 如果不存在，则将键值对链入链表中，并根据链表长度决定是否将链表转为红黑树
4. 判断键值对数量是否大于阈值，大于的话则进行扩容操作

### 扩容机制

> 在 HashMap 中，桶数组的长度均是2的幂，阈值大小为桶数组长度与负载因子的乘积。当 HashMap 中的键值对数量超过阈值时，进行扩容。

1. 计算新桶数组的容量 newCap 和新阈值 newThr
2. 根据计算出的 newCap 创建新的桶数组，桶数组 table 也是在这里进行初始化的
3. 将键值对节点重新映射到新的桶数组里。如果节点是 TreeNode 类型，则需要拆分红黑树。如果是普通节点，则节点按原顺序进行分组。

总结起来，一共有三种**扩容方式**：

1. 使用默认构造方法初始化HashMap。从前文可以知道HashMap在一开始初始化的时候会返回一个空的table，并且thershold为0。因此第一次扩容的容量为默认值`DEFAULT_INITIAL_CAPACITY`也就是16。同时`threshold = DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR = 12`。
2. 指定初始容量的构造方法初始化`HashMap`。那么从下面源码可以看到初始容量会等于`threshold`，接着`threshold = 当前的容量（threshold） * DEFAULT_LOAD_FACTOR`。
3. HashMap不是第一次扩容。如果`HashMap`已经扩容过的话，那么每次table的容量以及`threshold`量为原有的两倍。



#### 3. 重写对象的Equals方法时，要重写hashCode方法，为什么？跟HashMap有什么关系？

equals与hashcode间的关系:

1. 如果两个对象相同（即用equals比较返回true），那么它们的hashCode值一定要相同；
2. 如果两个对象的hashCode相同，它们并不一定相同(即用equals比较返回false)

因为在 HashMap 的链表结构中遍历判断的时候，特定情况下重写的 equals 方法比较对象是否相等的业务逻辑比较复杂，循环下来更是影响查找效率。所以这里把 hashcode 的判断放在前面，只要 hashcode 不相等就玩儿完，不用再去调用复杂的 equals 了。很多程度地提升 HashMap 的使用效率。

所以重写 hashcode 方法是为了让我们能够正常使用 HashMap 等集合类，因为 HashMap 判断对象是否相等既要比较 hashcode 又要使用 equals 比较。而这样的实现是为了提高 HashMap 的效率。