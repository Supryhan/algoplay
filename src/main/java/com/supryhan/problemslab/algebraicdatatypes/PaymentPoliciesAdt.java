package com.supryhan.problemslab.algebraicdatatypes;

import java.util.Locale;
import java.util.function.Function;

public class PaymentPoliciesAdt {

    private PaymentPoliciesAdt() {
    }

    public enum RetryDecision {
        DONE,
        DO_NOT_RETRY,
        RETRY
    }

    public static final Function<PaymentResultAdt, RetryDecision> RETRY_POLICY =
            result -> {

                if (result instanceof PaymentResultAdt.Success) {
                    return RetryDecision.DONE;
                }

                if (result instanceof PaymentResultAdt.Declined) {
                    return RetryDecision.DO_NOT_RETRY;
                }

                if (result instanceof PaymentResultAdt.TechnicalError error) {
                    return error.message()
                            .toLowerCase(Locale.ROOT)
                            .contains("timeout")
                            ? RetryDecision.RETRY
                            : RetryDecision.DO_NOT_RETRY;
                }

                throw new IllegalStateException(
                        "Unknown PaymentResultAdt: " + result
                );
            };
}
