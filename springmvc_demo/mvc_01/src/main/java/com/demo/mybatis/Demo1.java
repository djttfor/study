package com.demo.mybatis;


public class Demo1 {//
    Integer id ;
    String name;
    A a;
    B b ;
    public void doS(){
        this.b = new B();
        System.out.println(this.b.name);
        b.say();
    }
    public static void doB(){
       new Demo1().new B().say();
    }
    public static void main(String[] args) {
        D d = new D(){};
        d.he();
        D.say();
        A.say();
    }
    protected static class A{
        Demo1 d = new Demo1();
        public A build(Integer id){
            d.id = id;
            return this;
        }
        public A build(String name){
            this.d.name = name;
            return this;
        }
        public static void say(){
            System.out.println("这是内部类A");
        }

    }
    protected class B{
        String name;
        Demo1 demo1;
        public void say(){
            System.out.println("这是内部类B");
            A.say();
        }
    }
    protected class C{
        B b;
        public void say(){
            System.out.println("这是内部类C");
            new B().say();
        }
    }
    protected interface D{
        String NAME = "AAAA";
        static void say(){
            System.out.println("这是内部接口D");
        }
        default void he(){
            System.out.println("paappa");
        }
    }
    @Override
    public String toString() {
        return "Demo1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
class K implements Demo1.D{
    public static void main(String[] args) {

    }
}
interface Y extends Demo1.D{
     String name = "";
      void a();
      static void y(){

      }
}
interface Z extends Y{

}
