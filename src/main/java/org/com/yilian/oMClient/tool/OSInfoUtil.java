package org.com.yilian.oMClient.tool;

public class OSInfoUtil {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static OSInfoUtil _instance = new OSInfoUtil();


    private OSInfoUtil(){}

    public static boolean isLinux(){
        return OS.indexOf("linux")>=0;
    }
    public static boolean isWindows(){
        return OS.indexOf("window")>=0;
    }


}
