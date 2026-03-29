package com.reseller.panel.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private Double amount;
    private String type;
    private String status;
    private LocalDateTime createdAt;
}