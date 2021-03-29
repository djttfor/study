

## 1. a标签注意事项

有个target属性，默认值是_self,在自身窗口打开，_blank则是在新窗口打开。

锚标签 先指定： 然后 就可以去到那个地方

 text-decoration: none;/*去除超链接的下划线*/，需要在style设置

```html
<a href="javascript:void(0)">扫码登录</a>  <!--设置a标签点击不发生任何事-->
```

```html
<a name="top"></a> <!--抛锚-->
<a href="#top"></a><!--到抛锚的地方-->

<a href="333.html#down">去吧</a><!--去到另一个页面抛锚的地方-->

<!--图片链接-->
<a href="a.html">
    <!--alt:图片加载不了时显示的文字，title：鼠标悬停时显示的文字-->
        <img src="../images/img.png" width="10" height="20" alt="hehe" title="点我领取小电影">
</a>


```

## 2.块元素与行内元素

块元素：无论内容多少，该元素独占一行

①总是在新行上开始；
②高度，行高以及外边距和内边距都可控制；
③宽度缺省是它的容器的100%，除非设定一个宽度。
④它可以容纳内联元素和其他块元素

行内元素：内容撑开宽度，左右都是行内元素可以排在一行

①和其他元素都在一行上；
②高，行高及外边距和内边距不可改变；
③宽度就是它的文字的宽度，不可改变
④内联元素只能容纳文本或者其他内联元素

## 3.有序、无序、自定义

有序：ol ：li

无序：ul ：li   list-style: none去掉样式

自定义 ：dl ：dt（标题）：dd

## 4.表格

table ：表格  border:边框 cellpadding="10px"

tr：行

td：列 rowspan 跨行 colspan 跨列

style：样式 border-collapse: collapse 可以消除边框的边距

## 5.页面基本结构

![image-20210302115629580](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210302115629580.png)

## 6.iframe

内联框架，可以在页面嵌套另一个页面

```html
<iframe src="https://www.bilibili.com/video/BV1x4411V75C?p=11" height="500px" width="500px" name="hello">
   <!-- 使用a标签如下配置就可以把a标签跳转的页面在iframe里显示了-->
   <a href="http://www.baidu.com" target="hello">跳转</a> 
```

## 7.表单元格式

![image-20210302142422043](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210302142422043.png)

表单元素提交的时候要指定value和name，不然提交时什么都没有

readonly：只读

disable：禁用

hidden：隐藏

required：必需

## 8.下拉框

select : name 

option: value

```html
<select name="呵呵">
    <option value="1">天朝</option>
    <option value="2">美利坚</option>
    <option value="3">霓虹</option>
    <option value="4">阿三</option>
</select>
<!-- 下拉框展开-->
<select v-model="selected" multiple="multiple" style="width: 50px;display: block;">
            <option>A</option>
            <option>B</option>
            <option>C</option>
</select>
```

### 9.文本域

```html
<textarea name="a" cols="10" rows="50"></textarea>
<!-- 滑块-->
 <input type="range" name="r" step="1" min="0" max="100">
<!--label 点击搜索文字后，焦点自动跑到输入框里-->
<label for="sear">搜索<input type="search" name="search" maxlength="10" id="sear"></label>
<!--video-->
<video src="../video/未来日记.mp4" controls autoplay></video>
```

