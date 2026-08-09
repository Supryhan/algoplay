package com.supryhan.problemslab.algebraicdatatypes;

public sealed interface PaymentResultAdt
        permits PaymentResultAdt.Success,
        PaymentResultAdt.Declined,
        PaymentResultAdt.TechnicalError {

    record Success(String transactionId)
            implements PaymentResultAdt {
    }

    record Declined(String reason)
            implements PaymentResultAdt {
    }

    record TechnicalError(String message)
            implements PaymentResultAdt {
    }
}

