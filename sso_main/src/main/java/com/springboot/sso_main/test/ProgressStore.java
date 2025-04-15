package com.springboot.sso_main.test;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProgressStore {
    private final Map<String, TaskProgress> progressMap = new ConcurrentHashMap<>();

    public void init(String taskId, int total) {
        progressMap.put(taskId, new TaskProgress(total, 0, false));
    }

    public void increment(String taskId) {
        TaskProgress progress = progressMap.get(taskId);
        progress.setCompleted(progress.getCompleted() + 1);
    }

    public void finish(String taskId) {
        TaskProgress progress = progressMap.get(taskId);
        progress.setDone(true);
    }

    public TaskProgress get(String taskId) {
        return progressMap.get(taskId);
    }
}
