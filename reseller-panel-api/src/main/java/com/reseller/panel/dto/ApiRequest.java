package com.reseller.panel.dto;

import lombok.Data;

@Data
public class ApiRequest {
    private String mobileNumber;
    private String operator;
    private Double amount;

}