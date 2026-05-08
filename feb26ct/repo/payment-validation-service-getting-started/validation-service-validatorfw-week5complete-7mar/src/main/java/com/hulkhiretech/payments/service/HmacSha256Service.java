package com.hulkhiretech.payments.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.exception.HmacValidationException;
import com.hulkhiretech.payments.constant.PaymentConstants;

@Service
public class HmacSha256Service {

    private String secretKey = "THIS_IS_MY_SECRET"; // deterministic key for testing

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String generateHmac(String jsonInput, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] signatureBytes = mac.doFinal(jsonInput.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new HmacValidationException("Error computing HMAC: " + e.getMessage());
        }
    }

    public boolean verifyHmac(String payloadJson, String headerHmac) {
        String generatedHmac = generateHmac(payloadJson, secretKey);
        return generatedHmac.equals(headerHmac);
    }
}