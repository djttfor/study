package com.ex.util;

public class NameUtil {

    public static String buildFieldName(String name){
        return buildFieldName(name,"_");
    }

    public static String buildFieldName(String name,String separator){
        return buildName(name,separator,true);
    }
    
    public static String buildClassName(String name){
        return buildClassName(name,"_");
    }
    public static String buildClassName(String name,String separator){
        return buildName(name,separator,false);
    }

    /**
     * 下划线转驼峰
     * @param name
     * @param separator
     * @param flag
     * @return
     */
    public static String buildName(String name,String separator,boolean flag){
        String[] split = name.split(separator);
       // if (split.length==1) return name;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String s : split) {
            if(flag && !"".equals(s) && i++==0){
               sb.append(s);
            }else{
                sb.append(firstUpper(s));
            }
        }
        return sb.toString();
    }

    /**
     * 首字符大写
     * @param name
     * @return
     */
    public static String firstUpper(String name){
        if(null==name||"".equals(name)){
            return "";
        }
        name = name.trim();
        char[] chars = name.toCharArray();
        if (chars[0]>=97 && chars[0]<=122) {
            chars[0] -= 32;
            return new String(chars);
        }else{
            return name;
        }
    }

}
