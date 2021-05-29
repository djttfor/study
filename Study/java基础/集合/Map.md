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

### put方法的过程：

1.首先计算key的hashCode

2.根据hashCode & 底层数组长度 - 1 获得存放值的下标

3.如果该位置为null，那么new一个Node把值放入然后存入数组，如果不为null，分三种情况，

首先，取出该位置的Node进行比较，

3.1.如果与Node的hashCode相等且key的引用相等或者key的equals相等，那么将新的值覆盖旧的值，并返回被覆盖的值

3.2.如果hashCode不想等，树节点是红黑数节点，调用树节点的添加方法

3.3.如果hashCode不想等, 此节点是链表节点，遍历链表，要么key相等覆盖值，要么就添加到链表的末尾，当链表的长度达到8且数组长度大于64，该节点就转化为树节点，数组长度没到64，只会触发扩容。



### 2.重写hashCode与equals的问题

不重写hashCode的后果：

将实体类作为key，重新new的类就算类的内容相等，但hashCode是不等的，所以会保存到数组的另一个位置而不会覆盖原来key的value

重写hashCode但不重写equals的后果：

还是将实体类作为key，重新new的类就算类的内容相等，不重写的equals还是比较类的引用，会导致不会覆盖原来的值，而是存到链表的尾部



### 3.resize（）扩容问题

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