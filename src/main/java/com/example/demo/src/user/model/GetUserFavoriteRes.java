package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFavoriteRes {
    private int favoriteId;
    private int restaurantId;
    private int imageId;
    private String restaurantName;
    private int isCheetah;
    private int starPoint;
    private int deliveryTime;
    private int deliveryFee;
    private int isPackable;
}
