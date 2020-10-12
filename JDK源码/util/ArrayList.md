# ArrayList

## 字段

```java
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * Default initial capacity.
     * 默认的初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for empty instances.
     * 用于替换空实例的数组对象
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     * 使用默认容量的数据替换空实例对象
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     * 数据存放处
     * ArrayList的容量就是该array的长度。
     * 当第一个元素加入时，任何具有DEFAULTCAPACITY_EMPTY_ELEMENTDATA空的ArrayList将会被扩容到DEFAULT_CAPACITY个元素
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     * ArrayList存放数据的个数（并不等于elementData.length）
     * @serial
     */
    private int size;

```

这里为什么要***EMPTY_ELEMENTDATA***和***DEFAULTCAPACITY_EMPTY_ELEMENTDATA***呢？它们不都是为空的吗？

## 方法

 **指定默认容量**：若指定默认容量为0，则将elementData=EMPTY_ELEMENTDATA；此后，若add一个新的元素，ArrayList将会扩容为1。

 ```java
public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
}
// 扩容 
int newCapacity = oldCapacity + (oldCapacity >> 1);
 ------
int newCapacity = 0 + (0 >> 1) = 1;
 ```

 **使用默认的构造方法**：第一次add元素的时候，ArrayList会扩容到DEFAULTCAPACITY=10的大小

```java
public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
 }

// 扩容
// 如果elementData是DEFAULTCAPACITY_EMPTY_ELEMENTDATA
if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
    // 返回新容量与DEFAULT_CAPACITY的最大值（第一次添加就要扩容了，当然是DEFAULT_CAPACITY为最大值。
    return Math.max(DEFAULT_CAPACITY, minCapacity);
 }
return minCapacity;
```

使用Collection作为参数，转换为ArrayList

```java
    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayList(Collection<? extends E> c) {
        // Collection类型的数据转换为数组，赋予elementData
        elementData = c.toArray();
        // 如果elementData不为空
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            // Collection的toArray方法是不能返回元素是数组类型的
            if (elementData.getClass() != Object[].class)
                // 这里不太懂，为啥还要复制一次？
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            // 为空的话，赋予EMPTY_ELEMENTDATA给elementData即可
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
```

###  trimToSize

```java
    /** 清理无用的空间
     * Trims the capacity of this <tt>ArrayList</tt> instance to be the
     * list's current size.  An application can use this operation to minimize
     * the storage of an <tt>ArrayList</tt> instance.
     */
    public void trimToSize() {
        // modCount是一个标志，ArrayList修改的时候会使其变化
        modCount++;
        // 清理的前提肯定是size比elementData长度小啊
        if (size < elementData.length) {
            // 如果size为0，也就是此时elementData已经没有元素了，就将EMPTY_ELEMENTDATA赋予给elementData
            // 否则就将elementData中没有存放数据的地方压缩掉
            elementData = (size == 0)
              ? EMPTY_ELEMENTDATA
              : Arrays.copyOf(elementData, size);
        }
    }
```

###  add(E)

```java
/** 添加元素（尾部）
 * Appends the specified element to the end of this list.
 *
 * @param e element to be appended to this list
 * @return <tt>true</tt> (as specified by {@link Collection#add})
 */
public boolean add(E e) {
    // 检查一下容量是否足够
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}

// 检查容量（内部）
private void ensureCapacityInternal(int minCapacity) {
    ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

// 计算容量
private static int calculateCapacity(Object[] elementData, int minCapacity) {
    // 返回新容量与DEFAULT_CAPACITY的最大值（第一次添加就要扩容了，当然是DEFAULT_CAPACITY为最大值。
    // 这里其实就是当没有传入具体容量的时候，第一次默认扩容为默认容量，也就是0,
    // 但是还有一种情况就是，如果传入的是一个集合，那么第一次扩容还是得看集合的长度的
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        return Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    // 直接返回minCapacity
    return minCapacity;
}

// 确认真正的容量
private void ensureExplicitCapacity(int minCapacity) {
    modCount++;
    // overflow-conscious code
    // 因为传进来的是minCapacity = size+1，所以还不一定要扩容呢。
    if (minCapacity - elementData.length > 0)
        // 扩容了
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    // 新容量为旧容量的1.5倍
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    // 如果扩容后还是比minCapacity小，那就直接用minCapacity作为新容量了
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    // 如果新容量比MAX_ARRAY_SIZE还大，MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8
    // 为什么Integer.MAX_VALUE - 8要减去8呢？
    // 因为有些VM需要在数组中保留一些标头字段，要占用一些位置
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        // 看看能不能扩容成Integer.MAX_VALUE
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    // elementData复制一下，扩容
    elementData = Arrays.copyOf(elementData, newCapacity);
}

private static int hugeCapacity(int minCapacity) {
    // 溢出了，变负数了
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    // 返回Integer.MAX_VALUE或者MAX_ARRAY_SIZE
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
    MAX_ARRAY_SIZE;
}

```

