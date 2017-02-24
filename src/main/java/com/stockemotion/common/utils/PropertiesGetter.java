package com.stockemotion.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wode3 on 2016/11/23.
 */
public class PropertiesGetter {
    private static Properties config = null;

    static {
        InputStream in = PropertiesGetter.class.getClassLoader().getResourceAsStream(
                "server.properties");
        config = new Properties();
        try {
            config.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("No server.properties defined error");
        }
    }

    public static String getValue(String key) {
        try {
            String value = config.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ConfigInfoError" + e.toString());
            return null;
        }
    }
}
