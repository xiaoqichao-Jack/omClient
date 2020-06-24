package org.com.yilian.oMClient.tool;

import org.com.yilian.oMClient.instructions.SpecifyCollection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignatureUtils {
    /**
     * 判断加密指令是否正确
     * 加密算法：指令值+当前时间 然后反转，再取MD5
     * @return
     */
    public static int signature(String ciphertext) throws Exception{

        for (SpecifyCollection e : SpecifyCollection.values()) {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date()).substring(0,"yyyy-MM-dd HH".length());
            StringBuffer sb = new StringBuffer( e.getValue().toString()+date).reverse();
            //加密
            md5.update(sb.toString().getBytes());
            byte[] digest = md5.digest();
            BigInteger bi = new BigInteger(digest);
            String cipher = bi.toString(16).toLowerCase();
            System.out.println( "加密后："+cipher);
            if(cipher.equals(ciphertext)){
                return e.getValue();
            }
            //System.out.println(e.toString());
        }
        return -1;
    }
}
