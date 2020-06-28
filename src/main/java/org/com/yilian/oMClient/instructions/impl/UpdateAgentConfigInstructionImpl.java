package org.com.yilian.oMClient.instructions.impl;

import org.com.yilian.oMClient.instructions.Instruction;
import org.com.yilian.oMClient.tool.ProperUtils;

import java.io.*;
import java.net.Socket;

public class UpdateAgentConfigInstructionImpl implements Instruction {
    private static String fileDir = ProperUtils.getProp("agentConfigFilePath");
    public int executeInstruction(Socket socket)  {
        int result ;
        try{
            DataInputStream inputStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            long passedlen = 0;
            long len = 0;
            // 获取文件名
            String file = fileDir + File.separator + inputStream.readUTF();
            File filePath = new File(fileDir);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File updateFile = new File(file);
            if (updateFile.exists()) {
                updateFile.delete();
            }
            DataOutputStream fileOut = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file)));
            len = inputStream.readLong();
            System.out.println("文件的长度为:" + len + "\n");
            System.out.println("开始接收文件!" + "\n");
            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                passedlen += read;
                if (read == -1) {
                    break;
                }
                // 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
                System.out.println("文件接收了" + (passedlen * 100 / len)+ "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" + file + "\n");
            fileOut.close();
            result = 1;
        }catch(Exception e){
            result = 0;
        }
        return result;
    }
}
