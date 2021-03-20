# Redis

## Redis 是什么

Redis（Remote Dictionary Server )，即远程字典服务，是一个开源的使用ANSI [C语言](https://baike.baidu.com/item/C语言)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](https://baike.baidu.com/item/数据库/103728)

## Redis能做什么？

1.内存存储、持久化，内存中是断电即逝、所以说持久化很重要（rdb、aof）

2.效率高，可以用与高速缓存

3.发布订阅系统

4.地图信息分析

5.计时器、计数器

## Redis常用命令

#### 1.删除所有key

```
flushall 或 flushdb
```

#### 2.查询所有key

```
keys *
```

#### 3.判断一个key是否存在

```
exists key  #存在返回1，不存在返回0
```

#### 4.给一个key设定过期时间

```
EXPIRE key 10  #最后的参数是多少秒后过期，也可用来更新过期时间
```

#### 5.查看key过期时间

```
ttl key 
#后面是key，当返回值为-2表示过期了
```

#### 6.移动key到某个数据库

```
MOVE key db #后面的db范围是0-15
#select index 可以选择数据库
```

#### 7.判断key的类型

```
type key #返回key的类型
```

#### 8.删除key

```
del [key ...] #删除一个或多个key，返回删除成功的个数
#如：del key1 key2
```

## String

#### 1.设置

```
set key value #如果value有空格，就添加双引号
```

#### 2.获取

```
get key
```

#### 3.获取字符串的长度

```
strlen key #返回长度
```

#### 4.追加

```
append key string #如果key不存在，会创建
```

#### 5.自增

```
incr key #加1
decr key #减1
incrby key increment #相当于+=
decrby key increment #相当于-=
```

#### 6.截取操作

```
#截取，这个包头包尾，如果end是-1，表示倒数第一个
GETRANGE key start end 
#范围替换，将offset位置开始的字符换成value
SETRANGE key offset value
```

#### 7.设置值并设定过期时间

```
setex key seconds value
```

#### 8.设置值如果不存在

```
setnx key value #分布式锁常用
```

#### 9.批量

```
#设置多个值
mset k1 v1 k2 v2 k3 v3 ...
#获取多个值
mget k1 k2 k3
#如果存在一个值，则都不执行
msetnx k1 v1 k4 v4
```

#### 10.先获取再设置

```
getset k v
#如果k存在，那么设置v，不存在返回nil
```

## List

#### 1.增删

```
#获取list长度
LLEN key

#在左边添加元素，返回push后的list长度
LPUSH ls a b c #顺序为c b a
LPUSHX ls a b c #当key不存在时候，不会进行任何操作

#移除并返回左边第一个元素
LPOP key

#在右边添加元素，返回push后的list长度
RPUSH ls a b c #顺序为a b c
RPUSHX ls a b c #key不存在时候，不会进行任何操作

#移除并返回右边第一个元素
RPOP key
```

#### 2.获取

```
#LRANGE key start stop ,-1表示倒数第一，-2表示倒数第二，依次类推，
lrange ls 0 -1 #获取全部
lrange ls 0 4 #获取第一到第五个
lrange ls -4 -1 #倒数第4到倒数第一
```

#### 3.RPOPLPUSH  source des

返回Source被操作的元素，如果source不存在，那么返回nil，des不存在则新建

```
#假设 list1：a,b,c ; list2:1,2,3 执行操作rpoplpush list1 list2后 list1:a,b list2:c,1,2,3
#如果 source与des是同一个list，那么相当于旋转列表
```

#### 4.LINDEX key index

返回key对应索引的值，超过范围返回nil

```
lindex ls -1 #返回最后一个
```

#### 5.LINSERT key BEFORE|AFTER pivot value

把value插到pivot的前面或者后面，如果pivot找不到，则返回-1，如果key不存在，则什么也不会发生。

```
LINSERT ls after b l
```

#### 6.LSET key index value

覆盖对应index 的值为value，设置成功返回OK，超出key范围则返回error

```
lset ls -1 b 
```

#### 7.LTRIM key start stop

保留key 从start 到stop索引之内的值

```
LTRIM ls 1 3
```

#### 8.LREM key count value

从key中移除count个值为value的元素，返回删除的元素的个数

```
count > 0: 从头往尾移除值为 value 的元素。
count < 0: 从尾往头移除值为 value 的元素。
count = 0: 移除所有值为 value 的元素。

lrem ls 1 a #从头往尾移除一个a
lrem ls -1 a #从尾往头移除一个a
lrem ls 0 a #移除所有的a
```

#### 9.阻塞操作

```
# BRPOPLPUSH source destination timeout 与RPOPLPUSH一模一样只不过，source是空的时候，会等待其他人push元素或者达到timeout时限。timeout为0无限等待
#BLPOP与BRPOP比较复杂，请看官网
```

## Hash

相当于map集合

#### 1.hset

```
127.0.0.1:6379> hset user name "" age 18
(integer) 2
```

#### 2.hget

```
127.0.0.1:6379> hget user age
"18"
```

#### 3.hgetall

获取所有字段与值

```
127.0.0.1:6379> HGETALL user
1) "name"
2) ""
3) "age"
4) "18"
5) "sex"
6) ""
7) "address"
8) "bj"
```

#### 4.hlen

获取字段的数量

```
hlen user
```

#### 5.hexists 

返回hash里面field是否存在

```
127.0.0.1:6379> HEXISTS user name
(integer) 1
```

#### 6.hdel

删除指定的字段，可指定多个

```
127.0.0.1:6379> HDEL user sex address
(integer) 2
```

#### 7.hkeys

返回所有的字段

```
127.0.0.1:6379> HKEYS user
1) "name"
2) "age"
```

#### 8.hvals

返回所有的value

```
127.0.0.1:6379> HVALS user
1) ""
2) "18"
```

#### 9.hsetnx

一个字段不存在则创建，存在则操作无效果

```
127.0.0.1:6379> hsetnx user name ddd
(integer) 0
127.0.0.1:6379> hsetnx user sex man
(integer) 1
```

#### 10.hmset

设置多个字段与值

```
127.0.0.1:6379> hmset user e e f f
```

#### 11.hmget

获取多个字段的值

```
hmget user a c e f
```

#### 12.hstrlen

返回指定字段的值的长度

```
127.0.0.1:6379> HSTRLEN user name
(integer) 0
127.0.0.1:6379> HSTRLEN user age
(integer) 2
```

#### 13.hincrby

HINCRBY key field increment，增加 `key` 指定的哈希集中指定字段的数值。如果 `key` 不存在，会创建一个新的哈希集并与 `key` 关联。如果字段不存在，则字段的值在该操作执行前被设置为 0

`HINCRBY` 支持的值的范围限定在 64位 有符号整数

```
127.0.0.1:6379> HINCRBY user age 18
(integer) 36
127.0.0.1:6379> HINCRBY user age 18
(integer) 54
127.0.0.1:6379> HINCRBY user money 1000
(integer) 1000
127.0.0.1:6379> hkeys user
1) "name"
2) "age"
3) "sex"
4) "money"
127.0.0.1:6379> hgetall user
1) "name"
2) ""
3) "age"
4) "54"
5) "sex"
6) "man"
7) "money"
8) "1000"
```

#### 14.hincrbyfloat

为指定`key`的hash的`field`字段值执行float类型的`increment`加。如果`field`不存在，则在执行该操作前设置为0.如果出现下列情况之一，则返回错误：

- `field`的值包含的类型错误(不是字符串)。
- 当前`field`或者`increment`不能解析为一个float类型。

```
127.0.0.1:6379> HINCRBYFLOAT user score 65.8
"65.8"
127.0.0.1:6379> hget user score
"65.8"
127.0.0.1:6379> HINCRBYFLOAT user score 12.335
"78.135"
```

## Set

不可重复

#### 1.SADD 

SADD key member [member ...]，添加元素，元素不能重复

```
sadd iset s1 s2 s3
```

#### 2.SCARD

返回集合元素的数量

```
scard iset
```

#### 3.SREM

SREM key member [member ...]，移除被指定的元素

```
srem iset s1 s2
```

#### 4.SMEMBERS

返回集合的所有元素

```
smembers iset
```

#### 5.SMOVE

SMOVE source destination member,将source的member移动到des里

```
SMOVE iset iset2 s3
```

#### 6.随机

SPOP key [count]，随机返回并删除count个元素

```
spop iset 3
```

SRANDMEMBER key [count] ，随机返回但不删除count个元素

```
SRANDMEMBER iset 1
```

#### 7.差集、交集、补集

1.差集：SDIFF key [key ...]，返回一个集合与给定集合的差集的元素.

```
#注意以第一个集合为基准
sdiff iset iset2
127.0.0.1:6379> SMEMBERS iset2
1) "s3"
2) "s5"
3) "s4"
4) "s1"
127.0.0.1:6379> SMEMBERS iset3
1) "s2"
2) "s5"
3) "s4"
4) "s1"
127.0.0.1:6379> sdiff iset2 iset3
1) "s3"
127.0.0.1:6379> sdiff iset2 iset2
(empty array)
127.0.0.1:6379> sdiff iset3 iset2
1) "s2"
```

2.交集 ：SINSER key [key ...]，，

```
127.0.0.1:6379> SINTER iset2 iset3
1) "s5"
2) "s4"
3) "s1"
```

3.并集 ：SUNION key [key ...]，

```
127.0.0.1:6379> SUNION iset2 iset3
1) "s2"
2) "s5"
3) "s4"
4) "s3"
5) "s1"
```

## Zset

有序集合

#### 1.zadd

添加

```
127.0.0.1:6379> zadd iset 50 s1
(integer) 1
127.0.0.1:6379> zadd iset 50 s1 60 s2 70 s3
(integer) 2
```

#### 2.zcard

返回有序集合的元素个数

```
127.0.0.1:6379> zcard iset
(integer) 3
```

#### 3.zcount

返回指定分数范围的元素个数

```
127.0.0.1:6379> ZCOUNT iset 20 30
(integer) 0
127.0.0.1:6379> ZCOUNT iset 20 80
(integer) 3
```

#### 4.zincrby

为有序集key的成员member的score值加上增量increment

```
127.0.0.1:6379> ZINCRBY iset 10 s10
"20"
```

#### 5.zrange

根据索引遍历有序集合

```
127.0.0.1:6379> ZRANGE iset 0 -1
1) "s1"
2) "s3"
3) "s2"
127.0.0.1:6379> zrange iset 0 -1 withscores
1) "s1"
2) "50"
3) "s3"
4) "70"
5) "s2"
6) "90"
```

#### 6.zrangebyscore

根据分数获取

ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count] ,min和max可以是-inf和+inf，

