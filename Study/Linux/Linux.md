

## 1.软件安装

确认centos版本

```
cat /etc/redhat-release 
```

安装软件一般有三种方式：rpm 、解压缩、yum在线安装

### 1.jdk

开始工作，创建/usr/local/jdk文件夹，打下载的jdk放在里面。

查看安装过jdk没有

```
rpm -qa | grep + 软件名
#或者
whereis 软件名
```

如果已经安装了，那么输入以下命令卸载

```
rpm -e --nodeps xxx
rpm -e --nodeps java-1.8.0-openjdk-1.8.0.65-3.b17.el7.x86_64
```

安装

```
rpm -ivh xxx.rpm
```

1.编辑/etc/profile

```linux
vim /etc/profile 
```

2.输入以下内容

```
export JAVA_HOME=/usr/local/jdk8/jdk1.8.0_151
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```

3.输入source 命令使配置生效

```
source /etc/profile
```

4.输入java -version，输出以下信息表示配置成功

```
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```

### 2.mysql

官网下载rpm

![image-20210317235126290](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210317235126290.png)

在/usr/local/mysql/下解压

安装mysql5.7所需的依赖

```
yum install libaio

 yum install perl

yum install net-tools
```

在一堆rpm中，使用以下安装

```
rpm -ivh mysql-community-common-5.7.24-1.el7.x86_64.rpm

rpm -ivh mysql-community-libs-5.7.24-1.el7.x86_64.rpm

rpm -ivh mysql-community-client-5.7.24-1.el7.x86_64.rpm

rpm -ivh mysql-community-server-5.7.24-1.el7.x86_64.rpm
```

查看mysql启动状态

```
service mysqld status 
#Active: inactive (dead) 表示未启动

#启动mysql服务
service mysqld start
 service mysqld restart #重启服务，当修改了配置
再次查看状态
 Active: active (running)表示启动成功
```

查看mysql临时密码

```
grep password /var/log/mysqld.log
#用vim 搜索password
```

拿到临时密码，登录mysql，更改密码

```
set password = password("1998");
#如果出现提示密码不符合策略，mysql 5.7可以这么设置
set global validate_password_policy=0;
set global validate_password_length=1;
#要退出当前会话才生效，可以set session
```

开启远程连接

```
 GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'Szfore_68638' WITH GRANT OPTION;
```

完毕！

### 3.nginx

反向代理、动静分离、重写

下载、解压、输入whereis nginx，有就把他删掉

打开nginx目录

```
./configure 

make 

#如果make提示make: *** No rule to make target `build', needed by `default'.  Stop. 输入以下命令 重新./configure
yum -y install make zlib-devel gcc-c++ libtool openssl openssl-devel

make install
#安装成功输入
whereis nginx
#显示安装路径即表示安装成功
nginx: /usr/local/nginx
#打开此路径下的sbin,输入以下启动nginx
./nginx
```

#### 3.1 nginx常用命令

```
#启动
./nginx

#停止
./nginx -s stop

#安全退出
./nginx -s quit

#重新加载配置文件
./nginx -s reload 

#查看nginx进程
ps -aux|grep nginx
```

#### 查看错误日志

```
 cat /var/log/nginx/error.log
```

#### nginx.conf

```c


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;
    
    keepalive_timeout  65;

    upstream mytt1{
        server 192.168.8.129:81;
    }

    server {
        listen       80;
        server_name  www.mytt1.com;

        location / {
            #root   html;
	    proxy_pass http://mytt1;
            index  index.html index.htm;
        }

    
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

    }

}

```

#### 本体配置域名

```
如果想要访问到上面那个www.mytt1.com
1.去买个服务器，注册一个域名。
2.自己本地在C:\Windows\System32\drivers\etc\hosts下加入

你的ip地址 www.mytt1.com
```



### 4.redis

下载最新的6.2.1，需要安装gcc9.1.0（编译至少2小时），

解压

```
tar -zxvf redis-6.2.1.tar.gz
```

编译

```
cd redis-6.2.1
make
```

安装

```
#将redis安装到指定目录
make PREFIX=/usr/local/redis/ install
```

前台启动

```
cd /usr/local/redis/bin
./redis-server
#退出
ctrl+c
```

后台启动

```
#将redis.conf移动到/redis/bin下
cd redis/redis-6.2.1
mv redis.conf ../bin

#开启守护模式
vim redis.conf
将daemonize no 改为yes

指定配置文件启动
./redis-server ./redis-conf
```

redis性能测试

```
./redis-benchmark -h localhost -p 6379 -c 50 -n 10000
-h 指定主机
-p 端口
-c 并发数
-n 连接数
```

远程连接超时问题

```
#确认开放安全组6379，服务器防火墙开放6379端口
#修改redis.conf配置

注释掉 bind ip的配置，设置密码时也要注释掉

设置protect-mode no
重启

