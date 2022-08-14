package com.xxxxx.seckill.vo;

import java.util.Scanner;

/**
 * @Classname Player
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/14 3:17 PM
 * @Created by weivang
 */
public class Player {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int count = 0;
        for(int i=0; i<= num; i++){
            if(String.valueOf(i).contains("25")){
                count ++;
            }
        }
        System.out.println(count);
    }
}
