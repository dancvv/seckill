package com.xxxxx.seckill.vo;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Classname WaImai
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/13 5:14 PM
 * @Created by weivang
 */
public class WaImai {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int t = sc.nextInt();
        int[] sendTime = new int[n];
        int count = 0;
        int nowTime = t;
        for(int i=0; i<n; i++){
            sendTime[i] =sc.nextInt();
        }
        Arrays.sort(sendTime);
        for(int i=0; i<n; i++){
            if(sendTime[i] >= nowTime){
                count++;
                nowTime += t;
            }
        }
        System.out.println(n - count);
    }
}
