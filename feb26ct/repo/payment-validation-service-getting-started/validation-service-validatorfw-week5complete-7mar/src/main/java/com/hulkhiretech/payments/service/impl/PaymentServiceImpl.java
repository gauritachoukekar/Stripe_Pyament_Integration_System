package com.hulkhiretech.payments.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.constant.ValidatorRuleEnum;
import com.hulkhiretech.payments.pojo.PaymentRequest;
import com.hulkhiretech.payments.service.interfaces.BusinessValidator;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	@Value("${validator.rule-names}")
	private String validatorRuleNames;
	
	private final ApplicationContext applicationContext;
	
    @Override
    public String validateAndCreatePayment(PaymentRequest paymentRequest) {
        log.info("Validating and creating payment: {}", paymentRequest);
       
        
        // split the validatorRuleNames into list and iterate each rule
        String[] rules = validatorRuleNames.split(",");
        for (String rule : rules) {
        	log.info("Applying validation rule: {}", rule);
        	
        	Optional<Class<? extends BusinessValidator>> validatorClass = ValidatorRuleEnum.getValidatorClassByRule(rule.trim());
        	if(!validatorClass.isPresent()) {
        		log.warn("No validator found for rule: {}", rule);
        		continue;
        	}
        	
        	// load the validator bean from application context
        	BusinessValidator validator = applicationContext.getBean(
        			validatorClass.get());
        	
        	if(validator == null) {
				log.warn("No bean found for validator class: {}", 
						validatorClass.get().getName());
				continue;
			}
        	
        	// call the validate method of the validator
			validator.validate(paymentRequest);
        }
        
        log.info("All validations passed for payment request: {}", 
        		paymentRequest);
        
        // Code to invoke processing-service
        
        String result = "From Service Payment created successfully! \n" + paymentRequest;
        log.info("Payment creation result: {}", result);
        return result;
    }
    
    // init method with postconstruct and log validatorRuleNames
    @PostConstruct
    public void init() { 
    	log.info("PaymentServiceImpl initialized with "
    			+ "validatorRuleNames: {}", validatorRuleNames);
    }
}