```
#根据最大最小区间取值
127.0.0.1:6379> ZRANGEBYSCORE iset -inf +inf
1) "s6"
2) "s1"
3) "s2"
4) "s3"
127.0.0.1:6379> ZRANGEBYSCORE iset -inf +inf withscores
1) "s6"
2) "20"
3) "s1"
4) "50"
5) "s2"
6) "90"
7) "s3"
#根据分数区间取值
127.0.0.1:6379> ZRANGEBYSCORE iset 3 5
1) "s3"
2) "s4"
3) "s5"
127.0.0.1:6379> ZRANGEBYSCORE iset 3 5 withscores
1) "s3"
2) "3"
3) "s4"
4) "4"
5) "s5"
6) "5"
# ‘(’代表闭区间也就是说<3,没有‘(’表示开区间即<=3
127.0.0.1:6379> ZRANGEBYSCORE iset (3 5 withscores
1) "s4"
2) "4"
3) "s5"
4) "5"
127.0.0.1:6379> ZRANGEBYSCORE iset (3 (5 withscores
1) "s4"
2) "4"
```

#### 7.zrangebylex

根据member的字符数组Ascii码进行排序，分数必须相同

ZRANGEBYLEX key min max [LIMIT offset count] ,limit用法与mysql两个参数的limit用法一致

