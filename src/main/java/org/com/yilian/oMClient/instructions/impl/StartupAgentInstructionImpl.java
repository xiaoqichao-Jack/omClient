package org.com.yilian.oMClient.instructions.impl;

import org.com.yilian.oMClient.instructions.Instruction;
import org.com.yilian.oMClient.tool.OSInfoUtil;
import org.com.yilian.oMClient.tool.ProperUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class StartupAgentInstructionImpl implements Instruction {

    /**
     * 判断当前系统是linux
     * 还是Windows  C:\app\zabbix\bin\win64\zabbix_agentd.exe -c C:\app\zabbix\conf\zabbix_agentd.win.conf -x
     * @param socket
     * @return
     */
    public int executeInstruction(Socket socket) {
        if(OSInfoUtil.isWindows()){
            String relativelyPath=System.getProperty("user.dir");
            String batPath = relativelyPath + File.separator + "zabbixStart.bat";
            System.out.println(batPath);
            File batFile = new File(batPath);

            if (batFile.exists()) {
                callCmd(batPath);
            }
        }else{
            //调用shell 脚本
            bashCommand("systemctl start zabbix-agent");
        }
        return 0;
    }


    private void  callCmd(String locationCmd){
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(locationCmd);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("sb:" + sb.toString());
            System.out.println("callCmd execute finished");
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private String bashCommand(String command) {
        Process process = null;
        String stringBack = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : processList) {
            stringBack += line;
            stringBack +="\n";
        }
        return stringBack;
    }

}
