package com.hulkhiretech.payments.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.cache.ValidatorRuleCache;
import com.hulkhiretech.payments.constant.ErrorCodeEnum;
import com.hulkhiretech.payments.constant.ValidatorRuleEnum;
import com.hulkhiretech.payments.exception.PaymentValidationException;
import com.hulkhiretech.payments.pojo.PaymentRequest;
import com.hulkhiretech.payments.service.interfaces.BusinessValidator;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final ApplicationContext applicationContext;

	private final ValidatorRuleCache validatorRuleCache;

	@Override
	public String validateAndCreatePayment(
			PaymentRequest paymentRequest) {
		log.info("Validating and creating payment: {} ",
				paymentRequest);

		List<String> validatorRules = validatorRuleCache.getValidatorRules();
		log.debug("Loaded validator rules from cache: {}", validatorRules);
		
		// iterate configured validatorRules (loaded in init)
		if (validatorRules == null || validatorRules.isEmpty()) {
			log.error("No validator rules configured, skipping validations");
			throw new PaymentValidationException(
					ErrorCodeEnum.NO_VALIDATION_RULES_CONFIGURED.getErrorCode(),
					ErrorCodeEnum.NO_VALIDATION_RULES_CONFIGURED.getErrorMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		for (String rule : validatorRules) {
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
}