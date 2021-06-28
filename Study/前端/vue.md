

## 1.模板字符串

使用``反引号标识，用${}将变量括起来。

```javascript
//JQuery写法
$("#result").append(
        "He is <b>"+person.name+"</b>"+"and we wish to know his"+person.age+".That is all");
//es6写法 省去了双引号与加号，更加简洁
$("#result").append(
        `He is <b>${person.name}</b>and we wish to know his${person.age}.that is all`
        );
//所有的缩进和换行都会保存在输出中

//引用字符串本身
  let str="return"+"`Hello! ${name}`";
    let func=new Function("name",str);
    console.log(func("zzw"));
```

## 2.Vue入门

```javascript
var data = {site:'aaa'}
new Vue({
    el:'#app',
    data:{
        message:"hello world";
    }
});
```

## 2.1常用指令

### 1.v-bind

用于绑定标签属性的值，如：

```html

<div id="d2">
    <p :class="t1">今晚打老虎</p><!--t1='p1' 绑定t1的值，没有冒号在前值就为t1 -->
    <p :class="'p'+t1">今晚打老虎</p><!--t1='1' 正常拼接  -->
    <p v-bind:class="{'p1':use}">今晚打老虎</p> <!-- use为true可启用p1的样式 -->
    <p :class="[t1,t2]">今晚打老虎</p><!-- 绑定多个样式 -->    
    <!-- 根据条件绑定 -->
    <div :class="{ 'active' : isActive, 'text-danger' : hasError }"></div>
     <!-- 内联模式绑定 -->
    <p :style="{color:c1,fontSize:p1+'px'}">今晚打老虎</p>
    <!-- 内联模式绑定2，在methods使用方法返回，return的大括号不能省略 -->
     <p :style="pvss()">今晚打老虎</p>
    pvss(){return {color:this.c1,fontSize:this.p1+'px'};}
    <!-- 内联模式绑定3，在computed计算并返回，return的大括号不能省略 -->
    <p :style="s2">今晚打老虎</p>
    s2(){return {color:this.c1,fontSize:this.p1+'px'}; }
    <p :class="use?t1:''">今晚打老虎</p><!-- 使用三目运算符 -->
</div>
```

### 2.**computed**属性

使用：与一般的vue变量一样用法，编写方式与方法一样，但是方式是实时调用，而computed是计算好放入页面缓存。

```html
<div id="d2">
    <p :class="'p'+t1">今晚打老虎{{returnMessage1}}</p><!--正常用法-->
    <button @click="pvss()">{{m1}}</button><!-- set+get用法 -->
</div>
```

```javascript
var vue = new Vue({
    el: '#app',
    data: {
        message: "hello world",   
    },
    methods:{//方法
      pvss(){
           this.m1 = '点我'
       }
    },
    computed:{//与方法格式一样的编写
        returnMessage1(){
            return this.message+'，computed';
        },
        m1:{//set+get用法
            set:function (nv){
                this.message = nv+'m1额外';
            },
            get:function (){
                return this.message;
            }
        }
    }

});
```

### 3. watch

用于监听属性

```html
<div>
        千米:<input type="number" v-model="kmeter"><i>{{ms}}</i>
        米:<input type="number" v-model="meter">
</div>
```

```javascript
  data: {
         meter:0,
        kmeter:0, 
    },
 methods:{//方法     
        isNumber(val){
            if (parseFloat(val).toString() == "NaN") {
                return false;
            } else {
                return true;
            }
        }
    },
watch :{
    //写法1，监听值:回调函数
    kmeter:function (val) {//监听kmeter，':function'可以省略
       if(this.isNumber(val)){
           this.ms = '';
           this.kmeter = val;
           this.meter = val/1000;
       }else{
           this.ms = '只能接受数字';
       }
    },
    meter(val) {
        this.meter = val;
        this.kmeter = val*1000;
    }
    //写法二,新增一些属性设置后的写法
     demo1: {
            handler:function (nv,ov){//nv:新的值，ov：旧的值
                this.demo1.name = '创建了'
                alert(this.demo1.name)
            },
            deep:true,//监听对象内部变化需要
            immediate:true//为true后会立即执行handler的方法
        }
}
```

```html
<p>{{demo1}}</p><input type="button" value="点我" @click="demo1.name='李四'">
```

