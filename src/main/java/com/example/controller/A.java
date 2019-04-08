/**
 * 作者：chao
 * 时间：2019/4/6 15:09
 * 描述：
 */
package com.example.controller;


import java.util.ArrayList;
import java.util.List;

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
}
