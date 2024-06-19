package com.yogendra.controller;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactoryInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("monitor")
public class MonitorController {


    private final MonitorFactoryInterface monitorFactoryInterface;

    public MonitorController(MonitorFactoryInterface monitorFactoryInterface) {
        this.monitorFactoryInterface = monitorFactoryInterface;
    }

    @GetMapping("{name}")
    public ResponseEntity<String> getMonitorDetails(@PathVariable("name") String name) {
        Monitor monitor = monitorFactoryInterface.getMonitor(name, "ms.");
        return ResponseEntity.ok(monitor.toString());
    }
}
