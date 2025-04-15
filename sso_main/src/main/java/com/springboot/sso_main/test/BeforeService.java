package com.springboot.sso_main.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeforeService {

    @Autowired
    TestService service;
    
    public String testMethod1(){
        String taskId = UUID.randomUUID().toString();

        service.testMethod1(taskId);
        return taskId;
    }
}
