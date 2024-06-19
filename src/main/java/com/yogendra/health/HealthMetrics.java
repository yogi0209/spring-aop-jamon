package com.yogendra.health;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.actuate.health.Status;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.management.BufferPoolMXBean;

public class HealthMetrics {

    private final AccountHealthIndicator accountHealthIndicator;
    private final MeterRegistry meterRegistry;

    public HealthMetrics(AccountHealthIndicator accountHealthIndicator, MeterRegistry meterRegistry) {
        this.accountHealthIndicator = accountHealthIndicator;
        this.meterRegistry = meterRegistry;
    }


    @Scheduled(fixedRate = 15000, initialDelay = 0)
    public void reportHealth() {
        Gauge
                .builder("application.health",
                        () -> getStatus(accountHealthIndicator.getHealth(true).getStatus())
                )
                .register(meterRegistry);
    }

    private int getStatus(Status status) {
       return switch (status.getCode()) {
            case "NO_ACCOUNT" -> 0;
            case "DOWN" -> 1;
            case "OUT_OF_SERVICE" -> 2;
            case "UP" -> 3;
            default -> -1;
        };
    }
}
