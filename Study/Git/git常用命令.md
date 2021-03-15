## 1.添加工作目录文件到暂存区

```
git add .
```

## 2. 查看文件状态

在工作目录打开命令行输入以下

```
git status [filename] 
如：git status Study

查看所有文件状态
git status
```

## 3.提交所有文件到本地仓库

```
git commit -m "提交信息记录
```

## 4. IDEA git合并

点击右下角分支图标，点击Local Branches下的非当前分支，左键点击非当前分支，选择Merge into current,

这里合并的意思是：假设有两个分支dev与main，现在要把dev合并到main上，就是把dev上的新的内容加到main上，而main上的新内容是不会加到dev上的。

如果出现了冲突，如同一个类里面方法名相同