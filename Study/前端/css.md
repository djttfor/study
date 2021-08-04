# 

## 1.导入方式

行内样式：在标签元素中编写一个style属性

内部样式：写在head标签里面的style

外部样式：通过link标签引入

```html
<link rel="stylesheet" href="../css/style.css">
```

## 2.选择器

作用：选择页面上的某一个或者某一类元素

```html
<div id="d0">d0</div>
<div id="d1" style="width: 30%">
    <div id="d11">
        <span class="s1" >s1</span>
    </div>
    <span class="s2">s2</span>
    <span class="s2">s3</span>
</div>
<div id="d2">d2</div>
<div id="d3">d3</div>
```

### 2.1 基本选择器

**总结排序：!important > 行内样式 > ID选择器 > 类选择器 > 元素 > 通配符 > 继承 > 浏览器默认属性**

标签选择器 : 标签名称

类选择器 class ：.class名称

id选择器 ：#id名称

<table><thead><tr><th>选择器</th><th align="center"></th><th align="right">eg</th><th>描述</th></tr></thead><tbody><tr><td>通用选择器</td><td align="center"><a href="/cssref/selector_all.asp" title="CSS * 选择器">*</a></td><td align="right">*</td><td>选择所有元素。</td></tr><tr><td>类选择器</td><td align="center"><a href="/cssref/selector_class.asp" title="CSS .class 选择器">.<i>class</i></a></td><td align="right">.message</td><td>选择 class="intro" 的所有元素。</td></tr><tr><td>id选择器</td><td align="center"><a href="/cssref/selector_id.asp" title="CSS #id 选择器">#<i>id</i></a></td><td align="right">#head</td><td>选择 id="firstname" 的所有元素。</td></tr><tr><td>元素选择器</td><td align="center"><a href="/cssref/selector_element.asp" title="CSS element 选择器"><i>el</i></a></td><td align="right">p</td><td>选择所有 &lt;p&gt; 元素。</td></tr><tr><td>选择器分组</td><td align="center"><a href="/cssref/selector_element_comma.asp" title="CSS element,element 选择器"><i>el</i>,<i>el</i></a></td><td align="right">div,p</td><td>选择所有 &lt;div&gt; 元素和所有 &lt;p&gt; 元素。</td></tr><tr><td>后代选择器</td><td align="center"><a href="/cssref/selector_element_element.asp" title="CSS element element 选择器"><i>el</i> <i>el</i></a></td><td align="right">div p</td><td>选择 &lt;div&gt; 元素内部的所有 &lt;p&gt; 元素。</td></tr><tr><td>子元素选择器</td><td align="center"><a href="http://www.w3school.com.cn/tiy/t.asp?f=csse_selector_child" title="&quot;CSS"><i>el</i> &gt; <i>el</i></a></td><td align="right">div&gt;p</td><td>选择 &lt;div&gt; 的第一子代的 所有&lt;p&gt; 元素。</td></tr><tr><td>相邻兄弟选择器</td><td align="center"><a href="http://www.w3school.com.cn/tiy/t.asp?f=csse_selector_adjacent_sibling" title="CSS element+element 选择器"><i>el</i> + <i>el</i></a></td><td align="right">div+p</td><td>选择与&lt;div&gt;同级且紧接在其后的第一个 &lt;p&gt; 元素</td></tr></tbody></table>

### 2.2 层次选择器

后代选择器 ：选择自己所有的后代

```css
#d1 .s1{/*也可以用标签名，但最好用class名称或者id名称*/
    background: green;
}
```

子选择器 ：只选自己的直系子

```css
#d1>.s1{
    background: pink;
}
```

相邻兄弟选择器，同辈，只生效下面的第一个兄弟

```css
#d1 + div{
    background: green;
}
```

通用选择器 ，同辈，生效下面的所有兄弟

```css
#d1~div{
    background: #ff253d;
}
```

### 2.3 属性选择器

