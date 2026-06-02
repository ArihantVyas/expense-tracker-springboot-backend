package com.arihant.expense_tracker.controller;

import com.arihant.expense_tracker.config.ServerPortLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ServerPortLogger logger;

    // We need only instance of the logger for all instances of this class , so we use final .
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping
    public String testMethod(){
        log.info("Request received at /test to check app status. "+"App running on port :"+logger.getPort());
        return "App running on port : "+logger.getPort();
    }
}
