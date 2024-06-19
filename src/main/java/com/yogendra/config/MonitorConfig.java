package com.yogendra.config;

import com.jamonapi.MonitorFactory;
import com.jamonapi.MonitorFactoryInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.yogendra.aspect")
@EnableAspectJAutoProxy
public class MonitorConfig {

    @Bean
    MonitorFactoryInterface monitorFactory() {
        return MonitorFactory.getFactory();
    }

}


