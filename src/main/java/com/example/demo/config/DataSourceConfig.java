package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://mercancias1.coiuggsmxuus.us-east-1.rds.amazonaws.com:5432/mercancias_final") 
                .username("postgres")
                .password("javier2021")
                .build();
    }
}
