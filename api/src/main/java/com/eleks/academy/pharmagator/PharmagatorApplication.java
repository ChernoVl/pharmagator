package com.eleks.academy.pharmagator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PharmagatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmagatorApplication.class, args);
    }

    // цей бін потрібен для того щоб використовувати прекції
    // можу бути описаний в любому конфігкрпційному класі
//    @Bean
//    public ProjectionFactory projectionFactory(){
//        return new SpelAwareProxyProjectionFactory();
//    }
}
