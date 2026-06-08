package com.example.DataBase;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    @Getter
    private static String login;
    @Getter
    private static String password;
    @Getter
    private static String url;

    static  {
        Properties data = new Properties();
        InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            data.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        login = data.getProperty("db.login");
        password = data.getProperty("db.password");
        url = data.getProperty("db.url");
    }

    public static void setPassword(String password) {
        ConfigReader.password = password;
    }
}