```javascript

vue.$watch('demo1',function (nv, ov) {//监听实例写法，写在new Vue()外面
    vue.demo1.name = '创建了'
    alert(vue.demo1.name);
},{deep:true,immediate:true})
```

### 4. v-on

用于在标签里面绑定事件，可简写为@

```html
<!-- 当需要参数的时候需要用$event传递事件-->
<input type="button" :value="b1" @click="cl1($event)">
<!-- 不需要参数，括号都不用写 -->
<input type="button" :value="b1" @click="cl1">
```

```javascript
cl1(event){
   alert(str);
   this.b1++;
   if(event){//事件对象，可获取标签的相关信息
       alert(event.target.tagName);
   }
}
```

#### 4.1 .stop

```html
<div @click="cl2" style="border: 1px solid red">
    <!-- 加上了.stop之后，点击下面的按钮不会触发父类的点击事件-->
    <input type="button" :value="b1" @click.stop="cl1">
</div>
```

#### 4.2.prevent 

```html
 <!-- 阻止a标签跳转-->
<a href="../page/a.html" @click.stop.prevent="b1++">{{b1}}</a>
 <!-- 阻止表单提交-->
<form action="../page/a.html" method="get" @submit.prevent="cl1">
            <input type="text" name="username">
            <input type="text" name="password">
            <input type="submit" value="登录" >  
</form>
 <!-- 阻止表单提交-->
<input type="submit" value="登录" @click.prevent="cl1">
```

#### 4.3 capture

```html
 <!-- 点击3时，2会优先被执行，顺序2》3》1-->
<div @click="cl2" style="border: 1px solid red" >1
    <div @click.capture="cl3">2
        <div @click="cl4">3</div>
    </div>
</div>
```

#### 4.4 .once

```html
 <!-- 事件只会执行一次-->
<div @click.once="cl4">3</div>
```

#### 4.5 .self

```html
 <!-- 只有2被点击时，才会真正触发事件，不被传播触发，点击2时会正常传播事件-->
<div @click.once="cl2" style="border: 1px solid red" >1
    <div @click.self="cl3">2 
        <div @click="cl4">3</div>
    </div>
</div>
```

#### 4.6 .keyup

- `.enter`
- `.tab`
- `.delete` (捕获“删除”和“退格”键)
- `.esc`
- `.space`
- `.up`
- `.down`
- `.left`
- `.right`

```html
<input type="text"  @keyup.left="cl4"> <!-- 在输入框内按方向键左会触发事件-->
```

### 5.v-model

用于双向绑定元素

```html
<!-- 在输入框绑定，会实时监控输入的数据-->
呵呵:<input type="text" v-model="vm1">
 <p style="font-size: 20px;color: red">{{vm1}}</p>
<!-- 与下拉框绑定，绑定的key代表了下拉框选中的value，改变key则改变选中-->
<select v-model="vm1">
                <option value="1">北京</option><!-- 此时添加selected是无效的-->
                <option value="2">上海</option>
                <option value="3">广州</option>
</select>
<p>{{vm1}}</p>
<!-- 单选框绑定，同上-->
<input type="radio" value="man" name="sex" v-model="vm1">男<!-- 添加checked无效-->
<input type="radio" value="woman" name="sex" v-model="vm1">女
<p>{{vm1}}</p>
<!-- 单个复选框绑定，与boolean绑定 选中表示true，未选中表示false-->
<input type="checkbox" v-model="vm1" value="c1">
<!-- 多个复选框绑定数组，checkbox要有value，否则选中一个会全部选中，且值为null
当，数组中存在值跟checkbox的值相同时，则表示checkbox被选中，不存在相同的值时则未被选中
-->
<input type="checkbox" v-model="vm2" value="c1">
<input type="checkbox" v-model="vm2" value="c2">
<input type="checkbox" v-model="vm2" value="c3">
<p>{{vm2}}</p>
<!-- 全选、全不选、反选-->
<input type="checkbox" v-model="c1" value="c1" @click="c11">全选
        <input type="checkbox" v-model="c2" value="c2" @click="vm2.splice(0,vm2.length)">全不选
<input type="checkbox" v-model="c3" value="c3" @click="c31">反选
<ul>
    <li><input type="checkbox" value="1" v-model="vm2"></li>
    <li><input type="checkbox" value="2" v-model="vm2"></li>
    <li><input type="checkbox" value="3" v-model="vm2"></li>
    <li><input type="checkbox" value="4" v-model="vm2"></li>
    <li><input type="checkbox" value="5" v-model="vm2"></li>
</ul>
<p>{{vm2}}</p>
```

