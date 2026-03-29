package com.reseller.panel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceResponse {

    private Long userId;
    private String username;
    private Double balance;
}