<table><thead><tr><th align="center">类型</th><th align="right">eg</th><th>描述</th></tr></thead><tbody><tr><td align="center"><a href="/cssref/selector_attribute.asp" title="CSS [attribute] 选择器">[<i>attribute</i>]</a></td><td align="right">[target]</td><td>选择带有 target 属性所有元素</td></tr><tr><td align="center"><a href="/cssref/selector_attribute_value.asp" title="CSS [attribute=value] 选择器">[<i>attribute</i>=<i>value</i>]</a></td><td align="right">[target=_blank]</td><td>选择 target="_blank" 的所有元素</td></tr><tr><td align="center"><a href="/cssref/selector_attribute_value_contain.asp" title="CSS [attribute~=value] 选择器">[<i>attribute</i>~=<i>value</i>]</a></td><td align="right">[title~=flower]</td><td>选择 title 属性包含单词 "flower" 的所有元素</td></tr><tr><td align="center"><a href="/cssref/selector_attribute_value_start.asp" title="CSS [attribute¦=value] 选择器">[<i>attribute</i>¦=<i>value</i>]</a></td><td align="right">[lang¦=en]</td><td>选择 lang 属性值以 "en" 开头的所有元素。</td></tr><tr><td align="center">[attribute^=”value”]</td><td align="right">[abc^=”def”]</td><td>选择 abc 属性值以 “def” 开头的所有元素</td></tr><tr><td align="center">[attribute$=”value”]</td><td align="right">[abc$=”def”]</td><td>选择 abc 属性值以 “def” 结尾的所有元素</td></tr><tr><td align="center">[attribute*=”value”]</td><td align="right">[abc*=”def”]</td><td>选择 abc 属性值中包含子串 “def” 的所有元素</td></tr></tbody></table>

```css
a[id="a"]{
    background: green;
}

#p1 a{
    color: yellow;
    width: 50px;
    height: 50px;
    display: block;
    float: left;
    margin-left: 5px;
    text-align: center;/*文本水平居中*/
    border-radius: 10px;/*圆角边框*/
    text-decoration: none;/*去除超链接的下划线*/
    line-height: 50px;/*垂直居中*/
}

```

### 2.4 伪类选择器

<table><thead><tr><th align="center">类型</th><th align="right">eg</th><th>描述</th></tr></thead><tbody><tr><td align="center"><a href="/cssref/selector_link.asp" title="CSS :link 选择器">:link</a></td><td align="right">a:link</td><td>选择所有未被访问的链接</td></tr><tr><td align="center"><a href="/cssref/selector_visited.asp" title="CSS :visited 选择器">:visited</a></td><td align="right">a:visited</td><td>选择所有已被访问的链接</td></tr><tr><td align="center"><a href="/cssref/selector_active.asp" title="CSS :active 选择器">:active</a></td><td align="right">a:active</td><td>选择正在被点击的活动链接</td></tr><tr><td align="center"><a href="/cssref/selector_hover.asp" title="CSS :hover 选择器">:hover</a></td><td align="right">a:hover</td><td>选择鼠标指针位于其上的链接</td></tr><tr><td align="center"><a href="/cssref/selector_focus.asp" title="CSS :focus 选择器">:focus</a></td><td align="right">input:focus</td><td>选择获得焦点的 input 元素</td></tr><tr><td align="center"><a href="/cssref/selector_lang.asp" title="CSS :lang(language) 选择器">:lang(<i>language</i>)</a></td><td align="right">p:lang(it)</td><td>选择带有以 "it" 开头的 lang 属性值的每个 &lt;p&gt; 元素</td></tr></tbody></table>

### 2.5 伪元素

<table><thead><tr><th align="center">类型</th><th align="right">eg</th><th>描述</th></tr></thead><tbody><tr><td align="center"><a href="/cssref/selector_before.asp" title="CSS :before 选择器">:before</a></td><td align="right">p:before</td><td>在每个 &lt;p&gt; 元素的内容之前插入内容</td></tr><tr><td align="center"><a href="/cssref/selector_after.asp" title="CSS :after 选择器">:after</a></td><td align="right">p:after</td><td>在每个 &lt;p&gt; 元素的内容之后插入内容</td></tr><tr><td align="center"><a href="/cssref/selector_first-letter.asp" title="CSS :first-letter 选择器">:first-letter</a></td><td align="right">p:first-letter</td><td>选择每个 &lt;p&gt; 元素的首字母</td></tr><tr><td align="center"><a href="/cssref/selector_first-line.asp" title="CSS :first-line 选择器">:first-line</a></td><td align="right">p:first-line</td><td>选择每个 &lt;p&gt; 元素的首行</td></tr><tr><td align="center"><a href="/cssref/selector_first-child.asp" title="CSS :first-child 选择器">:first-child</a></td><td align="right">p:first-child</td><td>选择属于父元素的第一个子元素的每个 &lt;p&gt; 元素</td></tr><tr><td align="center"></td><td align="right"></td><td></td></tr></tbody></table>

