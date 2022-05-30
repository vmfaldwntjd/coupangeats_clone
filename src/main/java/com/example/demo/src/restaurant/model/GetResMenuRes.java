package com.example.demo.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetResMenuRes {
    private int menuId;
    private String menuName;
    private int menuPrice;
    private List<String> menuImageUrlList;
    private String menuDescription;
}
