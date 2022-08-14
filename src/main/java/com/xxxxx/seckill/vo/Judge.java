package com.xxxxx.seckill.vo;

import java.util.Random;
import java.util.Scanner;

/**
 * @Classname Judge
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/14 3:23 PM
 * @Created by weivang
 */
public class Judge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        Random r = new Random();
        boolean b = r.nextBoolean();
        System.out.println(b);

    }
}