<table class="dataintable"><tbody><tr><td><a href="/cssref/selector_gen_sibling.asp" title="CSS element1~element2 选择器"><i>el1</i>~<i>el2</i></a></td><td>p~ul</td><td>选择前面有 &lt;p&gt; 元素的每个 &lt;ul&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_first-of-type.asp" title="CSS :first-of-type 选择器">:first-of-type</a></td><td>p:first-of-type</td><td>选择属于其父元素的首个 &lt;p&gt; 元素的每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_last-of-type.asp" title="CSS :last-of-type 选择器">:last-of-type</a></td><td>p:last-of-type</td><td>选择属于其父元素的最后 &lt;p&gt; 元素的每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_only-of-type.asp" title="CSS :only-of-type 选择器">:only-of-type</a></td><td>p:only-of-type</td><td>选择属于其父元素唯一的 &lt;p&gt; 元素的每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_only-child.asp" title="CSS :only-child 选择器">:only-child</a></td><td>p:only-child</td><td>选择属于其父元素的唯一子元素的每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_nth-child.asp" title="CSS :nth-child(n) 选择器">:nth-child(<i>n</i>)</a></td><td>p:nth-child(2)</td><td>选择属于其父元素的第二个子元素的每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_nth-last-child.asp" title="CSS :nth-last-child(n) 选择器">:nth-last-child(<i>n</i>)</a></td><td>p:nth-last-child(2)</td><td>同上，从最后一个子元素开始计数。</td></tr><tr><td><a href="/cssref/selector_nth-of-type.asp" title="CSS :nth-of-type(n) 选择器">:nth-of-type(<i>n</i>)</a></td><td>p:nth-of-type(2)</td><td>选择属于其父元素第二个 &lt;p&gt; 元素的每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_nth-last-of-type.asp" title="CSS :nth-last-of-type(n) 选择器">:nth-last-of-type(<i>n</i>)</a></td><td>p:nth-last-of-type(2)</td><td>同上，但是从最后一个子元素开始计数。</td></tr><tr><td><a href="/cssref/selector_last-child.asp" title="CSS :last-child 选择器">:last-child</a></td><td>p:last-child</td><td>选择属于其父元素最后一个子元素每个 &lt;p&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_root.asp" title="CSS :root 选择器">:root</a></td><td>:root</td><td>选择文档的根元素。</td></tr><tr><td><a href="/cssref/selector_empty.asp" title="CSS :empty 选择器">:empty</a></td><td>p:empty</td><td>选择没有子元素的每个 &lt;p&gt; 元素（包括文本节点）。</td></tr><tr><td><a href="/cssref/selector_target.asp" title="CSS :target 选择器">:target</a></td><td>#news:target</td><td>选择当前活动的 #news 元素。</td></tr><tr><td><a href="/cssref/selector_enabled.asp" title="CSS :enabled 选择器">:enabled</a></td><td>input:enabled</td><td>选择每个启用的 &lt;input&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_disabled.asp" title="CSS :disabled 选择器">:disabled</a></td><td>input:disabled</td><td>选择每个禁用的 &lt;input&gt; 元素</td></tr><tr><td><a href="/cssref/selector_checked.asp" title="CSS :checked 选择器">:checked</a></td><td>input:checked</td><td>选择每个被选中的 &lt;input&gt; 元素。</td></tr><tr><td><a href="/cssref/selector_not.asp" title="CSS :not(selector) 选择器">:not(<i>selector</i>)</a></td><td>:not(p)</td><td>选择非 &lt;p&gt; 元素的每个元素。</td></tr><tr><td><a href="/cssref/selector_selection.asp" title="CSS ::selection 选择器">::selection</a></td><td>::selection</td><td>选择被用户选取的元素部分。</td></tr></tbody></table>

## 3.常用字体样式

```css
/*字体样式*/
body{
    color: green;/*字体颜色*/
    font-size: 30px;/*字体大小*/
    font-family: Arial ,楷体;/*字体*/
    font-style: italic;/*字体风格*/
    font-weight: bold;/*字体粗细*/
    line-height: 100px;/*行高*/
}
/*文本样式*/
#p1{
    text-indent: 2em;/*首行缩进2个字*/
    height: 500px;/*标签高度*/
    background: pink;
    text-align: center;/*文本居中*/
    line-height: 500px;/*行高与标签高度一样，便可实现垂直居中，不过仅限一行*/
}
```

## 4.图片格式

### 背景图片

```css
background-image: url("../images/img.png");/*默认平铺，图片太小，会复制另一张直至铺满*/
background-size: 100% 100%; /*设置占父元素 宽高的比例*/
background-position:10px 10px; /*图片左上角的坐标*/
background-repeat: no-repeat;/*不平铺*/
/*设置透明度的方法：父元素里设置图片，子元素设置如下*/
background:rgba(255,255,255,0.5);
```

### 图片

```css
<img src='' alt='爬'>/*设置图片*/
```

### 背景图片与图片的区别

