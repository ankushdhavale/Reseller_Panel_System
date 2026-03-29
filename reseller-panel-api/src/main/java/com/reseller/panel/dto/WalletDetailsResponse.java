package com.reseller.panel.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletDetailsResponse {

    private Long userId;
    private Double balance;
    private List<TransactionResponse> transactions;
}

