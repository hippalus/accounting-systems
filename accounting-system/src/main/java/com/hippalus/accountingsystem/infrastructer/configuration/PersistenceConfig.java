package com.hippalus.accountingsystem.infrastructer.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Getter@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.hippalus.accountingsystem.domain.repository")
public class PersistenceConfig {

    private String username;
    private String password;
    @Value("${spring.datasource.hikari.jdbc-url}")
    private String jdbcUrl;
    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.hikari.pool-name}")
    private String pollName;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxPollSize;
    @Value("${spring.datasource.hikari.connection-test-query}")
    private String connectionTestQuery;

    @Bean
    public DataSource dataSource(){
        var hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(this.driverClassName);
        hikariConfig.setJdbcUrl(this.jdbcUrl);
        hikariConfig.setUsername(this.username);
        hikariConfig.setPassword(this.password);
        hikariConfig.setMaximumPoolSize(this.maxPollSize);
        hikariConfig.setConnectionTestQuery(this.connectionTestQuery);
        hikariConfig.setPoolName(this.pollName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
