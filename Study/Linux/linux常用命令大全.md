### 1.切换到app目录 

```
cd app 
```

### 2.切换到上一层目录

```
cd ..  
```

### 3.切换到系统根目录

```
cd /   
```

### 4.切换到用户主目录

```
cd ~   
```

### 5.切换到上一个所在目录

```
cd -   
```

### 6.查看当前目录所有子文件 横着排

```
ls
```

### 7.效果同上,但会显示文件的详细信息 等于ls -l

```
ll    
```

### 8.查看包括隐藏文件在内

```
ls -a
```

### 9.查看所有文件包括详细信息

```
ls -all
```

### 10.查看当前所在的文件位置

```
pwd    
```

###  11.创建文件夹 

```
mkdir  xxx
```

### 12. 删除文件夹 

```
rmdir xxx  只能删除空的文件夹,不能删除文件
```

### 13.创建多级目录

```
mkdir -p aaa/bbb
```

### 14.删除多级目录

```
rmdir -p aaa/bbb/ccc #只能删除空的文件夹
```

### 15.获取该命令的提示,大多数命令都可以获取它的--help

```
mkdir --help 
```

### 16.创建一个空文件

```
touch xxx 
```

### 17.将一个文件的所有信息都罗列出来

```
cat aaa.log
```

### 18.显示一页数据,空格显示下一行,回车显示下一页 ,按q退出

```
more xxx 
```

### 19.通过pgUp和pgDn进行上下翻,每次只能翻一行数据,按q退出

```
less xxx 
```

### 20.查看某文件后10行数据

```
tail -10 xxx #查看尾巴几行
head -10 xxx #查看头几行
```

### 21.动态查看日志文件 ctrl+c退出

```
tail -f xxx  
```

### 22.rm

```
-f 不询问删除文件夹，不能删除文件
-r 递归删除所有文件以及文件夹
-rf 递归不询问删除所有
rm -rf xxx 删除利器
rm -rf / 自杀
```

### 23.cp

```
-a：此选项通常在复制目录时使用，它保留链接、文件属性，并复制目录下的所有内容。其作用等于dpR参数组合。
-d：复制时保留链接。这里所说的链接相当于Windows系统中的快捷方式。
-f：覆盖已经存在的目标文件而不给出提示。
-i：与-f选项相反，在覆盖目标文件之前给出提示，要求用户确认是否覆盖，回答"y"时目标文件将被覆盖。
-p：除复制文件的内容外，还把修改时间和访问权限也复制到新文件中。
-r：若给出的源文件是一个目录文件，此时将复制该目录下所有的子目录和文件。
-l：不复制文件，只是生成链接文件。

cp -r c a  #把c目录下的文件及文件夹拷贝到a目录下
```

### 24.mv

```
mv aaa bbb  #bbb不存在就是重命名，否则就是移动到bbb目录下
```

### 25. tar

```
-c：创建一个新tar文件
-v：显示运行过程的信息
-f：指定文件名
-z：调用gzip压缩命令进行压缩
-t：查看压缩文件的内容
-x：解开tar文件

tar -cvf a.tar ./* 打包当前目录下的所有文件
tar -cvf a.tar a   打包文件夹a

tar -xvf a.tar  解压

tar -zcvf a.tar.gz ./*  打包并压缩当前文件夹下所有文件
tar -zcvf a.tar.gz a 打包并压缩 a 文件夹
tar -zxvf a.tar.gz 解压
```

### 26.echo 

往文件里输入字符串

```
echo "今晚打老虎" >> a
```

### 27. vim

新建或编辑文件

iao进入插入模式

插入模式esc进入命令模式

命令模式:进入底线命令模式

```
:wq保存并退出
:w            - 保存文件，不退出 vim
:w file  -将修改另外保存到 file 中，不退出 vim
:w!          -强制保存，不退出 vim
:wq          -保存文件，退出 vim
:wq!        -强制保存文件，退出 vim
:q            -不保存文件，退出 vim
:q!          -不保存文件，强制退出 vim
:e!          -放弃所有修改，从上次保存文件开始再编辑

dd – 快速删除一行
yy - 复制当前行

命令模式
输入数字+空格，光标向后移，数字+回车，光标向下移
v ：按字符复制，上下左右选择，y复制，p粘贴
V ：按行复制，
Ctrl+V 按块复制

1. 命令模式下，输入：/字符串
比如搜索user, 输入/user
按下回车之后，可以看到vim已经把光标移动到该字符处和高亮了匹配的字符串
2. 查看下一个匹配，按下n(小写n)
3. 跳转到上一个匹配，按下N（大写N）
 命令模式下，输入:nohlsearch  也可以:set nohlsearch； 当然，可以简写，noh或者set noh。
 
 
 vim命令替换操作
替换当前行第一个 vivian为sky
:s/vivian/sky/

替换当前行所有 vivian为sky
:s/vivian/sky/g

替换第 n 行开始到最后一行中，每一行的第一个vivian为sky
:n,$s/vivian/sky/

替换第 n 行开始到最后一行中，每一行所有vivian为sky
n为数字，若n为.，表示从当前行开始到最后一行
:n,$s/vivian/sky/g

替换每一行的第一个vivian为sky(等同于 :g/vivian/s//sky/)
:%s/vivian/sky/

替换每一行中所有 vivian为sky(等同于 :g/vivian/s//sky/g)
:%s/vivian/sky/g
```

