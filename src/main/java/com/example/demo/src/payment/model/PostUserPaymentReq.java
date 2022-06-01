package com.example.demo.src.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.security.DenyAll;

@Getter
@Setter
@AllArgsConstructor
public class PostUserPaymentReq {
    private String cardNum;
    private int validThruMonth;
    private int validThruYear;
    private int cvc;
    private int password;
    private String cardName;
}
