

#### 1.事务的四大特性(ACID)

##### 1.1 原子性(Atomicity)

**原子性是指事务包含的所有操作要么全部成功，要么全部失败回滚**

##### 1.2 一致性(Consistency)

**事务的执行的前后数据的完整性保持一致.**

　　拿转账来说，假设用户A和用户B两者的钱加起来一共是5000，那么不管A和B之间如何转账，转几次账，事务结束后两个用户的钱相加起来应该还得是5000，这就是事务的一致性。

##### 1.3 隔离性(Isolation)

**一个事务执行的过程中,不应该受到其他事务的干扰**

　隔离性是当多个用户并发访问数据库时，比如操作同一张表时，数据库为每一个用户开启的事务，不能被其他事务的操作所干扰，多个并发事务之间要相互隔离。

　　即要达到这么一种效果：对于任意两个并发的事务T1和T2，在事务T1看来，T2要么在T1开始之前就已经结束，要么在T1结束之后才开始，这样每个事务都感觉不到有其他事务在并发地执行。

##### 1.4 持久性(Durability)

**事务一旦结束,数据就持久到数据库**

持久性是指一个事务一旦被提交了，那么对数据库中的数据的改变就是永久性的，即便是在数据库系统遇到故障的情况下也不会丢失提交事务的操作。

　　例如我们在使用JDBC操作数据库时，在提交事务方法后，提示用户事务操作完成，当我们程序执行完成直到看到提示后，就可以认定事务以及正确提交，即使这时候数据库出现了问题，也必须要将我们的事务完全执行完成，否则就会造成我们看到提示事务处理完毕，但是数据库因为故障而没有执行事务的重大错误

#### 2.数据库事务的隔离级别

数据库事务的隔离级别有4个，由低到高依次为**Read uncommitted、Read committed、Repeatable read、**Serializable**，这四个级别可以逐个解决**脏读、不可重复读、幻读这几类**问题。

![image-20210113093336877](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210113093336877.png)

##### 2.1 Read uncommitted

1.所有事务都可以看到其他未提交事务的执行结果
2.本隔离级别很少用于实际应用，因为它的性能也不比其他级别好多少
3.该级别引发的问题是——脏读(Dirty Read)：读取到了未提交的数据

##### 2.2 Read Committed

1.这是大多数数据库系统的默认隔离级别（但不是MySQL默认的）
2.它满足了隔离的简单定义：一个事务只能看见已经提交事务所做的改变
3.这种隔离级别出现的问题是——不可重复读(Nonrepeatable Read)：不可重复读意味着我们在同一个事务中执行完全相同的select语句时可能看到不一样的结果。
   |——>导致这种情况的原因可能有：(1)有一个交叉的事务有新的commit，导致了数据的改变;(2)一个数据库被多个实例操作时,同一事务的其他实例在该实例处理其间可能会有新的commit

##### 2.3 Repeatable read

(1)这是MySQL的默认事务隔离级别
(2)它确保同一事务的多个实例在并发读取数据时，会看到同样的数据行
(3)此级别可能出现的问题——幻读(Phantom Read)：当用户读取某一范围的数据行时，另一个事务又在该范围内插入了新行，当用户再读取该范围的数据行时，会发现有新的“幻影” 行
(4)InnoDB和Falcon存储引擎通过多版本并发控制(MVCC，Multiversion Concurrency Control)机制解决了该问题

##### 2.4 **Serializable**

(1)这是最高的隔离级别
(2)它通过强制事务排序，使之不可能相互冲突，从而解决幻读问题。简言之,它是在每个读的数据行上加上共享锁。
(3)在这个级别，可能导致大量的超时现象和锁竞争

#### 3.事务并发引起的问题

1、脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据

![image-20210114093656820](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210114093656820.png)

2、不可重复读：事务 A 多次读取同一数据，事务 B 在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取同一数据时，结果 不一致。

事务A在执行读取操作，由整个事务A比较大，前后读取同一条数据需要经历很长的时间 。而在事务A第一次读取数据，比如此时读取了小明的年龄为20岁，事务B执行更改操作，将小明的年龄更改为30岁，此时事务A第二次读取到小明的年龄时，发现其年龄是30岁，和之前的数据不一样了，也就是数据不重复了，系统不可以读取到重复的数据，成为不可重复读。

![image-20210114100516076](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210114100516076.png)

3、幻读：系统管理员A将数据库中所有学生的成绩从具体分数改为ABCDE等级，但是系统管理员B就在这个时候插入了一条具体分数的记录，当系统管理员A改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。

事务A在执行读取操作，需要两次统计数据的总量，前一次查询数据总量后，此时事务B执行了新增数据的操作并提交后，这个时候事务A读取的数据总量和之前统计的不一样，就像产生了幻觉一样，平白无故的多了几条数据，成为幻读。

