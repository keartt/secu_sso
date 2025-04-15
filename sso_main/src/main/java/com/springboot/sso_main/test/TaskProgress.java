package com.springboot.sso_main.test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskProgress {
    private int total;
    private int completed;
    private boolean done;
}
