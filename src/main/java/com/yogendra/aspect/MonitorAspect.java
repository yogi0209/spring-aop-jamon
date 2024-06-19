package com.yogendra.aspect;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactoryInterface;
import com.yogendra.annotation.MethodMonitor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class MonitorAspect {

    private final MonitorFactoryInterface monitorFactoryInterface;
    private final MeterRegistry meterRegistry;

    public MonitorAspect(MonitorFactoryInterface monitorFactoryInterface, MeterRegistry meterRegistry) {
        this.monitorFactoryInterface = monitorFactoryInterface;
        this.meterRegistry = meterRegistry;
    }


    @Around("@annotation(com.yogendra.annotation.MethodMonitor)")
    public Object monitorMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        MethodMonitor methodMonitor = method.getAnnotation(MethodMonitor.class);
        Monitor monitor = monitorFactoryInterface.start(methodMonitor.name());
        try {
            return joinPoint.proceed();
        } finally {
            monitor.stop();
            Counter.builder("http.requests.count")
                    .tag("uri", methodMonitor.uri())
                    .tag("method", methodMonitor.method())
                    .register(meterRegistry).increment();
            Gauge
                    .builder("http.response.time", monitor::getLastValue)
                    .tag("uri", methodMonitor.uri())
                    .tag("method", methodMonitor.method())
                    .register(meterRegistry);
        }
    }
}
