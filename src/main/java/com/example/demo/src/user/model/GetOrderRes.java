package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderRes {
    private String orderId;
    private int restaurantId;
    private int userId;
    private String name;
    private String restaurantName;
    private int deliveryStatus;
    private int orderTotalPrice;
    private String url;
}
