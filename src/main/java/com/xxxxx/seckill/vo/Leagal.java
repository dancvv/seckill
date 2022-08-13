package com.xxxxx.seckill.vo;

import java.util.Scanner;

/**
 * @Classname Leagal
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/13 4:13 PM
 * @Created by weivang
 */
public class Leagal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int count = 0;
        for(int i=0; i<n-2; i++){
            for(int j=i+1; j<n-1; j++){
                for (int k = j+1; k < n; k++) {
                    if(arr[i] - arr[j] == 2*arr[j] -arr[k]){
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
