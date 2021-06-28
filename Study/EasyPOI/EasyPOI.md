### 1.EasyPoi简介

> easypoi功能如同名字easy,主打的功能就是容易,让一个没见接触过poi的人员 就可以方便的写出Excel导出,Excel模板导出,Excel导入,Word模板导出,通过简单的注解和模板 语言(熟悉的表达式语法),完成以前复杂的写法

### 导入springboot依赖

```
<dependency>
    <groupId>cn.afterturn</groupId>
    <artifactId>easypoi-spring-boot-starter</artifactId>
    <version>4.1.3</version>
</dependency>
```

### 基本使用

首先要给实体类的字段上添加注解

#### @Excel

添加在普通字段上，常用属性有

- name: excel表格的列名
- width：格子的宽度
- needMerge: 当有一对多的字段时，需要给普通字段设定为true
- format：时间格式
- orderNum：排序权重，数字越大越往后排
- replace：值替换，下划线分割，前面是想换的值，后面是字段的值

```java
@Excel(name = "aid",needMerge = true)
private Integer id;
@Excel(name = "入职日期",width = 30, format = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime beginDate;
@Excel(name = "B的名字",needMerge = true,orderNum = "1")
private String name;
@Excel(name = "是否启用", replace = {"启用_true","停用_false"})
private Boolean enabled;
```

#### @ExcelEntity

添加在一对一字段上面，常用属性，没有orderNum属性，需要在对象里面指定

id：没发现有什么卵用

```java
@ExcelEntity
private B b;
```

#### @ExcelCollection

添加在一对多字段上

- orderNum：排序权重，数字越大越往后排

```java
@ExcelCollection(name = "集合D",orderNum = "3")
private List<D> ds;
```

#### 实体A

```java
@Data
public class A {
    @Excel(name = "aid",needMerge = true)
    private Integer id;
    @Excel(name = "A的名字",needMerge = true)
    private String name;
    private String remark;
    @ExcelEntity
    private B b;
    @ExcelCollection(name="集合C",orderNum = "4")
    private List<C> cs;
    @ExcelCollection(name = "集合D",orderNum = "3")
    private List<D> ds;

}
```

#### 单Sheet导出

```java
 public static void main(String[] args) throws IOException {
        A a = new A();
        a.setId(1);
        a.setName("AAAAA");
        B b = new B();
        b.setId(1);
        b.setName("BBBBB");
        List<C> cList = Arrays.asList(new C(1, "C1"), new C(2, "C2"));
        List<D> ds = Arrays.asList(new D(1, "D1"), new D(2, "D2"));
        a.setDs(ds);
        a.setB(b);
        a.setCs(cList);
        List<A> list = new ArrayList<>();
        list.add(a);
     //第一个参数为ExportParams，第二个参数为导出的实体类，第三个为Arraylist，不能是不可修改的list，要把实体类添加到list中
     //ExportParams第一个参数为标题独占一行，第二为副标题，也是独占一行，第三sheet名字，就是当前表格的名称
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("A", "test","爬"), A.class, list);
        sheets.write(new FileOutputStream("F:\\Java\\Study\\IProject\\test4.xls"));
    }
```

#### 多Sheet导出

```java
 public static void main(String[] args) throws IOException {
        A a = new A();
        a.setId(1);
        a.setName("AAAAA");
        B b = new B();
        b.setId(1);
        b.setName("BBBBB");
        List<C> cList = Arrays.asList(new C(1, "C1"), new C(2, "C2"));
        List<D> ds = Arrays.asList(new D(1, "D1"), new D(2, "D2"));
        a.setDs(ds);
        a.setB(b);
        a.setCs(cList);
        List<A> list = new ArrayList<>();
        list.add(a);
        List<A> list1 = new ArrayList<>();
        list1.add(a);
        List<A> list2 = new ArrayList<>();
        list2.add(a);
        ExportParams params1 = new ExportParams("A", "A1", ExcelType.HSSF);//第二个是sheet名，一定要不一样
        ExportParams params2 = new ExportParams("A", "A2", ExcelType.HSSF);
        ExportParams params3 = new ExportParams("A", "A3", ExcelType.HSSF);
        Workbook workbook = new HSSFWorkbook();
        ExcelExportService service = new ExcelExportService();
        service.createSheet(workbook,params1,A.class,list);//list一定不能是一个对象
        service.createSheet(workbook,params2,A.class,list1);
        service.createSheet(workbook,params3,A.class,list2);
        workbook.write(new FileOutputStream("F:\\Java\\Study\\IProject\\test5.xls"));
    }
```

#### 导入

更多配置详解官方配置文档

```java
 public static void main(String[] args) throws IOException {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);//标题行数
        importParams.setHeadRows(2);//表头行数
        importParams.setStartSheetIndex(1);
        importParams.setSheetNum(2);
        File file = new File("F:\\Java\\Study\\IProject\\test5.xls");
        List<Object> objects = ExcelImportUtil.importExcel(file, A.class, importParams);
        for (Object object : objects) {
            System.out.println(object);
        }
    }
```

