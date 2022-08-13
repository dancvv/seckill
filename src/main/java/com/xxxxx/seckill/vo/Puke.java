package com.xxxxx.seckill.vo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @Classname Puke
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/13 5:23 PM
 * @Created by weivang
 */
public class Puke {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Integer[] arr = new Integer[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();
        }
        List<Integer> list = Arrays.asList(arr);
        Collections.shuffle(list);
        list.toArray(arr);
        System.out.println(Arrays.toString(arr));
    }
}
