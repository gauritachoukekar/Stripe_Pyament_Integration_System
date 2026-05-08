-- MUST have priority 0
INSERT INTO validations.validation_rules
(validatorName, isActive, priority)
VALUES
('DUPLICATE_TXN_RULE', true, 0);

-- Business validation priority 10
INSERT INTO validations.validation_rules
(validatorName, isActive, priority)
VALUES
('PAYMENT_ATTEMPT_THRESHOLD_RULE', true, 10);


-- Validation Parameters

INSERT INTO validations.validation_rules_params
(validatorName, paramName, paramValue)
VALUES
('PAYMENT_ATTEMPT_THRESHOLD_RULE', 'durationInMins', '2');

INSERT INTO validations.validation_rules_params
(validatorName, paramName, paramValue)
VALUES
('PAYMENT_ATTEMPT_THRESHOLD_RULE', 'maxPaymentThreshold', '5');