package com.luoben.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.luoben"}) //配置包扫描 否则它只会扫描当前项目路径，扫描不到common中的公共配置
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {


        try {
            SpringApplication.run(EduApplication.class,args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
