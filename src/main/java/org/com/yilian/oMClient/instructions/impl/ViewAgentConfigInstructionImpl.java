package org.com.yilian.oMClient.instructions.impl;

import org.com.yilian.oMClient.instructions.Instruction;
import org.com.yilian.oMClient.tool.ProperUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class ViewAgentConfigInstructionImpl implements Instruction {
    private static String file = ProperUtils.getProp("agentConfigFile");
    public int executeInstruction(Socket socket) {
        int result ;
        try {

            File fi = new File(file);
            System.out.println("文件长度:" + (int) fi.length());
            DataInputStream fis = new DataInputStream(new FileInputStream(fi));
            DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
            ps.writeUTF(fi.getName());
            ps.flush();
            ps.writeLong((long) fi.length());
            ps.flush();
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            while (true) {
                int read = 0;
                if (fis != null) {
                    read = fis.read(buf);
                }
                if (read == -1) {
                    break;
                }
                ps.write(buf, 0, read);
            }
            ps.flush();
            fis.close();
            socket.shutdownOutput();
            result = 1;
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }
}
