package com.supryhan.problemslab.algebraicdatatypes;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class PaymentServiceAdt {

    public List<PaymentResultAdt> processPayments() {

        List<PaymentResultAdt> payments = new ArrayList<>();

        payments.add(
                new PaymentResultAdt.Success("TX-100")
        );

        payments.add(
                new PaymentResultAdt.Declined("Insufficient funds")
        );

        payments.add(
                new PaymentResultAdt.TechnicalError("Network timeout")
        );

        payments.add(
                new PaymentResultAdt.TechnicalError("Database unavailable")
        );

        payments.add(
                new PaymentResultAdt.Success("TX-200")
        );

        return payments;
    }
}