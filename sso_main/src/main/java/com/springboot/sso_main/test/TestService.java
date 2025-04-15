package com.springboot.sso_main.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    ProgressStore progressStore;
    
    @Async
    public void testMethod1(String taskId) {
        // dummy
        List<String> array = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            array.add(String.valueOf(i) + "번째 값");
        }

        progressStore.init(taskId, array.size());
        
        // do something
        for (String s : array) {
            try {
                Thread.sleep(1000); // 1초 대기 (1000ms)
                System.out.println(s);
                progressStore.increment(taskId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 예외 발생 시 인터럽트 플래그 재설정
            }
        }
    }
}
