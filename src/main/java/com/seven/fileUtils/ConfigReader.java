package com.seven.fileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private ConfigReader(){

    }
    private static Properties properties;
    static {
        try {
            String path="src/main/resources/application.properties";
            FileInputStream fis = new FileInputStream(path); // чтение файла в path
            properties = new Properties(); // создаем все обьекты класса properties
            properties.load(fis); // загружаем содержимое файла fis в properties
            fis.close();// закрыли поток
        }catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key)
    {
        return properties.getProperty(key);
    }


}
