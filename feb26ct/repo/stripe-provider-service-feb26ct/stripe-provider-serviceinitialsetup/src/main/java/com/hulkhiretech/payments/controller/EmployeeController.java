package com.hulkhiretech.payments.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.pojo.CreateEmpReq;
import com.hulkhiretech.payments.pojo.EmployeeResponse;

@RestController
@RequestMapping("v1/employees")
public class EmployeeController {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping
    public EmployeeResponse createEmployee(@RequestBody CreateEmpReq createEmpReq) {

        logger.info("Inside createEmployee method, createEmpReq: {}", createEmpReq);
        EmployeeResponse empResponse = new EmployeeResponse();
        empResponse.setId("emp123");
        logger.info("Employee created with ID: {}", empResponse);

        return empResponse;
    }
    
    @GetMapping("/{empId}")
     public EmployeeResponse getEmployeeById(@PathVariable String empId) {
    	logger.info("Inside getEmployeeById method, empId: {}", empId);
    	EmployeeResponse empResponse = new EmployeeResponse();
    	empResponse.setId(empId);
		logger.info("Employee retrieved with ID: {}", empResponse);

		return empResponse;
	}
    @GetMapping
    public List<EmployeeResponse> getAllEmployee() {
    			logger.info("Inside getAllEmployee method");
    			List<EmployeeResponse> empList = List.of(
						new EmployeeResponse());
    			EmployeeResponse empResponse = new EmployeeResponse();
    			empResponse.setId("id1");
    			logger.info("Employee list retrieved: {}", empList);
    			return empList;
    }
    
}