```js
c11(){//全选
    for (let i = 0; i < 5; i++) {
        if(this.vm2.indexOf(i+1)===-1){
            this.vm2[i]=i+1+'';
        }
    }
},
c31(){//反选
    for (let i = 0; i < 5; i++) {
        let value = i+1+'';
        let index = this.vm2.indexOf(value);
        if(index===-1){//不存在
            this.vm2.push(value);
        }else{//存在
            this.vm2.splice(index,1);
        }
    }
}
```

#### 5.1 .lazy

```html
<!-- 会在失去焦点时或按回车键时才会更新值-->
<input type="text" v-model.lazy="la1">
```

#### 5.2 .number

```html
<!-- 会自动过滤非数字的东西-->
<input type="text" v-model.number="la1">
```

#### 5.3 .trim

```html
<!-- 会自动过滤前后的空格-->
<input type="text" v-model.trim="la1">
```

## 2.2 判断与循环

```javascript
//判断
<div v-if="enable">
        今晚去哪里玩
    </div>
    <div v-else-if="false">
        v-else-if
    </div>
    <div v-else>
        今晚不去玩
    </div>
//循环，迭代数组
<h1 v-for="p in provinces">
            {{p}}
 </h1>
//迭代数字
 <h1 v-for="p in 10">
            {{p}}
 </h1>
//迭代对象
<h1 v-for="p in user">
            {{p}}
        </h1>
//迭代对象时输出对象的属性名，第一个为value，第二个为属性名，
//遍历的对象为数组时第二个参数为索引
  <h1 v-for="(v,k) in user">
            {{k}}:{{v}}
  </h1>
//第三个为索引
 <h1 v-for="(v,k,i) in user">
            {{k}}:{{v}}:{{i}}
        </h1>
//输出九九乘法表
 <h1 v-for="(v) in 9">
            <b v-for="m in v">
                {{m}}*{{v}}={{m*v}};
            </b>
        </h1>
//数据
var vue = new Vue({
    el: '#app',
    data: {
        message: "hello world",
        enable: false,
        provinces:['aaa','bbb','ccc','ddd'],
        user:
            {'name':'jimmy','password':'123',
                'accounts':['aaa','bbb']} ,
    },
     methods:{//方法
        aaa(){
            alert(`${this.message}`)
        }
    },
    created:function (){//钩子函数
        this.aaa();
    }
});
```

## 2.3 组件

必须写在new Vue的前面，不然报错。

因为组件是可复用的 Vue 实例，所以它们与 `new Vue` 接收相同的选项，例如 `data`、`computed`、`watch`、`methods` 以及生命周期钩子等。仅有的例外是像 `el` 这样根实例特有的选项。

```js
//基础写法
Vue.component('ass', {
    data()  {
        return {count : 0,hehe : 'lisi'}
    },
    template: '<button v-on:click="count++">You clicked me {{ count + hehe }} times.</button>'
});
//与正常的Vue一样的写法，只是data必须是个方法。
Vue.component('ass', {
    data()  {
        return {count : 0,hehe : 'lisi'}
    },
    computed:{
        r1(){
            return '今晚打老虎';
        }
    },
    methods:{
        m1(){
            this.count++;
        }
    },
    watch:{
        count(nv,ov){
            alert(`旧值:${ov},新值:${nv}`)
        }
    },
    props:['title','myName','fuck'],//使用要赋值
    template: '<button @click="m1()">{{count + r1 +title+myName+fuck}}</button>'
});

//Vue组件自定义属性用法
<blog-post
  v-for="post in posts"
  v-bind:key="post.id"
  v-bind:post="post"
></blog-post>
//将post作为attribute接受一个对象
Vue.component('blog-post', {
  props: ['post'],
  template: `
    <div class="blog-post">
      <h3>{{ post.title }}</h3>
      <div v-html="post.content"></div>
    </div>
  `
})
```

#### 1.$emit

作用：通过子组件触发父组件事件

