package com.supryhan.problemslab.algebraicdatatypes;

import com.supryhan.App;

import java.util.List;

import static com.supryhan.problemslab.algebraicdatatypes.PaymentResultAdt.*;
import static com.supryhan.problemslab.algebraicdatatypes.PaymentPoliciesAdt.RETRY_POLICY;
import static com.supryhan.problemslab.algebraicdatatypes.PaymentPoliciesAdt.RetryDecision.RETRY;

public class PaymentResponseMapperAdt extends App {

    public static String toResponse(PaymentResultAdt result) {

        if (result instanceof Success success) {
            return "HTTP 200: transaction=" + success.transactionId();
        }

        if (result instanceof Declined declined) {
            return "HTTP 422: declined: " + declined.reason();
        }

        if (result instanceof TechnicalError error) {
            return "HTTP 500: " + error.message();
        }

        throw new IllegalStateException(
                "Unknown PaymentResultAdt: " + result
        );
    }

    PaymentServiceAdt paymentService =
            new PaymentServiceAdt();

    List<PaymentResultAdt> results =
            paymentService.processPayments();

    // Case 2: ADT + Stream + pattern matching
    List<String> successfulTransactionIds =
            results.stream()
                    .<String>mapMulti((result, downstream) -> {
                        if (result instanceof Success success) {
                            downstream.accept(
                                    success.transactionId()
                            );
                        }
                    })
                    .toList();

    // Case 3: ADT + lambda + policy
    List<PaymentResultAdt> retryablePayments =
            results.stream()
                    .filter(result ->
                            RETRY_POLICY.apply(result) == RETRY
                    )
                    .toList();

    @Override
    protected void run() {

        PaymentResultAdt result =
                new Success("TX-100500");

        String response =
                PaymentResponseMapperAdt.toResponse(result);

        System.out.println(response);

        System.out.println(
                "Successful transactions: " + successfulTransactionIds
        );

        System.out.println(
                "Retryable payments: " + retryablePayments
        );
    }

    public static void main(String[] args) {
        launch(new PaymentResponseMapperAdt());
    }
}