![image-20210114101314904](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210114101314904.png)

假设有个表user,信息如下

![image-20210114155554650](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210114155554650.png)

![image-20210114155505655](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210114155505655.png)

|                    事务A                    |        事务B        |
| :-----------------------------------------: | :-----------------: |
|                  开启事务                   |                     |
|             查询所有,有5条记录              |                     |
|                                             |      开始事务       |
|                                             | 插入id为6的一条记录 |
|                                             |      提交事务       |
|    再查询还是只有5条记录,没有id为6的记录    |                     |
| 插入id为6的记录,失败,提示已经存在这条记录了 |                     |

事务A觉得莫名其妙,明明没有id为6的记录,然而却插入失败了,这就是幻读.同样的如果事务B删除了一条记录并提交了,
这时候被删除的那条记录就成了幻影,事务A可以查到那条记录,但是实际上它不存在了.

可以通过select * from user for update; 或select * from user lock in share mode;查询最新记录,避免幻读.

小结：不可重复读的和幻读很容易混淆，不可重复读侧重于修改，幻读侧重于新增或删除。解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表.

#### 4.事务操作相关

1.设置事务隔离级别

mysql 通过命令select  @@tx_isolation 查询数据库的隔离级别,默认为Repeatable read; 更新隔离级别的命令如:
set tx_isolation = 'read-uncommitted';相关语法如下

```mysql
SET GLOBAL TRANSACTION ISOLATION LEVEL Repeatable read;#设置全局事务,对正在使用的会话无效,新建的会话才有效
SELECT @@GLOBAL.tx_isolation; #查询全局事务的隔离级别
SET SESSION TRANSACTION ISOLATION LEVEL Repeatable read;#设置会话事务,对正在使用的会话有效,退出会话后无效.
SELECT @@SESSION.tx_isolation; #查询会话事务的隔离级别
#注意上面的设置隔离级别单词之间不用加'-',下面设置要加'-'
set tx_isolation = 'read-uncommitted'; #设置当前会话的隔离级别
select @@tx_isolation; #查询当前会话隔离级别
```

2.等待获取事务锁超时时间

show global  variables like  'innodb_lock_wait_timeout';

set  global innodb_lock_wait_timeout = 10;

show variables like  'innodb_lock_wait_timeout';

set  innodb_lock_wait_timeout = 10;

#### 5.事务的传播行为

- TransactionDefinition.PROPAGATION_REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。

- TransactionDefinition.PROPAGATION_REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起。

- TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。

- TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。

- TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。

- TransactionDefinition.PROPAGATION_MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。

- TransactionDefinition.PROPAGATION_NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。

  ![image-20210419212004435](https://tuchuang-1306293030.cos.ap-guangzhou.myqcloud.com/img/image-20210419212004435.png)

#### 6. @Transactional失效的场景

- @Transactional 应用在非 public 修饰的方法上
- 同一个类中非事务方法调用了事务方法
- 事务的传播机制设置不当
- 异常被catch住，没有抛给spirng或者触发事务回滚的异常设定为非RuntimeException或非Error

#### 7.事务传播机制的测试

>方法A、B是不同Service类的事务方法，在方法A中调用方法B，方法A的传播机制固定与方法B所有的传播机制匹配来进行测试

| Propagation   | 测试结果                                                     |
| ------------- | ------------------------------------------------------------ |
| REQUIRED      | **方法A**全部正常回滚，**方法B**前三个正常回滚，接着的后两个不回滚，**NEVER时**时会直接抛**IllegalTransactionStateException**异常，最后一个正常回滚 |
| SUPPORTS      | **方法A**全部不回滚，**方法B** **MANDATORY**时会抛**IllegalTransactionStateException**异常，其他全部不回滚 |
| MANDATORY     | 无论**方法B**的传播机制如何变化，**方法A**都会直接抛出**IllegalTransactionStateException**异常 |
| REQUIRES_NEW  | **与REQUIRED的结果一致**                                     |
| NOT_SUPPORTED | **与SUPPORTS的结果一致**                                     |
| NEVER         | **与SUPPORTS的结果一致**                                     |
| NESTED        | **与REQUIRED的结果一致**                                     |



#### 8.自定义事务管理器

```java
@Component
public class SelfTransactionManager {

    private TransactionStatus transactionStatus;

    //获取事务源
    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 手动开启事务
     * @return
     */
    public TransactionStatus begin(){
        transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transactionStatus;
    }

    /**
     * 提交事务
     * @param transactionStatus
     */
    public void commit(TransactionStatus transactionStatus){
        dataSourceTransactionManager.commit(transactionStatus);
    }

    /**
     * 回滚事务
     */
    public void rollBack(){
        dataSourceTransactionManager.rollback(transactionStatus);
    }
}
```