```js
//$emit("FunctionName",[arg1,arg2...]) 或 $emit("FunctionName") 

//自组件
methods:{
        m1(){
            this.count++;
            this.$emit('aaa');//如果需要传参数，直接写在后面
        }
    }
 template: '<button @click="m1">{{count + r1 +title+myName+fuck}}</button>'
//html
<ass title="a" fuck="b" my-name="c" @aaa="cl5"></ass>
//父组件
methods:{
        cl5(){//如果有参数，那么根据上emit传过来的顺序。
            ...
        }
    }
```

##### 1.1 点我变大案例

```html
<div id="app">
    <div :style="{fontSize:fs+'px'}">
        <aaa @bs-text="fs+=1"></aaa>
    </div>
</div>
```

```js
Vue.component('aaa',{
    template:`
    <div>
    <p >fantasy</p>
    <button @click="$emit('bs-text')">点我变大</button>
    </div>
    `
})
new Vue({
   el:'#app' ,
    data:{
       fs:30,
    }
});
```

#### 2.在组件上使用v-model

```html
<input v-model="searchText"><!-- 等价于下面-->
<input
  v-bind:value="searchText"
  v-on:input="searchText = $event.target.value"
>
<!-- 具体实现-->
Vue.component('custom-input', {
  props: ['value'],
  template: `
    <input
      v-bind:value="value"
      v-on:input="$emit('input', $event.target.value)"
    >
  `
})
```

## 2.4 插槽

用于在自定义组件里面插入内容，如下

```html
<div id="app">
    <aaa>dddd</aaa><!--没有定义slot是不允许插入任何东西的-->
</div>
```

```js
Vue.component('aaa',{
    template:`
    <div>
    <slot></slot>
    </div>
   `
})
```

### 1 . 具名插槽

当组件有多个地方需要插槽时，需要对插槽进行命名区分，不命名则默认为default

```js
 Vue.component('aaa',{
        template:`
        <div>
        <header>
        <slot name="header"></slot>
</header>
        <main>
        <slot name="default"></slot>
</main>
        <footer>
        <slot name="footer"></slot>
</footer>
        </div>
       `
    })
```

```html
<aaa><!--使用的时候，需要使用template标签，且指定插槽名称-->
    <template v-slot:header>
            <p>头部</p>
        </template>
        <p >主要内容</p>
        <template v-slot:footer>
            <p>尾部</p>
        </template>
</aaa>
```

### 2. 作用域插槽

插槽内的数据访问，只能访问到父元素，如下：

```html
<aaa>
    <template v-slot:header>
        <p>头部{{user.name}}</p><!--访问的是父级的数据-->
    </template>
</aaa>

<!--首先将要展示的数据当成attribute注入，如下格式-->
<slot name="header" :user="user"></slot>
<!--使用的时候在具名插槽的基础上加个='xxx',名字随意,然后使用如下格式-->
<aaa>
        <template v-slot:header="a">
            <p>头部{{a.user.name}}</p>
        </template>
</aaa>
<!--或者用以下写法-->
<template v-slot:header="{user}">
            <p>头部{{user.name}}</p>
        </template>
<!--或者用以下写法-->
        <template #header="{user:person}"><!--没名字：#default-->
            <p>头部{{user.name+person.name}}</p>
        </template>
```

## 2.5 axios

引入axios.js

```js
<script src="../js/axios.js"></script>
```

#### 1.post

```js
//发送数据，数据拼接在路径后面
axios
    .post(`/ssm/user/login`,u)
    .then(response=>{
        this.message = response.data;
        alert(this.message.code+','+this.message.message);
    }).catch((error)=>{
        alert(error);
})

 axios.post(`/ssm/user/br`,{name:"张三",date:new Date()})
                    .then(response=>{
                        let datas = response.data;
                        alert(datas.name+","+datas.date);
                    }).catch(error=>{
                    alert(error);
                })
```

## Vue-cli 安装

```js
//第一步安装node.js
//第二步在命令行输入加速路径
npm config set registry https://repo.huaweicloud.com/repository/npm/ 
//第三步 安装vue-cli
npm install vue-cli -g 
//安装完之后，查看是否安装成功
vue list
```

## Vue项目创建

```js
//以管理员打开cmd ，创建新Vue项目
vue init webpack myvue
//一路选no
//进入安装好的项目,输入命令，安装环境 ，如果出错提示输入命令即可
npm install
//启动项目
npm run dev
```



## Webpack打包工具

