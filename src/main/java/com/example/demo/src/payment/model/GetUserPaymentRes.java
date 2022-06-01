package com.example.demo.src.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserPaymentRes {
    private int cardId;
    private String cardName;
    private String cardNum;
    private int userId;
}
