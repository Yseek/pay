package com.pay.point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pay.point", "com.pay.common"})
public class PointApplication {
    public static void main(String[] args) {
        SpringApplication.run(PointApplication.class, args);
    }
}