#或者设置密码
```

设置密码

```
vim redis.conf
#设置
protect-mode yes
#增加密码
requirepass 123456

#登录方式1
./redis-cli -a 123456
./redis-cli -p6379 -a 123456

#登录方式2
./redis-cli后
auth 123456
```



## 2.常见信息详解

```
dr-xr-x---.  9 root root  4096 Mar 17 15:48 root
drwxr-xr-x  22 root root   620 Mar 17 10:28 run
lrwxrwxrwx   1 root root     8 Aug 17  2017 sbin -> usr/sbin
drwxr-xr-x.  2 root root  4096 Nov  5  2016 srv
dr-xr-xr-x  13 root root     0 Mar 17  2021 sys
drwxrwxrwt. 10 root root  4096 Mar 17 12:21 tmp
drwxr-xr-x. 13 root root  4096 Aug 17  2017 usr
drwxr-xr-x. 19 root root  4096 Mar 17  2021 var
```

### 1.第一个字符的含义

```
d 表示文件夹
- 表示文件夹
l 表示为链接文件
b 表示为装置文件里面的可供存储的接口设备（可随机存取装置）
c 表示为装置文件里面的串行端口设备，例如键盘、鼠标（一次性读取装置）
```

### 2.权限

```
接下来的字符每三个一组，r表示可读，w表示可写，x表示可执行，他们的位置不会改变，-表示没有权限，
前三个属主权限，第二个三个属组权限，最后三个属其他用户权限
```

![image-20210317160232907](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210317160232907.png)

### 3.从属

```
drwxr-xr-x. 19 root root  4096 Mar 17  2021 var
```

上面的第一个root表示该文件属于谁的，第一个root是组名，也就是root组。

修改文件属组

-R表示：递归更改该目录下所有文件

```
chgrn [-R] 属组名 文件名
```

修改文件属主

```
chown [-R] 属主名 文件名
```

修改文件权限

```
chmod [-R] 777 文件名
```

## 3.硬链接与软链接

#### 1.硬链接

 A----B ,假设B是A的硬链接，那么他们两个指向了同一个文件，如果你把它们之中任意一个删了，通过访问另一个也能访问到该文件。

```
ln [硬链接名] [源文件]
ln a1 a
```

#### 2.软链接

类似windows下的快捷方式。

```
ln -s [软链接名] [源文件]
ln -s a2 a
```

## 4.磁盘管理

> 概述

Linux磁盘管理好坏直接关系到整个系统的性能问题。

Linux磁盘管理常用命令为 df、du。

- df ：列出文件系统的整体磁盘使用量
- du：检查磁盘空间使用量



> df

df命令参数功能：检查文件系统的磁盘空间占用情况。可以利用该命令来获取硬盘被占用了多少空间，目前还剩下多少空间等信息。

语法：

```
df [-ahikHTm] [目录或文件名]
```

选项与参数：

- -a ：列出所有的文件系统，包括系统特有的 /proc 等文件系统；
- -k ：以 KBytes 的容量显示各文件系统；
- -m ：以 MBytes 的容量显示各文件系统；
- -h ：以人们较易阅读的 GBytes, MBytes, KBytes 等格式自行显示；
- -H ：以 M=1000K 取代 M=1024K 的进位方式；
- -T ：显示文件系统类型, 连同该 partition 的 filesystem 名称 (例如 ext3) 也列出；
- -i ：不用硬盘容量，而以 inode 的数量来显示

测试：

```
# 将系统内所有的文件系统列出来！
# 在 Linux 底下如果 df 没有加任何选项
# 那么默认会将系统内所有的 (不含特殊内存内的文件系统与 swap) 都以 1 Kbytes 的容量来列出来！
[root@kuangshen /]# df
Filesystem     1K-blocks   Used Available Use% Mounted on
devtmpfs          889100       0    889100   0% /dev
tmpfs             899460     704    898756   1% /dev/shm
tmpfs             899460     496    898964   1% /run
tmpfs             899460       0    899460   0% /sys/fs/cgroup
/dev/vda1       41152812 6586736  32662368  17% /
tmpfs             179896       0    179896   0% /run/user/0
# 将容量结果以易读的容量格式显示出来
[root@kuangshen /]# df -h
Filesystem     Size Used Avail Use% Mounted on
devtmpfs       869M     0 869M   0% /dev
tmpfs           879M 708K 878M   1% /dev/shm
tmpfs           879M 496K 878M   1% /run
tmpfs           879M     0 879M   0% /sys/fs/cgroup
/dev/vda1       40G  6.3G   32G  17% /
tmpfs           176M     0 176M   0% /run/user/0
# 将系统内的所有特殊文件格式及名称都列出来
[root@kuangshen /]# df -aT
Filesystem     Type       1K-blocks   Used Available Use% Mounted on
sysfs         sysfs               0       0         0    - /sys
proc           proc                0       0         0    - /proc
devtmpfs       devtmpfs       889100       0    889100   0% /dev
securityfs     securityfs          0       0         0    - /sys/kernel/security
tmpfs         tmpfs          899460     708    898752   1% /dev/shm
devpts         devpts              0       0         0    - /dev/pts
tmpfs         tmpfs          899460     496    898964   1% /run
tmpfs         tmpfs          899460       0    899460   0% /sys/fs/cgroup
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/systemd
pstore         pstore              0       0         0    - /sys/fs/pstore
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/freezer
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/cpuset
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/hugetlb
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/blkio
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/net_cls,net_prio
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/memory
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/pids
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/cpu,cpuacct
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/devices
cgroup         cgroup              0       0         0    - /sys/fs/cgroup/perf_event
configfs       configfs            0       0         0    - /sys/kernel/config
/dev/vda1     ext4         41152812 6586748  32662356  17% /
systemd-1      -                   -       -         -    - /proc/sys/fs/binfmt_misc
mqueue         mqueue              0       0         0    - /dev/mqueue
debugfs       debugfs             0       0         0    - /sys/kernel/debug
hugetlbfs     hugetlbfs           0       0         0    - /dev/hugepages
tmpfs         tmpfs          179896       0    179896   0% /run/user/0
binfmt_misc   binfmt_misc         0       0         0    - /proc/sys/fs/binfmt_misc
# 将 /etc 底下的可用的磁盘容量以易读的容量格式显示