```js
//打包工具安装 
npm install webpack -g
npm install webpack-cli -g
//配置
entry:入口文件，指定WebPack用哪个文件作为项目的入口
output：输出，指定WebPack把处理完成的文件放置到指定位置
module：模块，用于处理各种类型的文件
plugins：插件，如：热更新、代码重用等
resolve：设置路径指向
watch：监听，用于设置文件改动后直接打包
```

### webpack打包demo程序

1.创建文件夹study，用Idea打开

2.在study目录下新建一个module目录，

3.添加hello.js

```js
exports.sayHi= function () {
    document.write('<div style="font-size: 50px">Hello webpack</div>');
};
```

4.添加main.js

```js
var hello = require("./hello");
hello.sayHi();
```

5.在根目录下创建webpack.config.js

```js
module.exports = {
    entry: "./module/main.js",
    output: {
        filename: "./js/bundle.js"
    }
}
```

6.用cmd管理员模式到study文件夹下，输入webpack，就会自动打包

7.webpack -- watch 可实现热部署，要在study文件加下输入。

## Vue-Router

```js
//安装router环境
npm install vue-router --save-dev
//安装axios
npm install --save axios vue-axios
//安装elementUI
npm i element-ui -S
//安装qs
npm install qs --save
```

## Npm命令解释：

```js
//--save-dev 是作为开发依赖保存到 packsge.json 中的 devDependencies 中，即在开发环境中用到的依赖，如 webpack、babel 等用于开发打包的依赖，只是在执行打包时才会用到，开发的代码中并不包含这些依赖

//--save 安装的则是需要在你开发的代码中用到的依赖，如 vue，你需要 import Vue from vue。

npm install moduleName //安装模块到项目目录下

npm install -g moduleName //-g 的意思是将模块安装到全局，具体安装到磁盘的哪个位置，要看npm config prefix 的位置
npm install -save moduleName //-save 的意思是将模块安装到项目目录下，并在package 文件的dependencies节点写入依赖，-S为该命令的缩写
npm install -save-dev moduleName // -save-dev 的意思是将模块安装到项目目录下，并在package文件的devDependencies节点写入依赖，-D为该命令的缩写
```

## Vue工程注册组件

### 1.局部注册

定义三个vue组件，分别为header.vue、myContent.vue、footer.vue, 那么我要在bt.vue里面如何使用呢，如下

```html
<div>
  <Header></Header>
  <MyContent></MyContent>
  <Footer></Footer>
</div>
```

```js
import Header from "./header";
import Footer from "./footer";
import MyContent from "./myContent";
export default {
  name: "bt",
  components: {MyContent, Footer, Header},//局部注册组件，这样就可以在当前页面使用了
}
```

### 2.全局注册

在main.js中，做如下配置,就可以在整个项目中使用这些组件了

```js
import header from "./components/header";
import myContent from "./components/myContent"
import Footer from "./components/footer"
Vue.component('Header',header);
Vue.component('MyContent',myContent);
Vue.component('Footer',Footer);
```

## Vue组件间参数引用

### 1.父传子

```html
 <!-- 首先在子组件上使用props:['xxx']定义属性，然后在父组件上把data里面的数据通过v-bind:xxx="xxx"传递 -->
<div>
  <Header :name="names.h_name"></Header>
  <MyContent :name="names.content_name"></MyContent>
  <Footer :name="names.footer_name"></Footer>
</div>
```

#### 1.1 子类props的写法

```js
<script>
export default {
  name: "header",
  props:['name'],//第一种方式
}
</script>

//第二种方式，type-->对象：object，数组：array
props:{
    name:{type:String,required:true,default:"今晚打老虎"},
    keys:{type:Object,required:false,default:{}}
  }
```

### 2 .子传父

```js
//子组件header
 <button @click="btn">点我</button>
methods:{
    btn(){
      this.$emit("bbb",this.dw,this.dw1);//没有参数就不写
    }
  },
  data(){
    return {
      dw:'我们都有光明的未来',
      dw1:'我们都有美好的明天'
    }
  }
//父组件bt
<Header @bbb="bbb"></Header>
methods:{
    bbb(str,str2){
      this.names.h_name=str;
      this.names.content_name = str2;
    }
  }
```



## Vue解决跨域问题

### 1.什么是跨域问题？

