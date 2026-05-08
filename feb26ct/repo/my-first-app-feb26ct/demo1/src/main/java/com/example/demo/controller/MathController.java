package com.example.demo.controller;

import com.example.demo.service.AddService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MathController {

    private final AddService addService;

    @Value("${app.name}")
    private String appName;

    @GetMapping("/add")
    public int add(@RequestParam int a, @RequestParam int b){
        return addService.add(a,b);
    }

    @GetMapping("/app")
    public String getAppName(){
        return appName;
    }

}