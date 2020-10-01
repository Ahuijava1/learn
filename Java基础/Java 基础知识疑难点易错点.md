***Java 基础知识疑难点/易错点***

**关于equals**	

util的Objects

```java
import java.util.Objects;

public static boolean equals(Object a, Object b) {
        // 可以避免空指针异常。如果a==null的话此时a.equals(b)就不会得到执行，避免出现空指针异常。
        return (a == b) || (a != null && a.equals(b));
    }

```

这个是java.util.Objects的源码，用于判断两个变量是否相等。

之前我们说最好使用这种判断字符串是否相等

```java
//推荐这种
"str".equals(str);
//不是这种
str.equals("str");
```

为啥推荐这种呢，因为怕str对象为null，而null调用方法会抛出空指针异常。

但是看看java.util.Objects的equals源码，发现它已经考虑到了全部情况了，真™牛逼。既可以判断对象地址是否相等，又可判断字符是否相等，兼容性爆棚。

注意：null调用静态方法不会抛空指针异常，因为静态方法使用静态绑定。String的equals不是static方法，而java.util.Objects的equals方法是static，原生Object的equals是non-static方法。

```java
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        Demo demo = null;
        demo.main(new String[3]);

    }
}
```

程序可以运行，说明null可以调用静态方法。不过这个程序是个无限递归程序，会出现StackOverflowError哈哈。

注意：null == null 为***true***

**List.asList(array)**

这个返回的是java.util.Arrays的一个内部类，我们获取后不能使用它的修改方法，例如：**`add()`、`remove()`、`clear()，`** 否则抛出*UnsupportedOperationException*

可以使用如下方法实现真正的转换：

```java
// 最简便的方法(推荐)
List list = new ArrayList<>(Arrays.asList("a", "b", "c"));

//使用 Java8 的Stream(推荐)
Integer [] myArray = { 1, 2, 3 };
List myList = Arrays.stream(myArray).collect(Collectors.toList());
//基本类型也可以实现转换（依赖boxed的装箱操作）
int [] myArray2 = { 1, 2, 3 };
List myList = Arrays.stream(myArray2).boxed().collect(Collectors.toList());

//自己动手实现（教育目的）
//JDK1.5+
static <T> List<T> arrayToList(final T[] array) {
  final List<T> l = new ArrayList<T>(array.length);

  for (final T s : array) {
    l.add(s);
  }
    //为啥要用括号 -表达式而已，
  return (l);
}

```



**Conllection.toArray()**

该方法是一个泛型方法：` T[] toArray(T[] a);` 如果`toArray`方法中没有传递任何参数的话返回的是`Object`类型数组。

```java
String [] s= new String[]{
    "dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
};
List<String> list = Arrays.asList(s);
//反转
Collections.reverse(list);
//没有指定类型的话会报错
s=list.toArray(new String[0]);

```

有点奇怪

> 由于JVM优化，`new String[0]`作为`Collection.toArray()`方法的参数现在使用更好，`new String[0]`就是起一个模板的作用，指定了返回数组的类型，0是为了节省空间，因为它只是为了说明返回的类型。
>
> 详见：https://shipilev.net/blog/2016/arrays-wisdom-ancients/



**fail-fast**	

[更多了解]: https://blog.csdn.net/chenssy/article/details/38151189

> 快速失败”也就是fail-fast，它是Java集合的一种错误检测机制。当多个线程对集合进行结构上的改变的操作时，有可能会产生fail-fast机制。记住是有可能，而不是一定。例如：假设存在两个线程（线程1、线程2），线程1通过Iterator在遍历集合A中的元素，在某个时候线程2修改了集合A的结构（是结构上面的修改，而不是简单的修改集合元素的内容），那么这个时候程序就会抛出 ConcurrentModificationException 异常，从而产生fail-fast机制。
>
> 单线程也可能出现这种错误，当你在迭代内部的时候，使用集合类的方法也会出现错误。

想想看，我在迭代的时候，你突然修改了我的数据，那我不得出错啊？那如何修改集合呢？使用迭代器的方法!

```java
List<String > list = new ArrayList<>();
list.add("1");
list.add("2");
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()){
    String item = iterator.next();
    if("1".equals(item)){
        iterator.remove();
    }
}
```

反例：(为啥只有倒数第二个元素移除才不报错呢？存疑)

```java
for(String item : list){
    //只有倒数第二个元素移除才不报错
    if("1".equals(item)){
        list.remove(item);
    }
}
```

