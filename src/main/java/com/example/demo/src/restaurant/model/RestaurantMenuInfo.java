package com.example.demo.src.restaurant.model;

import com.example.demo.src.cart.model.ResOrderMenuInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantMenuInfo {
    private int restaurantId;
    private String restaurantName;
    private boolean isCheetah;
//    private List<ResOrderMenuInfo> resOrderMenuInfo;

}
