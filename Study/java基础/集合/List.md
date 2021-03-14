# List

## 1.继承体系

![image-20210217101939286](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210217101939286.png)

## 2.ArrayList

### 1.基本概念

底层Object数组,如果不指定容量,在第一次添加元素时,初始容量为10,

新容量=旧容量+旧容量>>1,也就是扩容一半,然后把旧数组内容拷贝到新数组中,

### 2.常用方法

#### 2.1  Add(E e)方法(添加元素,成功返回true)

```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
```

添加元素前先确保容量充足,如不足则扩容,扩容方式*:**新容量=旧容量+旧容量>>1,然后把旧数组的内容拷贝到新数组中***,
然后就是**线程不安全问题**:详见3.1,解决方法:加锁,使用 Collections.synchronizedList(List<T>),底层是原来list调用的时候会上锁

#### 2.2 Add(int index,E e)(在指定的索引处添加元素,无返回)

```java
public void add(int index, E element) {
    rangeCheckForAdd(index);

    ensureCapacityInternal(size + 1);  // Increments modCount!!
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
```

,首先有个范围检查,index>size 或者 index<0 都会抛出异常,接着确保容量,然后就把原index以及index往后的元素往后挪size-index个位置.最后在index这个位置插入.

#### 2.3 Set(int index, E e)(在指定的索引处设置元素,设置成功把原来的元素返回)

```java
public E set(int index, E element) {
    rangeCheck(index);

    E oldValue = elementData(index);
    elementData[index] = element;
    return oldValue;
}
```

范围检查设置的index>=size,抛出异常,直接把值设置在index位置,把原来的值返回

#### 2.4 remove(int index) 删除指定索引处的方法,返回被删除的元素

```java
public E remove(int index) {
    rangeCheck(index);

    modCount++;
    E oldValue = elementData(index);

    int numMoved = size - index - 1;//目的是判断是否是头或者尾
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work

    return oldValue;
}
```

不废话

#### 2.5 remove(Object o) 找到指定的元素并删除,成功返回true

```java
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}
```

#### 2.6 indexOf(Object o) 根据元素找到索引

```java
public int indexOf(Object o) {
    if (o == null) {
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    return -1;
}
```

indexOf从数组前往后找,lastIndexOf从后往前找

### 3.需要注意的问题

#### 3.1 线程不安全

体现在add方法上,

```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    /*
    第一种情况:由于size++不是原子性操作,所以会有线程安全问题,假设size=4,线程A先执行size++,没执行完,这时CPU调度到了线程B,线程B执行了size++,此时size=5,随后执行elementData[5] = xxx,这时调度到了线程A,线程A没有发现size的值变成了5,所以执行size++后还是5,最后执行elementData[5] = xxx把线程B的值给覆盖了;
    第二种情况:假设当前容量为10,size为9,线程A与线程B都通过了容量检查,此时线程A执行elementData[9] = xxx,到线程B执行的时候size=10,超出了当前最大索引,抛出java.lang.ArrayIndexOutOfBoundsException
    */
    return true;
}
```

#### 3.2 foreach迭代时增删问题

在ArrayList迭代的时候不能增删元素,会抛出java.util.ConcurrentModificationException,原因是:

**foreach**编译后,实际上是用了迭代器(**ArrayList**的内部类**Itr**)来进行遍历,迭代器在调用**next**和**remove**时检查预期修改(**expectedModCount**)与**ArrayList**的**ModCount**是否一致,预期修改只有在初始化迭代器和调用迭代器的**remove**方法才与**Modcount**保持一致,如果迭代的时候调用了**ArrayList**的**remove**方法,此时就不会保持一致了,在下一次迭代器调用**next**方法的时候会抛出并发修改异常(**ConcurrentModificationException**).增加元素同理.

正确的做法是 不要使用foreach迭代的时候删除元素或者使用**removeIf**方法来进行删除,不过请注意该方法会删除符合条件的所有元素.

#### 3.3 ArrayList去重的问题