跨域，指的是浏览器不能执行其他网站的脚本。它是由浏览器的同源策略造成的，是浏览器对JavaScript施加的安全限制。

### 2.什么是同源？

所谓同源是指，域名，协议，端口均相同

```
http://www.dj.com/ -> http://admin.dj.com //跨域
http://www.dj.com/ -> http://www.dj.com //f非跨域
http://www.dj.com/ -> http://www.dj.com:8080 //跨域
http://www.dj.com/ -> https://admin.dj.com //跨域
```

### 3. 如何解决跨域问题？

####  使用CORS（跨资源共享）解决跨域问题

CORS是一个W3C标准，全称是跨域资源共享（Cross-origin resource sharing）。它允许浏览器向跨源服务器发出XMLHttpRequest请求，从而克服了AJAX只能同源使用的限制。

CORS需要浏览器和服务器同时支持。目前所有浏览器都支持改功能，IE浏览器不能低于IE10.整个CORS通信过程，都是浏览器自动完成，不需要用户参与。对于开发者来说，CORS通信与同源的AJAX通信没有差别，代码完全一样。浏览器一旦发现AJAX请求跨源，就好自动添加一些附加的头信息，有时还会多出一次附加的请求，但用户不会有感觉。因此，实现CORS通信的关键是服务器。只要服务器实现了CORS接口，就可以跨源通信（在header中设置:“Access=Control-Allow-Origin”,”*”）;

#### 1. ssm使用拦截器配置

```java
public class CrosInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");

        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");

        String method= request.getMethod();

        if (method.equals("OPTIONS")){
            response.setStatus(200);
            return false;
        }

        return true;
    }
}
//springboot的配置
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CrosInterceptor())
                .addPathPatterns("/**");
    }
}
/*
spring-mvc.xml 拦截器配置
<mvc:interceptors>    
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.ex.ssm.interceptor.CrosInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
*/
```

#### 2 .spirng-mvc.xml下配置

```xml
<mvc:cors>
    <mvc:mapping path="/**"
    allowed-origins="*"
    allowed-methods="POST,GET,OPTIONS,DELETE,PUT,PATCH"
    allowed-headers="Access-Control-Allow-Headers,Authorization,X-Requested-With"
    allow-credentials="true"/>
</mvc:cors>
```

 String method= request.getMethod();

```java
//搞一个拦截器加入这段
if (method.equals("OPTIONS")){
        response.setStatus(200);
        return false;
    }
```

## 资源打包

1. 用vue-cl拉取一个项目骨架
2. 安装依赖
3. 使用npm run dev 进入开发者模式

此时在开发者模式中，各种修改都能看到实时效果，这些效果实际上都是有vue-cli打包并发布到node.js上的，开发完成后需要将资源手动部署到在自己的服务器上的。使用 npm run build

图片等静态资源要放在static目录下，访问通过全路径URL访问，如：http://localhost:8085/static/images/1.png

## 定义全局函数

在stc下创建plugin文件夹，创建js文件，加入以下,然后在main.js中导入，Vue.use(你创建的文件)；

```js
export default ({

  install(Vue,options) {
    Vue.prototype.open1 = function (msg) {
      Vue.prototype.$message({
        showClose: true,
        message: msg,
        type: 'warning'
      });
    }
  }
})
//定义全局变量
//创建个vue文件，加入以下
<script>
const BASE_URL = 'http://localhost:8086/ssx/'
export default {
  BASE_URL
}
</script>

//在main.js导入
import ssx_url from '@/myConfig/iurl.vue'
Vue.prototype.ssx_url = ssx_url;
//使用
this.ssx_url.BASE_URLs
```

## Vue element Admin

```
//克隆项目
git clone git@gitee.com:panjiachen/vue-element-admin.git
//进入项目目录
npm install
//不行就搞下面
npm i -g mirror-config-china --registry=https://registry.npm.taobao.org
npm install
//不报错就运行
npm run dev
```

Vue admin template

```
git clone git@gitee.com:panjiachen/vue-admin-template.git
//进入项目目录
npm install
//不报错就运行
npm run dev
```

## export和export default的区别

在ES6中，export与export default均可用于导出常量、函数、文件、模块等，你可以在其它文件或模块中通过`import+(常量 | 函数 | 文件 | 模块)名`的方式，将其导入，以便能够对其进行使用，但在一个文件或模块中，export、import可以有多个，export default仅有一个。

