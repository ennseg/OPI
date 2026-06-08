//package com.example;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//import org.flywaydb.core.Flyway;
//
//@WebListener
//public class StartupListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        Flyway flyway = Flyway.configure()
//                .dataSource(
//                        "jdbc:postgresql://lab3-primary:" + System.getenv("DB_PORT") + "/" + System.getenv("DB_NAME"),
//                        System.getenv("DB_USER"),
//                        System.getenv("DB_PASS")
//                )
//                .load();
//
//        flyway.migrate();
//    }
//}
