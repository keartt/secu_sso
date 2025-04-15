package com.springboot.sso_main;

import com.springboot.sso_main.test.BeforeService;
import com.springboot.sso_main.test.ProgressStore;
import com.springboot.sso_main.test.TaskProgress;
import com.springboot.sso_main.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    BeforeService service;
    @Autowired
    ProgressStore progressStore;

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 로그인 사용자: " + authentication.getName());
        return "index";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // SecurityContextLogoutHandler를 사용하여 로그아웃 처리
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);

        // 로그아웃 후 리디렉션할 URL
        return "redirect:/login";
    }
    
    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body(service.testMethod1());
    }
    
    @GetMapping("/test/chk")
    @ResponseBody
    public ResponseEntity<?> test2(@RequestParam String taskId) {
        TaskProgress progress = progressStore.get(taskId);
        return ResponseEntity.ok().body(progress);
    }
}
