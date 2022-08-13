package com.xxxxx.seckill.vo;

import java.io.File;
import java.io.InputStream;
import java.io.Writer;
import java.util.*;

/**
 * @Classname Test
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/9 2:32 PM
 * @Created by weivang
 */
public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int times = sc.nextInt();
        Map<String, Integer> map = new HashMap<>();
        Map<String, List<String>> recomend = new HashMap<>();
        Set<String> set = new HashSet<>();
        while(times > 0){
            times--;
            int opp = sc.nextInt();
            if(opp == 1){
                String name = sc.next();
                int num = sc.nextInt();
                map.put(name, num);
                List<String> list = new ArrayList<>();
                while(num > 0){
                    num--;
                    String str = sc.next();
                    list.add(str);
                    set.add(str);
                }
                recomend.put(name, list);
            }else if(opp == 2){
                String name = sc.next();
                if(!recomend.containsKey(name)){
                    System.out.println("error");
                    continue;
                }
                List<String> strings = recomend.get(name);
                System.out.println(set.size() - strings.size());
            }
        }

    }
}
