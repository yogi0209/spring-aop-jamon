package com.yogendra.app;

import com.yogendra.config.AppConfig;
import com.yogendra.config.HealthConfig;
import com.yogendra.config.MonitorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AppConfig.class, MonitorConfig.class, HealthConfig.class})
public class SpringAopJaMonApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringAopJaMonApplication.class, args);
    }
}
