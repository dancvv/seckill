package com.xxxxx.seckill.vo;

import java.util.Random;
import java.util.Scanner;

/**
 * @Classname v2
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/11 8:16 PM
 * @Created by weivang
 */
public class v2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] sub1 = new int[n-1];
        int[] sub2 = new int[n];
        for(int i=0; i<n-1; i++){
            sub1[i] = sc.nextInt();
        }
        for(int i=0; i<n; i++){
            sub2[i] = sc.nextInt();
        }
        Random random = new Random();
        int ans = random.nextInt(n / 2);
        System.out.println(ans);
    }
}
