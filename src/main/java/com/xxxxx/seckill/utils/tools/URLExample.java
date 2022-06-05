package com.xxxxx.seckill.utils.tools;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLExample {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://baidu.com");
        URLConnection urlConnection = url.openConnection();
        InputStream in = urlConnection.getInputStream();
        int data = in.read();
        while(data != -1){
            System.out.println( (char) data);
            data = in.read();
        }
        in.close();
    }
}
