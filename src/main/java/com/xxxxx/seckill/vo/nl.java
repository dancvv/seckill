package com.xxxxx.seckill.vo;

/**
 * @Classname nl
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/10 8:44 PM
 * @Created by weivang
 */
import java.util.*;

public class nl {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int q = scanner.nextInt();
        Set<String> strs = new HashSet<>();
        Map<String, List<String>> peoples = new HashMap<>();
        while (q-- > 0) {
            int flag = scanner.nextInt();
            if (flag == 1) {
                String name = scanner.next();
                int count = scanner.nextInt();
                List<String> tempList = new ArrayList<>();
                while (count-- > 0) {
                    String tempStr = scanner.next();
                    tempList.add(tempStr);
                    strs.add(tempStr);
                }
                peoples.put(name, tempList);
            } else {
                String name = scanner.next();
                if (!peoples.containsKey(name)) {
                    System.out.println("error");
                    continue;
                }
                List<String> tempList = peoples.get(name);
                System.out.println(strs.size() - tempList.size());
            }
        }
    }
}