### export的使用

```js
//demo1.js
export const str = 'hello world'
 
export function f(a){
    return a+1
}
```

### export的导入

```js
//demo2.js
import { str, f } from 'demo1' //也可以分开写两次，导入的时候带花括号
```

### export default的使用

```js
//demo3.js
export default const str = 'hello world'
```

### export default的导入

```js
import str from 'demo1' //导入的时候没有花括号
```

## vueX

### 安装

```
npm install vuex --save
```

### 配置

在src下创建store文件夹添加index.js

```js
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store =  new Vuex.Store({
    //想放什么类型写什么类型，
  state:{
    routes:[],
    count:0,
    flag:false
  },
    //监听数值变化
  getters:{
    getCount:state => state.count,
    getFlag:state => state.flag
  },
    //通过this.$store.commit('方法名'，参数)来调用下面的方法
  mutations:{
    initRoutes(state,data){
      state.routes = data;
    },
      //调用方法时不用传state，直接传后面的参数
    countIncrement(state,num){
      state.count+=num;
    },
    countDecrement(){
      this.state.count--;
    },
    reverseFlag(){
      this.state.flag = !this.state.flag;
    }
  },

    //通过这个调用mutations的方法，使用时用this.$store.dispatch('方法名'，参数)来调用
  actions:{
    getAddCount:(context,num)=>{context.commit('countIncrement',num)}
  }
})

export default store
```

### html

```html
  <div>
             <h1>从state上拿到的{{this.$store.state.count}}</h1>
             <h1>从state上拿到的{{this.$store.state.flag}}</h1>
             <h1>从getters上拿到的{{this.$store.getters.getCount}}</h1>
             <h1>从getters上拿到的{{this.$store.getters.getFlag}}</h1>
             <input type="text" v-model="numb"></input>
             <button @click="countIncrement(numb)">+</button><button @click="countDecrement">-</button>
             <router-view/>
           </div>
```

### method

```js
methods:{
    countIncrement(num){
      this.$store.commit('countIncrement',parseInt(num))
      this.reverseFlag()
    },
    countDecrement(){
      this.$store.commit('countDecrement');
    },
    reverseFlag() {
      this.$store.commit('reverseFlag');
    },
    actionTest(num){
      this.$store.dispatch('getAddCount',parseInt(num))
    }
  }
```

### mapActions

mapActions 工具函数会将 store 中的 dispatch 方法映射到组件的 methods 中

vuex中

```js
actions: {
    test1 (){
      console.log('这是mapActions的测试1');
    },
    test2 ({ commit },num){
      console.log(num);
    },
}
```

组件

```js
 methods: {
    ...mapActions(['test1','test2']),
 }
```

调用

```js
 created () {
    this.test1()
    this.test2(2)
  },
```

### mapState

用组件的computed取接收vuex的state值

vuex

```js
 state: {
    test1:'111',
    test2:[],
   }
```

getters.js

```js
const getters = {
  test1: state => state.user.test1,
  test2: state => state.user.test2,
  }
```

组件

```js
computed: mapState({
    test1: state => state.user.test1,
    test2: state => state.user.test2,
  })
```

调用

```js
created () {
    console.log(this.test1)
    console.log(this.test2.length)
  },
```



## 路由守护卫士

```js
//to表示要去的页面
//from表示当前页
//next()是一个钩子，必须要调用，如果值为false如：next(false)，则阻止当前页面跳转，一般放行调用next()即可
router.beforeEach((to, from, next) => {
  
})
```

## ES6奇葩语法

### 1.多定义并赋值

```js
//定义一个对象
let user={
      name:'jimmy',
      password:'123'
    };
//定义变量并赋值
    let{
      name,password,age
    }=user;
//age不在user里面，所以是undefined
 alert(name+password+age);
```

### 2.字段名称取值

```js
 let user={
      name:'jimmy',
      password:'123'
    };
    alert(user['name']);
```

### 3.对象拷贝

```js
let user={
      name:'jimmy',
      password:'123'
    };
    let b={
      age:23
    }
    Object.assign(b,user)//对象拷贝
    let str =''
    Object.keys(b).forEach(v=>{
      str += b[v];
    });
    alert(str)
```

### 4.奇怪的取反

