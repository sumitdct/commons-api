package org.billing.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableEurekaClient
@ComponentScan(value = {
        "org.billing.api.controllers",
        "org.billing.api.listeners",
        "org.billing.api.repository",
        "org.billing.api.service",
        "org.billing.api.util",
        "org.billing.api.datasource.config",
        "org.billing.api.datasource.config.util"
})
public class CommonsApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CommonsApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CommonsApiApplication.class);
    }
}
