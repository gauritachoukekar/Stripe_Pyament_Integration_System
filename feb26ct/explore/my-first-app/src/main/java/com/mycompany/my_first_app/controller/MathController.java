package com.mycompany.my_first_app.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.my_first_app.service.interfaces.AddService;
import com.mycompany.my_first_app.service.interfaces.SubService;

@RestController
public class MathController {
	private static final Logger logger = LoggerFactory.getLogger(MathController.class);

    private final AddService addService;
    private final SubService subService;

    public MathController(AddService addService, SubService subService) {
        this.addService = addService;
        this.subService = subService;
    }

    @GetMapping("/add")
    public int addGet(@RequestParam int val1,
                   @RequestParam int val2) {
    	logger.info("addGet called with parameters: val1={}, val2={}", val1, val2);
    	logger.info("Calling AddService to perform addition");
        int result = addService.add(val1, val2);
    	logger.info("Result of addition: {}", result);
        return result;
    }
    @GetMapping("/subtract")
    public int subtractGet(@RequestParam int val1,
			   @RequestParam int val2) {
		logger.info("subtractGet called with parameters: val1={}, val2={}", val1, val2);
		logger.info("Calling SubService to perform subtraction");
		int result = subService.sub(val1, val2);
		logger.info("Result of subtraction: {}", result);
		return result;
	}
    @GetMapping("/addMultiple")
    public int addMultipleGet(@RequestParam int[] values) {
		logger.info("addMultipleGet called with parameters: values={}", values);
		logger.info("Calling AddService to perform addition of multiple values");
		int result = 0;
		for (int value : values) {
			result = addService.add(result, value);
		}
		logger.info("Result of addition of multiple values: {}", result);
		return result;
	}
}
