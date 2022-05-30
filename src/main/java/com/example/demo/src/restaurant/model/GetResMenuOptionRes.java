package com.example.demo.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetResMenuOptionRes {
    private int optionKindId;
    private String optionKindName;
    private Boolean isEssential;
//    private List<Integer> optionId;
//    private List<String> optionName;
//    private List<Integer> optionPrice;
    private List<OptionInfo> optionInfoList;
}
