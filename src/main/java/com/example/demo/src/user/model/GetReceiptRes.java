package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReceiptRes {
    private int receiptId;
    private int orderId;
    private int orderTotalPrice;
    private String menuName;
    private String payInfo;
    private int discountFee;
    private String payBy;
    private int deliveryFee;
    private int orderPrice;
    private String deliveryAddress;
    private int userId;
}
