package org.com.yilian.oMClient.instructions;

import org.com.yilian.oMClient.instructions.impl.UpdateAgentConfigInstructionImpl;
import org.com.yilian.oMClient.instructions.impl.UpdateAndInstallInstructionImpl;
import org.com.yilian.oMClient.instructions.impl.ViewAgentConfigInstructionImpl;
import org.com.yilian.oMClient.tool.SignatureUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Functions {
    private Functions(){}
    private static Functions instans;
    public static Functions getInstans(){
        if(null == instans){
            instans = new Functions();
        }
        return instans;
    }
    private static boolean writeMessageToService(String message, Socket socket) throws Exception {
        OutputStream out = socket.getOutputStream();
        PrintStream printStream = new PrintStream(out);
        printStream.println(message);
        printStream.flush();
        return true;
    }

    public void upadteAndInstal(Socket socket) throws Exception{
            writeMessageToService("verification success", socket);
            //执行指令
            Instruction instruction = new UpdateAndInstallInstructionImpl();
            int execution = instruction.executeInstruction(socket);
            //发送执行结果
            boolean b = (execution == 1) ? writeMessageToService("execute instruction success", socket) : writeMessageToService("execute instruction error", socket);
            System.out.println("写完发送执行结果之后 socket状态 " + socket.isClosed());
    }

    public void viewAgentConfig(Socket socket){
        //执行指令
        Instruction instruction = new ViewAgentConfigInstructionImpl();
        int execution = instruction.executeInstruction(socket);
    }

    public void upadteAgentConfig(Socket socket) throws Exception{
        writeMessageToService("verification success", socket);
        //执行指令
        Instruction instruction = new UpdateAgentConfigInstructionImpl();
        int execution = instruction.executeInstruction(socket);
        //发送执行结果
        boolean b = (execution == 1) ? writeMessageToService("execute instruction success", socket) : writeMessageToService("execute instruction error", socket);
        System.out.println("写完发送执行结果之后 socket状态 " + socket.isClosed());
    }
}
