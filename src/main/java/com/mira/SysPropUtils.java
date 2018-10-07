/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira;

import com.mira.database.ConnectionManager;
import java.io.File;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author basri baki
 */
public class SysPropUtils {
     private static Properties prop = null;
     
     public static void reload() {
        prop = new Properties();
            if(ConnectionManager.isSystemProperties()) {
                try {
                    prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("system.properties"));
                }catch(Exception e) {}
            }
    }
     
     public static String getProperty(String name) {
        if(prop == null) {
            reload();
        }
        return prop.getProperty(name);
    }
}
    