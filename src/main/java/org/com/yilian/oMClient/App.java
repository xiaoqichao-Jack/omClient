package org.com.yilian.oMClient;

import org.com.yilian.oMClient.instructions.Functions;
import org.com.yilian.oMClient.instructions.Instruction;
import org.com.yilian.oMClient.instructions.SpecifyCollection;
import org.com.yilian.oMClient.instructions.impl.*;
import org.com.yilian.oMClient.tool.OSInfoUtil;
import org.com.yilian.oMClient.tool.SignatureUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端程序入口类
 * 1 建立连接
 * 2 验证签名
 * 3 校验指令
 * 4 执行指令
 * 5 记录日志
 * 6 返回结果
 */
public class App implements Runnable{
    private Socket socket;
    public App(Socket clientSocket){
        socket = clientSocket;
    }


    private static boolean writeMessageToService(String message, Socket socket) throws Exception {
        OutputStream out = socket.getOutputStream();
        PrintStream printStream = new PrintStream(out);
        printStream.println(message);
        printStream.flush();
        return true;
    }

    private static void getInstruction(int result,Socket socket) throws Exception{
        Functions fun = Functions.getInstans();
        switch (result) {
            case 1:
                fun.upadteAndInstal(socket);
                break;
            case 2:
                fun.viewAgentConfig(socket);
                break;
            case 3:
                fun.upadteAgentConfig(socket);
                break; //可选
            case 4:
                fun.shutDownAgent(socket);
                break; //可选
            case 5:
                fun.startupAgent(socket);
                break; //可选
            case 6:

                break; //可选
            case 7:

                break; //可选
            default: //可选
                //语句
        }
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            Scanner scanner = new Scanner(in);
            String message = scanner.nextLine();
            System.out.println("client>" + message);
            int i = SignatureUtils.signature(message);
            if(-1 == i){
                System.out.println("verification error");
                writeMessageToService("verification error", socket);
            }else{
                System.out.println("verification sucessed");
                //linux不提供更新和安装功能
                if((SpecifyCollection.UPDATE_AND_INSTALL.getValue() == i)&& OSInfoUtil.isLinux()){
                    System.out.println("This feature is not supported");
                    writeMessageToService("This feature is not supported", socket);
                }else{
                    getInstruction(i,socket);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
