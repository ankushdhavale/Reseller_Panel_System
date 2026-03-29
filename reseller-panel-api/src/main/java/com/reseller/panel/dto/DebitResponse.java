package com.reseller.panel.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitResponse {

    private Long walletId;
    private Double amount;
}