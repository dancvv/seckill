package com.xxxxx.seckill.vo;

import java.util.Scanner;

/**
 * @Classname Clean
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/13 4:28 PM
 * @Created by weivang
 */
public class Clean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        //StringBuilder sbd = new StringBuilder();
        //for (int i = 0; i < k; i++) {
        //    sbd.append(sc.next());
        //}
        String sbd = sc.next();
        int[][] mat = new int[n][m];
        int sum = n*m;
        mat[0][0] = 1;
        int count = 1;
        int x=0;
        int y=0;
        for(int i=0; i<sbd.length(); i++){
            if(sbd.charAt(i) == 'W'){
                x--;
                if(x>n-1 || x<0){
                    System.out.println("No");
                    break;
                }
                if(mat[x][y] != 1){
                    mat[x][y] = 1;
                    count++;
                }
            }else if(sbd.charAt(i) == 'A'){
                y--;
                if(y > m-1 ||y<0){
                    System.out.println("No");
                    break;
                }
                if(mat[x][y] != 1){
                    mat[x][y] = 1;
                    count++;
                }
            }else if(sbd.charAt(i) == 'S'){
                x++;
                if(x > n-1 || x < 0){
                    System.out.println("No");
                    break;
                }
                if(mat[x][y] != 1){
                    mat[x][y] = 1;
                    count++;
                }
            }else if(sbd.charAt(i) == 'D'){
                y++;
                if(y > m-1 || y<0){
                    System.out.println("No");
                    break;
                }
                if(mat[x][y] != 1){
                    mat[x][y] = 1;
                    count++;
                }
            }
            if(count == sum){
                System.out.println("Yes");
                System.out.println(i + 1);
                break;
            }
        }

        if(count < sum){
            System.out.println("No");
            System.out.println(sum - count);
        }

    }
}
