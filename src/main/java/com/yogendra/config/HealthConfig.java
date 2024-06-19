package com.yogendra.config;

import com.yogendra.health.AccountHealthIndicator;
import com.yogendra.health.HealthMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class HealthConfig {

    private final JdbcTemplate jdbcTemplate;

    public HealthConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Bean
    public AccountHealthIndicator accountHealthIndicator() {
        return new AccountHealthIndicator(jdbcTemplate);
    }

    @Bean
    public HealthMetrics healthMetrics(MeterRegistry meterRegistry) {
        return new HealthMetrics(accountHealthIndicator(), meterRegistry);
    }
}
