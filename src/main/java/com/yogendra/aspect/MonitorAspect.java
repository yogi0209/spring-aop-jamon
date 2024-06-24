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
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MonitorAspect {

    private final MonitorFactoryInterface monitorFactoryInterface;
    private final MeterRegistry meterRegistry;

    public MonitorAspect(MonitorFactoryInterface monitorFactoryInterface, MeterRegistry meterRegistry) {
        this.monitorFactoryInterface = monitorFactoryInterface;
        this.meterRegistry = meterRegistry;
    }


    @Around(value = "controllerMethod() && @annotation(methodMonitor)")
    public Object monitorMethodExecution(ProceedingJoinPoint joinPoint, MethodMonitor methodMonitor) throws Throwable {
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

    @Pointcut("execution(* com.yogendra.controller.*.*())")
    public void controllerMethod() {
    }

}