```
#查询所有，-表示最小，+表示最大
127.0.0.1:6379> ZRANGEBYLEX iset2 - +
1) "aaa"
2) "aba"
3) "bbb"
4) "ccc"
#获取a<x<z之间,
127.0.0.1:6379> zrangebylex iset2 (a (z
1) "aaa"
2) "aba"
3) "bbb"
4) "ccc"
#获取a<= x <z之间,
127.0.0.1:6379> zrangebylex iset2 [a (z
1) "a"
2) "aaa"
3) "aba"
4) "bbb"
5) "ccc"
```

#### 8.zrem

删除指定的member

```
127.0.0.1:6379> ZREM iset s10
(integer) 1
```

#### 9.zrevrange

跟zrange一样，不过是从大到小进行排序

```
127.0.0.1:6379> ZREVRANGE iset 0 -1
1) "s5"
2) "s4"
3) "s3"
4) "s2"
5) "s1"
```

#### 10.zrevrangebylex

跟zrangebylex一样，不过是从大到小进行排序

```
127.0.0.1:6379> ZREVRANGEBYLEX iset2 + -
1) "ccc"
2) "bbb"
3) "aba"
4) "aaa"
5) "a"
#获取a<= xx <ccd范围的值
127.0.0.1:6379> ZREVRANGEBYLEX iset2 (ccd [a
1) "ccc"
2) "bbb"
3) "aba"
4) "aaa"
5) "a"
```

#### 11.zrevrangebyscore

