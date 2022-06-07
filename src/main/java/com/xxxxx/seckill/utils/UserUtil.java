package com.xxxxx.seckill.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.vo.RespBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>(count);
        for(int i=0; i<count; i++){
            User user = new User();
            user.setId(13100000000L+i);
            user.setNickname("user"+i);
            user.setSlat("1a2b3c");
            user.setLoginCount(1);
            user.setRegisterDate(new Date());
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSlat()));
            users.add(user);
        }
        System.out.println("create user");
//        插入数据库
//        Connection connection = getConn();
//        String sql = "insert into t_user(login_count, nickname, register_date, slat, password, id) values (?,?,?,?,?,?)";
//        PreparedStatement pstmt = connection.prepareStatement(sql);
//        System.out.println("用户长度"+users.size());
//        for (User user : users) {
//            pstmt.setInt(1, user.getLoginCount());
//            pstmt.setString(2, user.getNickname());
//            pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
//            pstmt.setString(4, user.getSlat());
//            pstmt.setString(5, user.getPassword());
//            pstmt.setLong(6, user.getId());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        connection.close();
        System.out.println("inset to db");
        // 登录，生成token
        String urlString = "http://localhost:8868/login/doLogin";
        File file = new File("/Users/wangwei162/Documents/config.csv");
        if(file.exists()){
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (User user : users) {
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFromPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = bout.toString();
            ObjectMapper mapper = new ObjectMapper();
            /**
             * 报错，字符串不匹配
             */
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = (String) respBean.getObj();
            System.out.println(userTicket);
            System.out.println("create userTicket: " + user.getId());

            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file: " + user.getId());
        }
        raf.close();
        System.out.println("over");
    }

    /**
     * 连接数据库基本操作
     * @return
     * @throws Exception
     */
    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://49.233.56.74:3310/seckill?useUnicode=true&characterEndcoding=UTF-8&&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
