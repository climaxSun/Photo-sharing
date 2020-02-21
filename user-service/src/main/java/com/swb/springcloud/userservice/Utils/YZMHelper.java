package com.swb.springcloud.userservice.Utils;

import java.util.Random;

public class YZMHelper {

    public static char[] charArray(){
            int i = 1234567890;
            String s ="qwertyuiopasdfghjklzxcvbnm";
            String S=s.toUpperCase();
            String word=s+S+i;
            char[] c=word.toCharArray();
            return c;
        }

    public static String verifyCode(){
        char[] c= charArray();//获取包含26个字母大小写和数字的字符数组
//        System.out.println(Arrays.toString(c));

        Random rd = new Random();
        String code="";
        for (int k = 0; k < 6; k++) {
            int index = rd.nextInt(c.length);//随机获取数组长度作为索引
            code+=c[index];//循环添加到字符串后面
        }
        return code;
    }

}
