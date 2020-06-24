package org.com.yilian.oMClient;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 客户端程序入口类
 * 1 建立连接
 * 2 验证签名
 * 3 校验指令
 * 4 执行指令
 * 5 记录日志
 * 6 返回结果
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        String str = date.substring(0,"yyyy-MM-dd HH".length());
        StringBuffer sb = new StringBuffer("02"+str).reverse();
        //加密
        md5.update(sb.toString().getBytes());
        byte[] digest = md5.digest();
        BigInteger bi = new BigInteger(digest);
        String result = bi.toString(16).toLowerCase();
        System.out.println( "加密前："+sb.toString());
        System.out.println( "加密后："+result);
    }
}
