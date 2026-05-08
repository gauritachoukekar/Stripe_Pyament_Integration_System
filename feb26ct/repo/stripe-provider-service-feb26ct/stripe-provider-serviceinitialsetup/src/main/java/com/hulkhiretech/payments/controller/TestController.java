package com.hulkhiretech.payments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.hulkhiretech.payments.pojo.TestRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger =
            LoggerFactory.getLogger(TestController.class);

    @GetMapping("/one")
    public String myMethod() {
        logger.info("inside myMethod of TestController");
        return "Hello from TestController myMethod()!";
    }

    @GetMapping("/two")
    public String myMethod2() {
        logger.info("inside myMethod2 of TestController");
        return "Hello from TestController myMethod2()!";
    }

    @PostMapping("/post/hello/{pv1}/{pv2}/world/789")
    public String myMethodPost(
            @RequestParam("value1") int val1,
            @RequestParam int val2,
            @PathVariable("pv1") String pathVar1,
            @PathVariable("pv2") String pathVar2,
            @RequestHeader("my-header") String myHeader,
            @RequestBody TestRequest testRequest) {

        logger.info("Inside myMethodPost "
                + "val1:{}|val2:{}|pathVar1:{}|pathVar2:{}|my-header:{}|testRequest:{}",
                val1, val2, pathVar1, pathVar2, myHeader, testRequest);

        int sum = testRequest.getNum1() + testRequest.getNum2();

        return "Hello from TestController myMethodPost() "
                + "\n val1:" + val1
                + "\n val2:" + val2
                + "\n pathVar1:" + pathVar1
                + "\n pathVar2:" + pathVar2
                + "\n my-header:" + myHeader
                + "\n testRequest:" + testRequest
                + "\n sum:" + sum;
    }

    @PostMapping("/two")
    public String myMethodPost2() {
        logger.info("inside myMethodpost2 of TestController");
        return "Hello from TestController myMethodpost2()!";
    }
}