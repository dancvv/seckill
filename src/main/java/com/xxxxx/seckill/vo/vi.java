package com.xxxxx.seckill.vo;

import java.util.Scanner;

/**
 * @Classname vi
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/11 8:06 PM
 * @Created by weivang
 */
public class vi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int index = 0;
        while(n-- > 0){
            arr[index++] = sc.nextInt();
        }
        int a = 0;
        int k = 1;
        for (int j : arr) {
            int ans =0;
            if( j == 1) {
                System.out.println(1);
            }
            int count = 0;
            while (a < j) {
                a = Math.max(a, k) + a;
                k++;
                count++;
            }
            System.out.println(count);
        }
    }
}
