package com.reseller.panel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reseller.panel.dto.BalanceResponse;
import com.reseller.panel.dto.TransactionResponse;
import com.reseller.panel.dto.WalletDetailsResponse;
import com.reseller.panel.response.ApiResponse;
import com.reseller.panel.service.UserService;
import com.reseller.panel.service.WalletService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/reseller")
@RequiredArgsConstructor
public class ResellerController {

    private final WalletService walletService;	

    @GetMapping("/{userId}/checkBal")
	public ResponseEntity<ApiResponse<BalanceResponse>> checkBalance(
	        @PathVariable("userId") Long userId) {
	    BalanceResponse data = walletService.getBalanceByUserId(userId);
	    return ResponseEntity.ok(
	            ApiResponse.success(data, "Balance fetched successfully")
	    );
	}
    
    @GetMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getMyTransactions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(walletService.getAllTransactionsByWalletId(id));
    }
    
    
    
    @GetMapping("/{userId}/details")
	public ResponseEntity<ApiResponse<WalletDetailsResponse>> getWalletDetails(
	        @PathVariable("userId") Long userId) {

	    WalletDetailsResponse data = walletService.getWalletDetailsByUserId(userId);

	    return ResponseEntity.ok(
	            ApiResponse.success(data, "Wallet details fetched successfully")
	    );
	}
}