跟zrangebyscore一样，不过是从大到小进行排序

```
127.0.0.1:6379> ZREVRANGEBYSCORE iset +inf -inf
1) "s5"
2) "s4"
3) "s3"
4) "s2"
5) "s1"
#获取4<=s<5
127.0.0.1:6379> ZREVRANGEBYSCORE iset (5 4
1) "s4"
```

#### 12.zremrangebylex

根据名称的ascii区间删除的，不要在分数不同的有序集合中使用

```
#删除所有
127.0.0.1:6379> ZREMRANGEBYLEX iset2 - +
(integer) 5
#删除a
127.0.0.1:6379> ZREMRANGEBYLEX iset2 [a [a
(integer) 1
#删除a、d之间，不包括a、d
127.0.0.1:6379> zremrangebylex iset2 (a (d
(integer) 4
```

#### 13.zremrangebyscore

根据分数区间删除

```
#删除分数在0<=s<5的
127.0.0.1:6379> ZREMRANGEBYSCORE iset 0 (5
(integer) 4
```

#### 14.zrank

根据member获取排名，从小到大，排名从0开始，也就是获取索引

```
127.0.0.1:6379> zadd iset 1 s1 2 s2 3 s3 4 s4 5 s5
(integer) 4
127.0.0.1:6379> zrank iset s4
(integer) 3
```

#### 15.zrevrank

根据member获取排名，从大到小，排名从0开始，也就是获取索引

```
127.0.0.1:6379> zrange iset 0 -1 withscores
 1) "s1"
 2) "1"
 3) "s2"
 4) "2"
 5) "s3"
 6) "3"
 7) "s4"
 8) "4"
 9) "s5"
10) "5"
127.0.0.1:6379> ZREVrank iset s5
(integer) 0
```

#### 16.zscore

根据member名称获取分数

```
127.0.0.1:6379> ZSCore iset s5
"5"
```

#### 17.ZINTERSTORE

求交集

ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight] [SUM|MIN|MAX]

```
destination:放交集的key
numkeys:需要做交集key的个数
weights：权重，做交集运算时每个集合中的member会把自己的score乘以这个权重，默认为1
aggregate[SUM|MIN|MAX]：对于各个key中的相同元素是求和，取最小，还是取最大，默认为sum。
```

```
#模拟数据z1 、 z2
127.0.0.1:6379> zadd z1 10 a 20 b 30 c 60 d
(integer) 4
127.0.0.1:6379> zadd z2 10 g 20 b 30 c 80 d
(integer) 4
#使用z3保存并集结果，指定进行交集的是z1、z2
127.0.0.1:6379> ZINTERSTORE z3 2 z1 z2 
(integer) 3
#z3中，交集的元素的分数默认是z1与z2分数之和（可以设定取最大还是最小）
127.0.0.1:6379> zrange z3  0 -1 withscores
1) "b"
2) "40"
3) "c"
4) "60"
5) "d"
6) "140"
#取分数最大值
127.0.0.1:6379> ZINTERSTORE z3 2 z1 z2 aggregate max
(integer) 3
127.0.0.1:6379> zrange z3  0 -1 withscores
1) "b"
2) "20"
3) "c"
4) "30"
5) "d"
6) "80"
```

#### 18.ZUNIONSTORE

求并集，参数同17

```
127.0.0.1:6379> ZUNIONSTORE z4 2 z1 z2
(integer) 5
127.0.0.1:6379> zrange z4 0 -1 withscores
 1) "a"
 2) "10"
 3) "g"
 4) "10"
 5) "b"
 6) "40"
 7) "c"
 8) "60"
 9) "d"
10) "140"
```

#### 19.zlexcount

返回指定元素字符范围之内的元素个数，

```
127.0.0.1:6379> zrange z4 0 -1 withscores
 1) "a"
 2) "10"
 3) "g"
 4) "10"
 5) "b"
 6) "40"
 7) "c"
 8) "60"
 9) "d"
10) "140"
127.0.0.1:6379> ZLEXCOUNT z4 [a [c
(integer) 1
```

#### 20.zpopmax

ZPOPMAX key [count]，弹出并返回count个最大的元素

zpopmin与这个一样，不过是弹出并返回最小的

```
127.0.0.1:6379> zrange z4 0 -1 withscores
 1) "a"
 2) "10"
 3) "g"
 4) "10"
 5) "b"
 6) "40"
 7) "c"
 8) "60"
 9) "d"
10) "140"
127.0.0.1:6379> ZPOPMAX z4 2
1) "d"
2) "140"
3) "c"
4) "60"
```

