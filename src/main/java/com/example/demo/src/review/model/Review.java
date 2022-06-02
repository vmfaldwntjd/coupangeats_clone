package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Review {
    private int reviewId;
    private int restaurantId;
    private int userId;
    private int starPoint;
    private String menuName;
    private String content;
}
