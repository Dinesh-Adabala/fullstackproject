package com.neoteric.fullstackdemo_31._8._4.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Atm {
    private String cardNumber;
    private String cvv;
    private String accountNumber;
    private String pin;
    private Date cardExpiry;
}
