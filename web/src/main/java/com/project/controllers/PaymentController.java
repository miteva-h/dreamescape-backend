package com.project.controllers;

import com.project.domain.dto.ChargeRequest;
import com.project.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final StripeService stripeService;

    @PostMapping("/payment")
    public Charge createCharge(@RequestBody ChargeRequest chargeRequest) throws StripeException {
        return stripeService.createCharge(chargeRequest);
    }
}