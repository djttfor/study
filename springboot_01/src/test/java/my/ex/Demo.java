package my.ex;


import org.junit.Test;

public class Demo {
    @Test
    public void demo1(){
        String str = "hello";
        System.out.println(myReverse2(str));
    }
    public static String myReverse(String str){
        if(str==null || str.length()==0){
            return "";
        }else{
            return str.charAt(str.length()-1)+myReverse(str.substring(0,str.length()-1));
        }
    }
    public static String myReverse2(String str){
        if(str==null || str.length()==0){
            return str;
        }else{
            return myReverse2(str.substring(1))+str.charAt(0);
        }
    }
    /*
    ello h

    */
}