看图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200807092203747.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTc5NjI1Nw==,size_16,color_FFFFFF,t_70#pic_center)

### add(i, E)

```java
/** 添加元素 add（特定位置）
 * Inserts the specified element at the specified position in this
 * list. Shifts the element currently at that position (if any) and
 * any subsequent elements to the right (adds one to their indices).
 *
 * @param index index at which the specified element is to be inserted
 * @param element element to be inserted
 * @throws IndexOutOfBoundsException {@inheritDoc}
 */
public void add(int index, E element) {
    // 检查插入位置的合法性
    rangeCheckForAdd(index);
	// 检查容量（内部），前面已经讲过
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    // 复制，将index后面所有的元素往后移动一位
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    // 将index位置设为element
    elementData[index] = element;
    size++;
}

// 检查插入位置的合法性
private void rangeCheckForAdd(int index) {
    if (index > size || index < 0)
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}
```

### addAll

```java
// 在elementData尾部添加c
public boolean addAll(Collection<? extends E> c) {
    Object[] a = c.toArray();
    int numNew = a.length;
    // 同上
    ensureCapacityInternal(size + numNew);  // Increments modCount
    // 在elementData尾部添加
    System.arraycopy(a, 0, elementData, size, numNew);
    size += numNew;
    return numNew != 0;
}

// 在elementData的index位置后添加c
public boolean addAll(int index, Collection<? extends E> c) {
    // 检查index的合法性
    rangeCheckForAdd(index);

    Object[] a = c.toArray();
    int numNew = a.length;
    // 同上
    ensureCapacityInternal(size + numNew);  // Increments modCount
	// numMoved是index距离size的大小（左边为正，右边为负）
    int numMoved = size - index;
    // index在原数组内部
    if (numMoved > 0)
        // 移动复制
        System.arraycopy(elementData, index, elementData, index + numNew,
                         numMoved);
    // 在elementData的index位置后添加
    System.arraycopy(a, 0, elementData, index, numNew);
    size += numNew;
    return numNew != 0;
}

```

### remove

```java
/** 移除特定位置的元素
 * Removes the element at the specified position in this list.
 * Shifts any subsequent elements to the left (subtracts one from their
 * indices).
 *
 * @param index the index of the element to be removed
 * @return the element that was removed from the list
 * @throws IndexOutOfBoundsException {@inheritDoc}
 */
public E remove(int index) {
    // 检查index的合法性
    rangeCheck(index);

    modCount++;
    // 保存旧值
    E oldValue = elementData(index);
	// 需要移动的元素的个数（这里还要减1是因为index是0开始的）
    int numMoved = size - index - 1;
    if (numMoved > 0)
        // index往后的所有元素往前移动一位
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    // 非元素位置null（为了让GC回收该位置的元素，清除引用）
    // jvm用的是引用计数算法或者可达性分析算法进行GC
    elementData[--size] = null; // clear to let GC do its work
 	// 返回旧值（移除的值）
    return oldValue;
}

```

