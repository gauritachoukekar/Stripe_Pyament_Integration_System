DROP DATABASE IF EXISTS validations;

DROP USER IF EXISTS 'validations_user'@'%';

-- Creates database
CREATE DATABASE validations;

-- Select database
USE validations;

-- Creates user
CREATE USER 'validations_user'@'%' IDENTIFIED BY 'P9v@tX3#nLz!Q8wK';

-- Grant permissions
GRANT SELECT, INSERT, UPDATE, DELETE ON validations.* TO 'validations_user'@'%';

FLUSH PRIVILEGES;

-- Create Tables

CREATE TABLE merchant_payment_request (
 id INT NOT NULL AUTO_INCREMENT,
 endUserID VARCHAR(100),
 merchantTxnReference VARCHAR(100) NOT NULL,
 transactionRequest TEXT DEFAULT NULL,
 creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (id),
 UNIQUE KEY (merchantTxnReference)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE validation_rules (
 id INT NOT NULL AUTO_INCREMENT,
 validatorName VARCHAR(50) NOT NULL,
 isActive BOOLEAN NOT NULL,
 priority SMALLINT NOT NULL,
 creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (id),
 UNIQUE KEY (validatorName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE validation_rules_params (
 id INT NOT NULL AUTO_INCREMENT,
 validatorName VARCHAR(50) NOT NULL,
 paramName VARCHAR(200) NOT NULL,
 paramValue VARCHAR(200) NOT NULL,
 creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (id),
 FOREIGN KEY (validatorName)
 REFERENCES validation_rules (validatorName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;