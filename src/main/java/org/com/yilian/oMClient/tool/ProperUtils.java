package org.com.yilian.oMClient.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

public class ProperUtils {
    public static String getProp(String key) {
        Properties properties = new Properties();
        String relativelyPath=System.getProperty("user.dir");
        String filePath = relativelyPath + File.separator + "config.properties";
        Reader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            properties.load(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