[root@kuangshen /]# df -h /etc
Filesystem     Size Used Avail Use% Mounted on
/dev/vda1       40G  6.3G   32G  17% /
```



> du

Linux du命令也是查看使用空间的，但是与df命令不同的是Linux du命令是对文件和目录磁盘使用的空间的查看，还是和df命令有一些区别的，这里介绍Linux du命令。

语法：

```
du [-ahskm] 文件或目录名称
```

选项与参数：

- -a ：列出所有的文件与目录容量，因为默认仅统计目录底下的文件量而已。
- -h ：以人们较易读的容量格式 (G/M) 显示；
- -s ：列出总量而已，而不列出每个各别的目录占用容量；
- -S ：不包括子目录下的总计，与 -s 有点差别。
- -k ：以 KBytes 列出容量显示；
- -m ：以 MBytes 列出容量显示；

测试：

```
# 只列出当前目录下的所有文件夹容量（包括隐藏文件夹）:
# 直接输入 du 没有加任何选项时，则 du 会分析当前所在目录的文件与目录所占用的硬盘空间。
[root@kuangshen home]# du
16./redis
8./www/.oracle_jre_usage  # 包括隐藏文件的目录
24./www
48.                        # 这个目录(.)所占用的总量
# 将文件的容量也列出来
[root@kuangshen home]# du -a
4./redis/.bash_profile
4./redis/.bash_logout    
....中间省略....
4./kuangstudy.txt # 有文件的列表了
48.
# 检查根目录底下每个目录所占用的容量
[root@kuangshen home]# du -sm /*
0/bin
146/boot
.....中间省略....
0/proc
.....中间省略....
1/tmp
3026/usr  # 系统初期最大就是他了啦！
513/var
2666/www
```

通配符 * 来代表每个目录。

与 df 不一样的是，du 这个命令其实会直接到文件系统内去搜寻所有的文件数据。

> 磁盘挂载与卸除

根文件系统之外的其他文件要想能够被访问，都必须通过“关联”至根文件系统上的某个目录来实现，此关联操作即为“挂载”，此目录即为“挂载点”,解除此关联关系的过程称之为“卸载”

Linux 的磁盘挂载使用mount命令，卸载使用umount命令。

磁盘挂载语法：

```
mount [-t 文件系统] [-L Label名] [-o 额外选项] [-n] 装置文件名 挂载点
```

测试：

```
# 将 /dev/hdc6 挂载到 /mnt/hdc6 上面！
[root@www ~]# mkdir /mnt/hdc6
[root@www ~]# mount /dev/hdc6 /mnt/hdc6
[root@www ~]# df
Filesystem           1K-blocks     Used Available Use% Mounted on
/dev/hdc6              1976312     42072   1833836   3% /mnt/hdc6
```

磁盘卸载命令 umount 语法：

```
umount [-fn] 装置文件名或挂载点
```

选项与参数：

- -f ：强制卸除！可用在类似网络文件系统 (NFS) 无法读取到的情况下；
- -n ：不升级 /etc/mtab 情况下卸除。

卸载/dev/hdc6

```
[root@www ~]# umount /dev/hdc6
```

## 5.杂项

### 查看云服务器上的tomcat日志

```
tail -f catalina.out
```

### 解决tomcat在服务器上启动慢的原因

在tomcat bin/ catalina.sh 中任意地方加入

```
JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
```

### 网络配置目录：Centos 7

```
cd /etc/sysconfig/network-scripts
```
