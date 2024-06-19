package com.yogendra.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;


public class AccountHealthIndicator implements HealthIndicator {

    public final JdbcTemplate jdbcTemplate;

    public AccountHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        Long count = jdbcTemplate.queryForObject("select count(*) from account", Long.class);

        if (count >= 5) {
            return Health.up().withDetail("accounts", count).build();
        } else {
            return Health.status("NO_ACCOUNT").withDetail("accounts", count).build();
        }
    }
}