```java
/** 
 * 这里不检查index为负数的情况，数组访问之前就会判断访问下标是否为负数
 * 为负数抛ArrayIndexOutOfBoundsException
 *
 * Checks if the given index is in range.  If not, throws an appropriate
 * runtime exception.  This method does *not* check if the index is
 * negative: It is always used immediately prior to an array access,
 * which throws an ArrayIndexOutOfBoundsException if index is negative.
 */
private void rangeCheck(int index) {
    // 只判断是否越上界
    if (index >= size)
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}
```
```java
/**
 * 移除首个特定元素
 * Removes the first occurrence of the specified element from this list,
 * if it is present.  If the list does not contain the element, it is
 * unchanged.  More formally, removes the element with the lowest index
 * <tt>i</tt> such that
 * <tt>(o==null && get(i)==null && o.equals(get(i)))</tt>
 * (if such an element exists).  Returns <tt>true</tt> if this list
 * contained the specified element (or equivalently, if this list
 * changed as a result of the call).
 *
 * @param o element to be removed from this list, if present
 * @return <tt>true</tt> if this list contained the specified element
 */
public boolean remove(Object o) {
    // 元素也是可以为null的
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                // 快速移除
                fastRemove(index);
                return true;
            }
    } else {
       	// 没什么好讲的了，就是遍历，找第一个值相等的元素，有就true，无就false
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}
```

### fastRemove

```java
/*
 * 该方法和remove方法相比，少了一个检查边界的步骤。其余与remove方法无不同
 * 为什么不用检查边界了呢？ 
 * 答案很明显，该方法是在remove（Object）里面的迭代里面调用的，自然不会出现边界问题
 * Private remove method that skips bounds checking and does not
 * return the value removed.
 */
private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    // 同上
    elementData[--size] = null; // clear to let GC do its work
}
```

### removeRange*

```java
/**
 * 移除特定范围的元素，没有返回值
 * 修饰符为protected，暂不提供给外部使用
 * Removes from this list all of the elements whose index is between
 * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
 * Shifts any succeeding elements to the left (reduces their index).
 * This call shortens the list by {@code (toIndex - fromIndex)} elements.
 * (If {@code toIndex==fromIndex}, this operation has no effect.)
 * 判定是否越界（在哪检查的呢？）
 * @throws IndexOutOfBoundsException if {@code fromIndex} or
 *         {@code toIndex} is out of range
 *         ({@code fromIndex < 0 ||
 *          fromIndex >= size() ||
 *          toIndex > size() ||
 *          toIndex < fromIndex})
 */
protected void removeRange(int fromIndex, int toIndex) {
    modCount++;
    // 需要移动的范围
    int numMoved = size - toIndex;
    // 移动
    System.arraycopy(elementData, toIndex, elementData, fromIndex,
                     numMoved);

    // clear to let GC do its work
    // 其余无用元素全部设置为null
    int newSize = size - (toIndex-fromIndex);
    for (int i = newSize; i < size; i++) {
        elementData[i] = null;
    }
    // 设置新size
    size = newSize;
}
```

### removeAll

```java
/**
 * 在list里面移除collection所包含的全部元素
 * Removes from this list all of its elements that are contained in the
 * specified collection.
 */
public boolean removeAll(Collection<?> c) {
    // 检查c是否是null，若为null，抛NullPointerException
    Objects.requireNonNull(c);
    // 批量移除
    return batchRemove(c, false);
}

Objects.requireNonNull:

    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
```

```java
private boolean batchRemove(Collection<?> c, boolean complement) {
    final Object[] elementData = this.elementData;
    int r = 0, w = 0;
    boolean modified = false;
    try { 
        // 遍历
        for (; r < size; r++)
            // removeAll传来的是complement是false
            // 这里的complement很厉害，如果为false，则说明去除包含的元素
            // 如果为true，说明保留包含的元素（retain）
            // 反正就是，能进入if里面的元素，就能保存
            if (c.contains(elementData[r]) == complement)
                // 从0开始保存目标元素
                elementData[w++] = elementData[r];
    } finally {
        // 这里是是为了兼容AbstractCollection啥啥啥的，不重要。
        // 如果是r!=size，那么说明c.contains抛异常了
        // Preserve behavioral compatibility with AbstractCollection,
        // even if c.contains() throws.
        if (r != size) {
            System.arraycopy(elementData, r,
                             elementData, w,
                             size - r);
            w += size - r;
        }
        // w != size 说明不是所有元素都要清除或者保留
        if (w != size) {
            // 老规矩，置null
            // clear to let GC do its work
            for (int i = w; i < size; i++)
                elementData[i] = null;
            modCount += size - w;
            size = w;
            modified = true;
        }
    }
    return modified;
}
```