

1. 虚拟机是否使用TLAB，可以通过**-XX：+/-UseTLAB**参数来 设定。
2. 对象实例数据的存储顺序会受到虚拟机分配策略参数（**-XX：FieldsAllocationStyle**参数）和字段在Java源码中定义顺序的影响。
3. HotSpot虚拟机默认的分配顺序为*longs/doubles、ints、shorts/chars、bytes/booleans、oops（Ordinary Object Pointers，OOPs）*，从以上默认的分配策略中可以看到，相同宽度的字段总是被分配到一起存放，在满足这个前提条件的情况下，在父类中定义的变量会出现在子类之前。如果HotSpot虚拟机的 **+XX：CompactFields**参数值为true（默认就为true），那子类之中较窄的变量也允许插入父类变量的空隙之中，以节省出一点点空间。