```java
public static void removeDuplicate2(String[] s){//使用set集合简单快捷
    Set<String> set = new HashSet<>(Arrays.asList(s));
    for (String s1 : set) {
        System.out.println(s1);
    }
}
//第二种暴力遍历.不使用任何集合框架
 public static void removeDuplicate(String[] s){
        int count =0;
        //遍历算出重复元素的个数
        for (int i = 0; i < s.length - 1; i++) {
            for (int j = i+1; j < s.length; j++) {
                if(s[i].equals(s[j])){
                    count++;
                    break;
                }
            }
        }
        //根据重复元素个数获取去重数组的长度
        String[] s2 = new String[s.length-count];
        //把不重复的元素放入新数组中,具体操作是,遍历旧数组,没提出一个元素,都要到新数组里面看看是否存在,不存在则在新数组里面添加
        boolean flag = true;
        int index = 0;
        for (String s1 : s) {
            for (int i = 0; i < s2.length; i++) {
                if(s1.equals(s2[i])){
                    flag = false;
                    break;
                }
            }
            if(flag){
                s2[index++] = s1;
            }
            flag = true;
        }
        System.out.println(Arrays.toString(s2));
    }
```

#### 3.4 排序问题

使用Collections.sort进行排序,排序的实体需要是Comparable的子类或本身.

```java
public static <T extends Comparable<? super T>> void sort(List<T> list) {
    list.sort(null);
}
```

## 3.LinkedList

### 1.基本概念

底层双向链表,可以从头向后遍历,也可以从尾向头遍历,增删快,查询慢.

```java
private static class Node<E> {//LinkedList的node
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

### 2.常用方法

#### 2.1 add(E e) 添加成功返回true

```java
public boolean add(E e) {
    linkLast(e);
    return true;
}
 void linkLast(E e) {//尾插法
        final Node<E> l = last;//最后一个note
        final Node<E> newNode = new Node<>(l, e, null);//新建node,last节点做前节点,后节点为空
        last = newNode;//last节点重新指向新节点
        if (l == null)//链表为空,即第一次添加
            first = newNode;//令首节点也等于新节点
        else
            l.next = newNode;
        size++;
        modCount++;
    }
```

每次添加元素都会插到尾部

#### 2.2 add(int index,E e) 往指定索引插入元素,无返回

```java
public void add(int index, E element) {
    checkPositionIndex(index);//index >= 0 && index <= size;否则抛出IndexOutOfBoundsException

    if (index == size)
        linkLast(element);
    else
        linkBefore(element, node(index));
    /*
    node(index)获取index位置的node,index < size>>1 从头遍历,反之从尾遍历
    */
    Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
    void linkBefore(E e, Node<E> succ) {//中间插入法
        // assert succ != null;
        final Node<E> pred = succ.prev;//记录前节点
        final Node<E> newNode = new Node<>(pred, e, succ);//newNode的前节点为原节点的前节点,后节点为原节点
        succ.prev = newNode;//原节点的前节点指向新节点
        if (pred == null)//前节点为null,说明succ是首节点,那么要把newNode设为首节点
            first = newNode;
        else
            pred.next = newNode;//前节点的后节点设为newNode
        size++;
        modCount++;
    }
     private void linkFirst(E e) {//头插法
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }
}
```

总结:如果插入的索引等于size,那么执行尾插,否则插到指定索引的地方

#### 2.3 set(int index,E e) 在指定索引插入元素,返回旧元素

```java
public E set(int index, E element) {
    checkElementIndex(index);//index >= 0 && index < size,不在范围抛出越界异常
    Node<E> x = node(index);
    E oldVal = x.item;
    x.item = element;
    return oldVal;
}
```

#### 2.4 remove(int index) 删除指定索引处的元素,返回被删除的元素

```java
public E remove(int index) {
    checkElementIndex(index);
    return unlink(node(index));
}

 E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
        if (prev == null) {//说明是头节点
            first = next;
        } else {
            prev.next = next;//前元素的next指向后元素
            x.prev = null;//prev设为null
        }

        if (next == null) {//说明是尾节点
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        modCount++;
        return element;
    }
```

#### 2.5 remove(Object o) 根据元素遍历整个链表,删除成功返回true

```java
public boolean remove(Object o) {
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null) {
                unlink(x);
                return true;
            }
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
    }
    return false;
}
```

#### 2.6 indexOf(Object o) 找到第一个与查找元素匹配的元素索引,并返回,找不到返回-1

```java
public int indexOf(Object o) {
    int index = 0;
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null)
                return index;
            index++;
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item))
                return index;
            index++;
        }
    }
    return -1;
}
```

lastIndexOf类似,只不过从后往前遍历

peek() 获取头元素但不删除

poll() 获取头元素并删除

pop() 获取头元素并删除

push() 添加元素到头部

offer() 添加元素到头部,返回boolean

### 3.需要注意的地方

LinkedList也是线程不安全的,并发情况下可能add方法会出现

节点相互覆盖

比如：链表

或者

链表接不上等问题：

**（*比如last节点的值为1，last.prev节点的值为0，但是last.prev.next的值却是2，这是非常蛋疼的）***



