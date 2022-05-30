package com.example.demo.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetResMenuOptionRes {
    private int optionId;
    private String optionName;
    private Boolean isOptional;
    private int resOptionId;
    private List<String> optionListName;
    private List<Integer> optionListPrice;
}
