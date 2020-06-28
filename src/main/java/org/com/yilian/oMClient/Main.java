package org.com.yilian.oMClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int port=62453;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //创建服务端
            System.out.println("服务端启动，运行在" + serverSocket.getLocalSocketAddress());
            // 轮流等待请求
            while(true) {
                // 等待客户端请求,无请求则闲置;有请求到来时,返回一个对该请求的socket连接
                Socket clientSocket = serverSocket.accept();
                // 处理连接
                Thread t = new Thread(new App(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