background-image：不会超出父元素的范围，父元素需要有宽高才能显示

img：图片有多大就显示多大,图片可以超出父元素的范围，图片大小需要设定自己的height与width；

## 5.盒子模型

### 5.1 margin 外边距

margin:  10px 0 10px 50px; 上左下右 ，两个的时候是 上下，左右 ，一个的话就是全部

### 5.2 padding 内边距

同上

### 5.3 border 边框

```css
#log-banner{
    border: 2px solid red;
    width: 100px;
    height: 100px;
    position: relative;
    top: 100px;
    left: 100px;
    background-color: red;
    border-radius: 50px 50px 50px 50px;/*设置上左下右的圆角半径，等于标签长度一半就是一个圆*/
    box-shadow: 10px 10px 100px yellow;/*黄色阴影*/
}
```

盒子的长宽 = 外边距+内边距+边框+内容长宽

如：div的width=250 ，内边距+外边距+边框=150，那么盒子长度就有350了。

### 5.4 float

float：left或right； 块元素才会浮动，如果都是left，会堆在一行

如果要浮动到新的行，需要设置clear：both；

div浮动的时候，其他div就会被移动上来，需要下面的div设置clear：both

解决父级边框塌陷问题：

1.设置overflow：hidden 或 auto 或 scroll（这个会生成拉长条）

```css
2.在父标签添加一个伪类，注意冒号直接不能有空格
#content:after{
    content: "";
    display: block;
    clear: both;
}
```

## 6  定位

### 1.父相子绝

父元素设置relative，子元素设置absolute，子元素就可以使用**bottom、right、left、top**来基于父元素偏移了，

**注意**：如果父元素没有设置relative，那么会基于body偏移

偏移效果：left: 30px 会使得子元素的左上角端点离左边30px, right:30px 会使右上角端点离右边30px,依此类推。

```css
background-image: url(${imgUrl});
    background-size: 100% 100%;
    width: 100px;
    height: 100px;
    position: absolute;
    top: 30px;
    left: 30px;
```

### 2.子相

子元素设置relative，此时会根据它的直接父元素进行偏移

### 注意事项

>relative只会相对于自己的直接父元素偏移，top：200px是指向父元素的上边界偏移200px.
>
>absolute会找到设置了relative或absolute的父元素，没有找到则以body为父元素为基准，设定top 、left、right、bottom时 top、left优先级大于right、bottom

固定定位：相对于浏览器进行定位，规定在浏览器的某个位置

position：flex

z-index: 在两个相互覆盖的块级元素中定义，谁大谁在上面。

## @keyframes

>使用@keyframes规则，你可以创建动画。
>
>创建动画是通过逐步改变从一个CSS样式设定到另一个。
>
>在动画过程中，您可以更改CSS样式的设定多次。
>
>指定的变化时发生时使用％，或关键字"from"和"to"，这是和0％到100％相同。

### 示例1：div块逐渐变色

```css
.d2{
  width: 100px;
  height: 100px;
  background-color: red;
  animation: mymove 2s linear infinite;;
}

@keyframes mymove
{
	0% {background-color: red;}
	25% {background-color: yellow;}
  50% {background-color: black;}
  75% {background-color: orange;}
  100% {background-color: pink;}
}
```

## div的overflow

overflow 属性规定当内容溢出元素框时发生的事情。

| 值      | 描述                                                     |
| ------- | -------------------------------------------------------- |
| visible | 默认值。内容不会被修剪，会呈现在元素框之外。             |
| hidden  | 内容会被修剪，并且其余内容是不可见的。                   |
| scroll  | 内容会被修剪，但是浏览器会显示滚动条以便查看其余的内容。 |
| auto    | 如果内容被修剪，则浏览器会显示滚动条以便查看其余的内容。 |
| inherit | 规定应该从父元素继承 overflow 属性的值。                 |

## 使用：before插入背景图片

```less
.app{
  position: relative;
  overflow: hidden;
  &:before {
    content: "";
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 3;
    background-attachment: fixed;
    background-image: url("../assets/images/grid.png")
  }
}
```

## @Media

min-height：500px表示 浏览器窗口高度小于500px将触发条件

max-height：500px表示 浏览器窗口高度大于等于500px将触发条件

```
 @media (min-height: 500px) {
      display: none;
}
```

## 水平居中的说明

父元素设置text-align：center 只对display：inline或inline-block的子元素有用

对于display:block 在自身设置margin：auto即可居中；

超级水平居中大法：

```css
.nav-p{
  margin:0 auto;
  float: left;
  width: 500px;
  position: absolute;
  left: 0;
  right: 0;
}
```

