package org.com.yilian.oMClient.instructions.impl;

import org.com.yilian.oMClient.exception.DownloadFileException;
import org.com.yilian.oMClient.exception.UnZipFilesException;
import org.com.yilian.oMClient.instructions.Instruction;
import org.com.yilian.oMClient.tool.ProperUtils;
import org.com.yilian.oMClient.tool.ZipUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UpdateAndInstallInstructionImpl implements Instruction {
    //下载跟新文件目录
    private static String downFileDir = ProperUtils.getProp("updatePath");
    //解压安装目录
    private static String installFileDir = ProperUtils.getProp("installFileDir");

    /**
     * 跟新并安装 分为四步 只考虑Windows场景
     * 1 下载跟新包 增量的
     * 2 停止服务
     * 3 解压并覆盖原有的文件
     * 4 重启服务
     * @param socket
     * @return
     */
    public int executeInstruction(Socket socket) {
        int result = 0;
        File updateFile = null;
        try {
           updateFile = downloadFile(socket);
        } catch (DownloadFileException e) {
            result = 2;
        }
        //暂停服务
        new ShutDownAgentInstructionImpl().executeInstruction(socket);
        //删除安装目录下的 bin和config 目录下面的所有文件
        delFiles(new File(installFileDir+File.separator+"bin"));
        delFiles(new File(installFileDir+File.separator+"conf"));
        //将安装包解压到指定的安装目录
        unZipFiles(updateFile,installFileDir);
        //执行安装并启动 执行.bat脚本
        String relativelyPath=System.getProperty("user.dir");
        String batPath = relativelyPath + File.separator + "installAndStart.bat";
        File batFile = new File(batPath);

        if (batFile.exists()) {
            callCmd(batPath);
        }
        return result;
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

    private boolean delFiles(File file){
        boolean result = false;
        //目录
        if(file.isDirectory()){
            File[] childrenFiles = file.listFiles();
            for (File childFile:childrenFiles){
                result = delFiles(childFile);
                if(!result){
                    return result;
                }
            }
        }
        //删除 文件、空目录
        result = file.delete();
        return result;
    }

    private File downloadFile(Socket socket) {
        File result;
        try {
            DataInputStream inputStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            long passedlen = 0;
            long len = 0;
            // 获取文件名
            String file = downFileDir + File.separator + inputStream.readUTF();
            File filePath = new File(downFileDir);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            result = new File(file);
            if (result.exists()) {
                result.delete();
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
                System.out.println("文件接收了" + (passedlen * 100 / len) + "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" + file + "\n");
            fileOut.close();
        } catch (Exception e) {
            throw new DownloadFileException(e.getMessage());
        }
        return result;
    }

    /**
     * 将下载的安装包解压
     */
    @SuppressWarnings("rawtypes")
    private void unZipFiles(File zipFile, String descDir) {
        try{
            ZipUtil.unZip(zipFile,descDir);
        }catch (IOException e){
            throw new UnZipFilesException(e.getMessage());
        }

    }

}