```js
//这里的感叹号是取反的意思，加在字符串上面就变为了boolean，如果是!''那么就是true，那么!!''就是false，花里胡哨
Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true
```

### 5.数组与对象正确的判空姿势

#### 数组

```js
if(this.arr && this.arr.length>0){
      alert("aaa")
    }else{
      alert('bbb')
    }
```

#### 对象

```js
if(this.obj && Object.keys(this.obj).length>0){
      alert("aaa")
    }else{
      alert('bbb')
    }

//使用对象的字段来判断，如果字段不存在也就是undefined
if(this.obj && this.obj.id){
      alert("aaa")
    }else{
      alert('bbb')
    }
```

### 6.async 与await的用法

>async 是一个修饰符，async 定义的函数会默认的返回一个Promise对象resolve的值，因此对async函数可以直接进行then操作,返回的值即为then方法的传入函数
>
>await 关键字 只能放在 async 函数内部， await关键字的作用 就是获取 Promise中返回的内容， 获取的是Promise函数中resolve或者reject的值
>
>如果await 后面并不是一个Promise的返回值，则会按照同步程序返回值处理,为undefined

```js
//用法1，给方法添加async修饰，在调用时直接.then(x=>{x})处理
async t1(){
      return 1;
}
created() {
    this.t1().then(x=>{console.log(x)})
}
//用法2，获取异步方法的返回值
t1(){
      return new Promise(resolve => {
        setTimeout(function(){
          console.log('到点了')
          resolve(4)
        }, 1000)
      })
    },
 async t2(){
      let a = await this.t1()
      console.log(a)
    }
created() {
    this.t2();
  }
```

### 7.Promise的用法

#### 处理返回值，处理错误

```js
//1
t1(){
      let a = 5
      return new Promise((resolve,reject)=>{
        if(a>5){
          resolve(5)
        }else{
          reject(new Error('爬'))//将打印错误
        }
      });
    }
//2,在执行axios方法时，可以处理出现的错误
 logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  }
//3. 第一种处理方式的变种
 t1(){
      let a = 5
      if(a>5){
        return  Promise.resolve(5)
      }else{
        return  Promise.reject(new Error('爬'))
      }
    }
```

#### 方法返回后可做处理

```js
 t1(){
      let a = 5
      return new Promise((resolve, reject)=>{
        if(a>=5){
          resolve(6)
        }else{
          reject('出错了');
        }
      })
    }
//处理
created() {
    this.t1().then(res => {
      alert(res)//结果
    }).catch(err => {
      alert(err)//打印出错了
    })
  }
```

### 8.拓展运算符

三个点（...）真名叫扩展运算符，是在ES6中新增加的内容，它可以在函数调用/数组构造时，将数组表达式或者string在语法层面展开；还可以在构造字面量对象时将对象表达式按照key-value的方式展开

#### 遍历

```js
// 数组
var number = [1,2,3,4,5,6]
console.log(...number) //1 2 3 4 5 6
//对象
var man = {name:'chuichui',height:176}
console.log({...man}) / {name:'chuichui',height:176}
```

#### 合并

```js
//数组的合并
var arr1 = ['hello']
var arr2 =['chuichui']
var mergeArr = [...arr1,...arr2]
mergeArr  // ['hello','chuichui']
// 对象分合并
var obj1 = {name:'chuichui'}
var obj2 = {height:176}
var mergeObj = {...obj1,...obj2}
mergeObj // {name: "chuichui", height: 176}
```

#### 字符串转字符数组

```js
var arr1 = [...'hello']
arr1 // ["h", "e", "l", "l", "o"]
```

#### 函数传参

```js
function f(v,w,x,y,z){ }
var args = [2,3]
f(1,...args,4,...[5])
```



## require的使用

不适用import，动态的导出模块的内容

### 示例

```js
//test.js
const a = ()=>{
  alert('zzzzzzz')
}

const b = 'ccc'

export default {
  a,
  b
}

//使用export，就不用.default
 var m = require('../components/test').default;
     m.a();
     alert(m.b)

```

## 清除npm的缓存

先把node_module文件夹删了

```
npm cache clean --force
```

## 管理员模式CMD下删除文件

```
rmdir /s/q node_modules
```

<p align="center">今天去哪里玩好呢</p>

## js下载文件插件安装

```
npm install js-file-download
```

## SocketJS安装

```
npm install sockjs-client
npm install stompjs
```

