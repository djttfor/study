### HTTP

#### 架构

![image-20210124153539932](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210124153539932.png)

##### 1.应用层

应用层一般是我们编写的应用程序,决定了向用户提供的应用服务.应用层可以通过系统调用与传输层进行通信.如:FTP,DNS,HTTP.

##### 2.传输层

传输层通过系统调用想应用层提供处于网络连接中的两台计算机之间的数据传输功能.在传输层用两个性质不同的协议:TCP和UDP.

##### 3.网络层

网络层用来处理在网络上流动的数据包,数据包是网络传输的最小数据单位.该层规定了通过怎样的路径(传输路线)到达对方的计算机,并把数据包传输给对方.

##### 4.链路层

链路层用来处理连接网络的硬件部分,包括控制操作系统,硬件设备驱动,NIC(Network Interface Card,网络适配器)以及光纤等物理可见部分.硬件上的范畴均在链路层的作用范围之内.

数据包的封装过程

![image-20210124203234391](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210124203234391.png)

##### 无连接

无连接的含义是限制每次只处理一个请求,服务器处理完客户端的请求并收到客户端的应答后,即断开连接,采用这种方式可以节省传输时间.

##### 无状态

无状态是指协议对于事务处理没有记忆能力.缺少状态意味着如果后续处理需要前面的信息,则它必须重传,这样可能导致每次连接传送的数据量增大.

![image-20210125211146093](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210125211146093.png)

一个Http请求的过程

![image-20210124154857406](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210124154857406.png)

![image-20210124155204625](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210124155204625.png)

![image-20210124155234548](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210124155234548.png)

TCP三次握手

![image-20210124163407049](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210124163407049.png)