package com.example.desmosecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.example.desmosecommerce")
public class DesmosecommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesmosecommerceApplication.class, args);
    }

}
