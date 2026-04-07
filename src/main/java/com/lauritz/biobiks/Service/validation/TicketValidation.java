package com.lauritz.biobiks.Service.validation;

import org.springframework.stereotype.Component;

@Component
public class TicketValidation {

    public ValidationResult validateTicketQuantity(int quantity) {
        ValidationResult result = new ValidationResult();

        if (quantity < 1) {
            result.addError("Du skal mindst vælge 1 billet.");
        }
        if (quantity > 10) {
            result.addError("Du kan maksimalt købe 10 billetter til samme forestilling ad gangen.");
        }

        return result;
    }
}
