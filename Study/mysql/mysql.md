# mysql

## 杂项

### 1.主键自增(auto_increment)的问题

指定自增的列必须有有索引,一张表最多有一列自增

查看自增量

```mysql
show create table t2;-- 查看表创建,最后面接有最新的自增值,也就是插入下一行的自增值,注意插入失败自增量也会增加
show table status; -- 查看数据库中所有表的状态,里面有一行是自增值
alter table t2 engine = 'innodb';-- 修改存储引擎
alter table t2 charset = 'gbk';-- 修改表字符集
```

#### 查看自增量相关 : show VARIABLES like '%auto_increment%'.

| Variable_name            | Value |
| ------------------------ | ----- |
| auto_increment_increment | 1     |
| auto_increment_offset    | 1     |

两个值的含义：

auto_increment_increment：自增值的自增量 (每次增加多少)

auto_increment_offset： 自增值的偏移量 (初始值)

设置了两个值之后，每次自增的量：

**auto_increment_offset + auto_increment_increment * N**的值，其中**N>=0**(N可以看做是第几行),上限受字段类型限制

**如何变更下一次的自增值**

比如下一次的自增值为90,你要把自增值改为80,那先要把>=80的列全部删除,然后执行下面语句

```mysql
alter table t2 auto_increment = 80;
```

### 2 .limit 用法

```mysql
mysql> SELECT * FROM table LIMIT 5,10; // 检索记录行 6-15

//为了检索从某一个偏移量到记录集的结束所有的记录行，可以指定第二个参数为 -1： 
mysql> SELECT * FROM table LIMIT 95,-1; // 检索记录行 96-last.

//如果只给定一个参数，它表示返回最大的记录行数目： 
mysql> SELECT * FROM table LIMIT 5; //检索前 5 个记录行

//换句话说，LIMIT n 等价于 LIMIT 0,n。
```

### 3. 函数的使用

```mysql
#生成0-1之间的小数
RAND()*10 
#返回小于x的整数
floor(x)
#存储过程的创建
delimiter $$$
create procedure zqtest2()
begin
    declare i int default 0;
    declare s int default 0;
    set i=0;
    set s = 0;
    start transaction;
    while i<10 do
    set s=floor(RAND()*1000);
insert into product(product_name, image, price, sale_price, type_id,
                    type_name, flag, create_time, update_time, create_user)
                    values ('衣服','1',s,s*0.9,5,'服装',1,now(),now(),10086);
            set i=i+1;
        end while;
    commit;
end
$$$
delimiter ;
#存储过程的调用
call zqtest2();

#函数的创建
delimiter $$
create function batch_insert(n int) returns int
begin
    declare r int default 0;
    declare i int default 0;
    declare s int default 0;
    set s = 0;
    while i<n do
            set s=floor(RAND()*1000);
            insert into product(product_name, image, price, sale_price, type_id,
                                type_name, flag, create_time, update_time, create_user)
            values ('球鞋','2',s,s*0.9,5,'鞋类',2,now(),now(),10087);
            set i=i+1;
        end while;
    return r;
end $$

#函数的调用
select batch_insert(20);
```

