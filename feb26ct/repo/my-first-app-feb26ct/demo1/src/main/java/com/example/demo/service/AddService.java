package com.example.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class AddService {

    public int add(int a, int b){
        return a + b;
    }

    @PostConstruct
    public void init(){
        System.out.println("AddService Bean Initialized");
    }
}