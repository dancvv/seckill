import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

//static class Main {
//
//    public int minimumDeviation(int[] nums) {
//        int ans, matrix;
//        Arrays.sort(nums);
//        int n = nums.length;
//        matrix = Integer.MAX_VALUE;
//        ans = nums[n-1] - nums[0];
//        PriorityQueue<Integer> q = new PriorityQueue<>();
//        for(int i=0; i<n; i++){
//            if(nums[i] % 2 != 0){
//                nums[i] *= 2;
//                q.add(nums[i]);
//                matrix = Math.min(matrix, nums[i]);
//            }
//        }
//        while ((!q.isEmpty())){
//            int now = q.poll();
//            ans = Math.min(ans, now - matrix);
//            if(now % 2  != 0) {
//                break;
//            }
//            q.add(now / 2);
//            matrix = Math.min(matrix, now /2 );
//        }
//        return ans;
//    }
//    /* Write Code Here */
//    public int findMinDifference(String[] timePoints) {
//        Date[] dates = new Date[timePoints.length];
//        for(int i=0; i< timePoints.length; i++){
//            try{
//                Date date = new SimpleDateFormat("HH:mm").parse(timePoints[i]);
//                dates[i] = date;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        Arrays.sort(dates);
//        long[] distance = new long[dates.length];
//        for(int i=0; i< dates.length; i++){
//            if(i == dates.length - 1){
//                distance[i] = Math.abs((dates[i].getTime() - dates[0].getTime())/60000L);
//            }else{
//                distance[i] = Math.abs((dates[i+1].getTime() - dates[i].getTime())/60000L);
//            }
//            distance[i] = distance[i] > 720L ?(1440L - distance[i]) : distance[i];
//        }
//        Arrays.sort(distance);
//        Long minest = distance[0];
//        String intNum = minest.toString();
//        return Integer.valueOf(intNum);
//    }
//}

public class Solution {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int res;

        int timePoints_size = 0;
        timePoints_size = in.nextInt();
        if (in.hasNextLine())
            in.nextLine();
        String[] timePoints = new String[timePoints_size];
        String timePoints_item;
        for(int timePoints_i = 0; timePoints_i < timePoints_size; timePoints_i++) {
            try {
                timePoints_item = in.nextLine();
            } catch (Exception e) {
                timePoints_item = null;
            }
            timePoints[timePoints_i] = timePoints_item;
        }

        //res = new Solution().findMinDifference(timePoints);
        //System.out.println(String.valueOf(res));

    }
}
