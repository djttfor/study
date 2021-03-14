# Map

## 1.map的体系

![image-20210227195520950](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210227195520950.png)

## 2.HashMap

### 1.基本概念

在 JDK1.7 中，HashMap 是由 数组+链表构成的。但是在 JDK1.8 中，HashMap 是由 数组+链表+红黑树构成，新增了红黑树作为底层数据结构，结构变得复杂了，但是效率也变的更高效

HashMap是一种key+value的存储结构，底层是数组和链表。这里的链表是单向链表，存储hashcode，key，value以及下一个元素的引用next。

```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    V value;
    Node<K,V> next;

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public final K getKey()        { return key; }
    public final V getValue()      { return value; }
    public final String toString() { return key + "=" + value; }

    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    public final boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Map.Entry) {
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            if (Objects.equals(key, e.getKey()) &&
                Objects.equals(value, e.getValue()))
                return true;
        }
        return false;
    }
}
```

### 2.常用方法解析

#### 2.1  V Add(K k,V v)

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
```

put方法的过程：

1.如果数组为空，则初始化一个长度为16的链表数组，不为空，则

拿key的hashCode & 数组长度 -1 （这等价与hashCode % 数组长度）获得索引位置，

如果该位置为空，就直接插入，不为空则与该位置的Node对比

如果hash相同，且key是同一个引用或者key.equals为true,会让新的value覆盖旧value，并返回旧值

如果当前节点是树节点，调用树节点的添加方法。

如果前面的equals为false，那么获取Node的next，next为null就插进去，但是如果当前链表的节点数量等于8时会触发树化操作，如果当前数组长度小于64（最小树化容量），那么会触发扩容，数组长度大于64则把链表转化为红黑树。



2.重写hashCode与equals的问题

这里需要注意如果key是一个自定义对象，作为hashMap的key时，要重写hashCode与equals方法
不重写hashCode会导致相同的对象插入的数组的位置不同，而不重写equals会导致本应覆盖的数据却挂到了链表尾部。

3.resize（）扩容问题

什么时候扩容？

1.size大于threshold时。

2.链表转化为红黑树，数组长度小于64时。

怎么扩容？

newCap = oldCap<<1，初始容量16，扩容一次*2,最大容量1<<30,之后不会再扩容；

遍历旧数组

如果Node是链表且只有一个元素，那么重新计算索引位置（newTab[e.hash & (newCap - 1)] = e）

如果是红黑树节点，调用红黑树的split

如果链表上有多个节点，那么如果hash & oldCap ==0，说明元素的索引可以保持在原来的位置

如果hash & oldCap !=0 ，那么新索引=原索引+数组的长度。

JDK7 的扩容需要重新hash，而且链表的数据由于头插法，与原来的数据倒序