package com.xxxxx.seckill.vo;

import java.util.Scanner;

/**
 * @Classname contain
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/6 7:56 PM
 * @Created by weivang
 */
public class contain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] arr = str.split(" ");
        int res = 0;
        for(String a:arr){
            if(a.contains("e") || a.contains("E")){
                res++;
            }
        }
        System.out.println(res);
    }
}
