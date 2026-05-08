package com.hulkhiretech.payments.repository.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.hulkhiretech.payments.constant.ErrorCodeEnum;
import com.hulkhiretech.payments.entity.MerchantPaymentRequestEntity;
import com.hulkhiretech.payments.exception.PaymentValidationException;
import com.hulkhiretech.payments.repository.interfaces.MerchantPaymentRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MerchantPaymentRequestRepositoryImpl
        implements MerchantPaymentRequestRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_SQL = """
            INSERT INTO merchant_payment_request
            (endUserID, merchantTxnReference, transactionRequest)
            VALUES (:endUserID, :merchantTxnReference, :transactionRequest)
            """;

    @Override
    public int saveMerchantPaymentRequest(
            MerchantPaymentRequestEntity entity) {

        log.info("Saving merchant payment request entity : {}", entity);

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("endUserID", entity.getEndUserID());
        params.addValue("merchantTxnReference",
                entity.getMerchantTxnReference());
        params.addValue("transactionRequest",
                entity.getTransactionRequest());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {

            namedParameterJdbcTemplate.update(
                    INSERT_SQL,
                    params,
                    keyHolder,
                    new String[]{"id"} // auto generated column
            );

            Number generatedId = keyHolder.getKey();

            if (generatedId != null) {

                log.info(
                        "Merchant payment request inserted with id : {}",
                        generatedId);

                return generatedId.intValue();
            }

            log.error(
                    "Failed to retrieve generated id after insert : {}",
                    entity);

            throw new PaymentValidationException(
                    ErrorCodeEnum.FAILED_TO_SAVE_PAYMENT_REQUEST.getErrorCode(),
                    ErrorCodeEnum.FAILED_TO_SAVE_PAYMENT_REQUEST.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        }
        catch (DuplicateKeyException ex) {

            log.error(
                    "Duplicate merchantTxnReference detected : {}",
                    entity.getMerchantTxnReference(),
                    ex
            );

            return -1;
        }
        catch (Exception ex) {

            log.error(
                    "Unexpected error while saving merchant payment request",
                    ex);

            throw new PaymentValidationException(
                    ErrorCodeEnum.FAILED_TO_SAVE_PAYMENT_REQUEST.getErrorCode(),
                    ErrorCodeEnum.FAILED_TO_SAVE_PAYMENT_REQUEST.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public int countRequestsForUserInLastMinutes(
            String endUserId,
            int minutes) {

        Instant startTime =
                Instant.now().minus(minutes, ChronoUnit.MINUTES);

        String sql = """
                SELECT COUNT(*)
                FROM merchant_payment_request
                WHERE endUserID = :endUserId
                AND creationDate >= :startTime
                """;

        Map<String, Object> params = Map.of(
                "endUserId", endUserId,
                "startTime", Timestamp.from(startTime)
        );

        Integer count =
                namedParameterJdbcTemplate.queryForObject(
                        sql,
                        params,
                        Integer.class
                );

        return count != null ? count : 0;
    }

}