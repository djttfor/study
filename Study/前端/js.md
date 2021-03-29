引入js文件

<script src="xxx.js"></script>

严格检查模式：在js文件或在script标签里声明 ’use strict’,必须写在第一行。

## 1.数组的方法

indexOf（var）同string的indexOf，找不到返回-1

slice（）同string 的substring（）

splice （） 参数1 ：删除和的添加的起始位置,参数2：删除元素的个数，参数3：要添加的元素

push（）压入到尾部

pop（）弹出尾部的一个元素

unshift（）压入到头部

shift（）弹出头部的一个元素

sort（arr）正序排序

reverse（arr）反转

concat（arr）接受一个数组拼接在后面并返回新的数组，旧数组不影响

join（string）把各个元素用传入的字符拼接起来

## 2.对象

动态的删减属性：delete user.name ;删除成功返回true

动态的添加：user.address = xxx ;

判断属性是否存在这个对象中 ： ‘name’ in user ; 

遍历对象：key：Object.keys(obj), values: Object.values(obj).

## 3.循环

```javascript
for (let i = 0; i < arr.length; i++) {//1
    console.log(arr[i]);
}
for (let arrKey in arr) {//2.遍历索引，不是遍历值
    console.log(arrKey);
}
for (let number of arr) {//3
    console.log(number);
}
arr.forEach((k,v)=>{//第一个是值，第二个是索引
    alert(k+v);
});
```

## 4.常用对象

判断类型：typeof 123  --- ‘number’

字符串操作：str.replace(/-/g,"") //1-2-3  ,把- 换成“”

### 4.1 时间相关：

let now = new Date();

getFullYear() 年

getMonth() 月 0-11

getDate() 日

getDay() 星期几 ，返回数字

getMinutes() 分

getSeconds() 秒

getMilliseconds()毫秒

getTime() 时间戳

### 4.2 JSON

var user = {name:'33',age:5,address:'北京'};

对象转JSON

var a = JSON.stringify(user);

JSON转对象

JSON.parse('{"name":"33","age":5,"address":"北京"}');

### 4.3 event

```html
<input type="checkbox" v-model="c1" value="c1" @click="c11($event)">全选
<!-- 
可以通过target属性获取value，id，等等attribute的值
 c11(event){
           if(event){
               alert(event.target.value)
           }
}
-->
```



## 5.BOM操作

bom: Browser Object Model 浏览器对象模型

window 代表了浏览器窗口

navigator 封装了浏览器的信息

screen 代表屏幕尺寸

location 代表当前页面的URL信息

document 代表当前的页面

history ：history.back()退回之前的页面，history.forword()前进

## 6.DOM操作

dom：document Object model 文档对象模型

获取节点

```html
<div id="eyebrows">
    <div class="login-tab" >
        <a href="javascript:void(0)">扫码登录</a>
    </div>
    <div class="login-tab" >
        <a href="javascript:void(0)">账户登录</a>
    </div>
</div>
```

```javascript
//获取eyebrows节点
var a = document.getElementById('eyebrows');
//获取第一个login-tab节点
var b = document.getElementsByClassName('login-tab')[0] //获取所有className为login-tab
//或
var c = a.children[0]//注意children获取的都是数组
//获取最里面的a标签
var d = c.children[0]//切记这个children是数组
```

更新节点

```javascript
d.innerText = '就是不登陆';//纯文本输出
d.innerHTML = '<a>sss</a>';//会解析标签

a.style.color = 'yellow';//各种样式都可以通过style设置
```

删除节点

找要删除的节点c找到父节点p，然后调用p.removeChild(c)，必须通过父类删除。

新增节点

```javascript
var para = document.createElement("p"); //创建一个p节点
var node = document.createTextNode("这是一个新的段落。"); //创建文本节点
para.appendChild(node);  //填充到p节点
var element = document.getElementById("div1"); 
element.appendChild(para);//将p节点添加到div1节点
```

设置元素显示和隐藏

```javascript
lm.setAttribute('hidden','');
lm.removeAttribute('hidden');
```

### 6.1 Jquery

公式：$(selector).action()

```javascript
$(function (){
    $('#eyebrows div:first-child>a').click(function () {
        alert($(this).css("fontSize"));
    })
        .css("fontSize","30px");

});
$(function(){});//入口函数
 $('#eyebrows div:first-child>a').click(function () {
        alert($(this).css("fontSize"));
 })//绑定点击事件
//获取style属性
$(this).css("fontSize")
//设置节点属性
$('#eyebrows').prop("id")
$('#eyebrows').attr("id")
//设置节点属性
$('#eyebrows').attr("class","aaa");
$('#eyebrows').prop("class","aaa");
//获取节点的内容
$('#eyebrows').html();
//获取节点的文本
$('#eyebrows').text();
//获得下拉框的value
selector.val();
//获得被选中下拉框的文本
$('#provinces option:selected').text()
//获取checkbox是否选中
$('选择器').is('checked');或 this.checked //this代表的是被操作的元素,是js对象
//设置checkbox选中或未选中
 $('#u11 li input').prop('checked',true); 或 this.checked = true
//反选
ip3.click(()=>{
        let a = $('#u11>li>input');
        a.each(function () {
           this.checked = !this.checked;//js写法
            // $(this).prop('checked',!$(this).is(':checked')) Jquery 写法
        })
=})
```

