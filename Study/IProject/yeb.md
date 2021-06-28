## 使用的技术

### 后台技术

Springboot，SpringMVC，MybatisPlus，Lombok，AutoGenerator，Swagger2，SpringSecurity，JWT，Kaptcha，Redis，EasyPOI

RabbitMQ，Mail，WebSocket，FastDFS

### 前端技术

Vue，Vue-cli，Vuex，VueRouter，ElementUI，Axios，ES6，Webpack，WebSocket，font-awesome，js-file-download，vue-chat

### 模块

登录，职位管理，职称管理，部门管理，操作员历，员工管理，工资账号管理，个人中心，在线聊天



## 项目记录

### 首页侧边栏图标

```bash
npm install font-awesome

#在main.js中导入
import 'font-awesome/css/font-awesome.css'
```

## 前台问题记录

教程提供的界面太丑了，所以用vue-element-admin的ui来替代

### 插件安装

#### 安装sass

```bash
#先用npm试一下，不行就用cnpm
npm install sass@1.26.2 sass-loader@7.3.1 --save

cnpm install sass@1.26.2 sass-loader@7.3.1 --save
```

#### 安装echarts,图表ui

```bash
npm install echarts@4.2.1 --save
```

#### 安装vue-count-to，一个数字滚动的插件

```bash
npm install vue-count-to@1.0.13 --save
```

#### 安装path-to-regexp,一个路径字符串转正则表达式的东西

```bash
npm install path-to-regexp@2.4.0 --save
```

#### 安装fuse.js，用于JavaScript中数据的模糊搜索

```bash
npm install fuse.js@3.4.4 --save
```

#### 安装screenfull，实现全屏功能

```bash
npm install screenfull@4.2.0 --save
```

#### 安装normalize.css

- 保留有用的默认值，不同于许多 CSS 的重置
- 标准化的样式，适用范围广的元素。
- 纠正错误和常见的浏览器的不一致性。
- 一些细微的改进，提高了易用性。
- 使用详细的注释来解释代码。 

```bash
npm install normalize.css@7.0.0 --save
```

#### 安装进度条插件

```bash
npm install nprogress@0.2.0 --save
```

#### 安装图标

```bash
npm install svg-sprite-loader@4.1.3 --save
```



### Components

#### RightPanel

屏幕右边的设置面板

## 后台问题记录

### 1.redis缓存异常了该如何处理？

默认情况，如果什么都不做，会抛出异常从而影响后面的逻辑执行

### 2.rabbitMQ如何把对象当作消息？

### 3.登录的token验证逻辑（非常重要）

4.