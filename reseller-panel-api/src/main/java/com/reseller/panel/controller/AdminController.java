package com.reseller.panel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reseller.panel.dto.ApiRequest;
import com.reseller.panel.dto.CreditResponse;
import com.reseller.panel.dto.DebitResponse;
import com.reseller.panel.dto.UserRequest;
import com.reseller.panel.dto.UserResponse;
import com.reseller.panel.dto.WalletCreditRequest;
import com.reseller.panel.dto.WalletDebitRequest;
import com.reseller.panel.dto.WalletDetailsResponse;
import com.reseller.panel.response.ApiResponse;
import com.reseller.panel.service.ApiService;
import com.reseller.panel.service.UserService;
import com.reseller.panel.service.WalletService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final WalletService walletService;	
    private final ApiService apiService;
    
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok(
                ApiResponse.success(response, "User created successfully")
        );
    }
    
    @PostMapping("/wallet/credit")
    public ResponseEntity<ApiResponse<CreditResponse>> credit(
    		@Valid @RequestBody WalletCreditRequest request) {
    	
    	CreditResponse response = walletService.credit(
    			request.getWalletId(),
    			request.getAmount()
    			);
    	
    	return ResponseEntity.ok(
    			ApiResponse.success(response, "Amount credited successfully")
    			);
    }

    @PostMapping("/wallet/debit")
    public ResponseEntity<ApiResponse<DebitResponse>> debit(
	        @Valid @RequestBody WalletDebitRequest request) {

	    DebitResponse response = walletService.debit(
	            request.getWalletId(),
	            request.getAmount()
	    );

	    return ResponseEntity.ok(
	            ApiResponse.success(response, "Amount debited successfully")
	    );
	}	
    
    
    @GetMapping("/reseller/{userId}/details")
	public ResponseEntity<ApiResponse<WalletDetailsResponse>> getWalletDetails(
	        @PathVariable("userId") Long userId) {

	    WalletDetailsResponse data = walletService.getWalletDetailsByUserId(userId);

	    return ResponseEntity.ok(
	            ApiResponse.success(data, "Wallet details fetched successfully")
	    );
	}
    
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("id") Long id) {

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.success(user, "User fetched successfully")
        );
    }
    
    @PostMapping("/recharge")
    public ResponseEntity<ApiResponse<Object>> callApi(
            @RequestParam("walletId") Long walletId,
            @RequestBody ApiRequest request) {

    	Object response = apiService.processApi(walletId, request);
    	return ResponseEntity.ok(ApiResponse.success(response));
    }
}