### 28.用户管理

```
cat /etc/passwd #查看用户信息
```



#### 1.增加用户

```
useradd -选项 用户名
-c comment 指定一段注释性描述。

-d 目录 指定用户主目录，如果此目录不存在，则同时使用-m选项，可以创建主目录。

-g 用户组 指定用户所属的用户组。

-G 用户组，用户组 指定用户所属的附加组。

-m　使用者目录如不存在则自动建立。

-s Shell文件 指定用户的登录Shell。

-u 用户号 指定用户的用户号，如果同时有-o选项，则可以重复使用其他用户的标识号。

用户名 :
指定新账号的登录名。

useradd -m van
```

#### 2.删除用户

```
userdel -r van
```

#### 3.修改用户

```
usermod -d /home/233 van #实为修改home下的文件
```

#### 4.切换用户

```
su 用户名
```

#### 5.用户密码的管理

```
passwd 选项 用户名
-l 锁定口令，即禁用账号。

-u 口令解锁。

-d 使账号无口令。

-f 强迫用户下次登录时修改口令。

如果是超级用户，输入passwd van
可以为van用户修改密码
```

### 29.用户组管理

每个用户都有一个用户组，系统可以对一个用户组中的所有用户进行集中管理。不同Linux 系统对用户组的规定有所不同，如Linux下的用户属于与它同名的用户组，这个用户组在创建用户时同时创建。

用户组的管理涉及用户组的添加、删除和修改。组的增加、删除和修改实际上就是对/etc/group文件的更新。

```
groupadd 选项 用户组
-g GID 指定新用户组的组标识号（GID）。
-o 一般与-g选项同时使用，表示新用户组的GID可以与系统已有用户组的GID相同。

#新增用户组
groupadd group1
 
#删除用户组
groupdel group1

#修改用户组
groupmod 选项 用户组
-g GID 为用户组指定新的组标识号。
-o 与-g选项同时使用，用户组的新GID可以与系统已有用户组的GID相同。
-n新用户组 将用户组的名字改为新名字
# 此命令将组group2的组标识号修改为102。
groupmod -g 102 group2

# 将组group2的标识号改为10000，组名修改为group3。
groupmod –g 10000 -n group3 group2

```

### 30.进程管理

1.在linux中，每一个程序都是有自己的一个进程，每一个进程都有一个id号。

2.每一个进程都有一个父进程。

3.进程可以有两种存在方式：前台，后台（守护进程）

4.一般的服务都是后台运行的，基本的程序都是前台进行的。

```
ps  #查看当前系统中正在执行的各种进程信息

-a 显示当前终端运行的所有进程信息
-u 以用户的信息显示进程
-x 显示后台运行进程的参数

| 竖杠在linux叫做管道
grep 查看文件中符合条件的字符串
#查看java相关的进程信息
ps -aux | grep java

ps -ef | grep java #查看父进程
#看进程树
pstree -au 
#查看java的进程树
pstree -au | grep java

#杀死进程
kill -9 PID
```

### 31.防火墙设置

#### centOS7

查看防火墙状态

```
systemctl status firewalld
```

开启防火墙

```
systemctl start firewalld
```

关闭防火墙

```
systemctl stop firewalld
```

重启防火墙

```
systemctl restart firewalld.service
```

查看开放的端口

```
firewall-cmd --list-ports
```

开放端口

```
firewall-cmd --permanent --zone=public --add-port=3306/tcp
```

关闭端口

```
firewall-cmd --permanent --zone=public --remove-port=8082/tcp
```

检查端口被哪个进程占用

```
netstat -lnp|grep 8080
```

查看绑定在该端口的tcp连接

```
netstat -talnp 3306
```

防火墙开机自启

```
systemctl enable firewalld
```

防火墙关闭开机自启

```
systemctl disable firewalld
```



#### centOS6

开放端口

```
/sbin/iptables -I INPUT -p tcp --dport 80 -j ACCEPT   写入修改
```

保持修改

    /etc/init.d/iptables save   保存修改

重启防火墙


    service iptables restart    重启防火墙，修改生效
查看端口状态

```
/etc/init.d/iptables status
```



### 32.find

.按照文件名查找

```
　　　　(1)find / -name httpd.conf　　#在根目录下查找文件httpd.conf，表示在整个硬盘查找
　　　　(2)find /etc -name httpd.conf　　#在/etc目录下文件httpd.conf
　　　　(3)find /etc -name '*srm*'　　#使用通配符*(0或者任意多个)。表示在/etc目录下查找文件名中含有字符串‘srm’的文件
　　　　(4)find . -name 'srm*' 　　#表示当前目录下查找文件名开头是字符串‘srm’的文件
```

### 33.curl

用于调试nginx ，tomcat是否启动成功

```bash
curl ip:端口
#示例
curl localhost:3344
#如果提示命令没找到，输入以下命令
yum update -y 
yum install curl -y
```

### 34.查看内存占用

```
free -m
```

### 35.查看CPU占用

```
top  #按q退出
```

### 36.查看进程id连接的外网IP

```
netstat -anptl pid
```

