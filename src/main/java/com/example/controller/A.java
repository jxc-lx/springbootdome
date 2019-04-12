/**
 * 作者：chao
 * 时间：2019/4/6 15:09
 * 描述：
 */
package com.example.controller;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class A {

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9};print(arr);
        List list = new ArrayList();
    }

    public static void print(int[] arr) {
        for (int a : arr) {
            System.out.println(a);

        }
    }
    @Test
    public void test(){

        String a=null;
        String b="123";
        Assert.assertNull(a);
        Assert.assertEquals(b,"123");

        List list = new ArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        for(Iterator iter = list.iterator(); iter.hasNext();){

            String str = (String) iter.next();
            System.out.println(str);
        }
        Map <String,String>map = new HashMap<String,String>();
        map.put("name","xiaoming");
        map.put("Sex","1");
        map.put("age","18");
        for (Map.Entry<String,String> entry :map.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        String [] arr ={"zzz","xxxxxx"};
        for (String s:arr) {
            System.out.println(s);
        }

    }


}
