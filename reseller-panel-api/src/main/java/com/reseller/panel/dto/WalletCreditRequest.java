package com.reseller.panel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WalletCreditRequest {

	@NotNull(message = "Wallet ID is required")
	private Long walletId;
	
	@NotNull(message = "Amount must be greater than 0")
	private Double amount;
}
