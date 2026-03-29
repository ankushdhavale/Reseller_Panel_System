	package com.reseller.panel.service;
	import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reseller.panel.dto.ApiRequest;
import com.reseller.panel.entity.ApiLog;
import com.reseller.panel.entity.Wallet;
import com.reseller.panel.entity.enums.Status;
import com.reseller.panel.repository.ApiLogRepository;
import com.reseller.panel.repository.UserRepository;
import com.reseller.panel.repository.WalletRepository;

import lombok.RequiredArgsConstructor;
	
	@Service
	@RequiredArgsConstructor
	public class ApiService {
	
	    private final RestTemplate restTemplate;
	    private final WalletService walletService;
	    private final ApiLogRepository apiLogRepo;
	    private final UserRepository userRepository; 
	    private final WalletRepository walletRepository;
	
	    @Value("${api.token}")
	    private String TOKEN;
	
	    @Value("${api.base.url}")
	    private String BASE_URL;
	
	    public Object processApi(Long walletId, ApiRequest apiRequest) {
	

	        Double totalAmount = apiRequest.getAmount();
	
	        String url = BASE_URL + "/posts";
	
//	        User user = userRepository.findById(walletId)
//	                .orElseThrow(() -> new RuntimeException("User not found")); 
	        
	        Wallet wallet = walletRepository.findById(walletId)
	                .orElseThrow(() -> new RuntimeException("Wallet not found"));
	        walletService.debit(walletId, totalAmount);
	
	        Map<String, Object> body = new HashMap<>();
	        body.put("mobileNumber", apiRequest.getMobileNumber());
	        body.put("operator", apiRequest.getOperator());
	        body.put("amount", apiRequest.getAmount());
	
	        try {
	            HttpHeaders headers = new HttpHeaders();
	            headers.set("Authorization", "Bearer " + TOKEN);
	            headers.setContentType(MediaType.APPLICATION_JSON);
	
	            ResponseEntity<Object> response = restTemplate.exchange(
	                    url,
	                    HttpMethod.POST,
	                    new HttpEntity<>(body, headers),
	                    Object.class
	            );
	
	            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
	                throw new RuntimeException("Recharge Failed from API");
	            }
	
	            Object responseBody = response.getBody();
	
	            apiLogRepo.save(ApiLog.builder()
	                    .apiName("recharge")
	                    .request(body.toString())
	                    .response(responseBody.toString())
	                    .status(Status.SUCCESS)
	                    .build());
	
	            return responseBody;
	
	        } catch (Exception ex) {
	
	            
	            walletService.credit(walletId, totalAmount);
	
	            apiLogRepo.save(ApiLog.builder()
	                    .apiName("recharge")
	                    .request(body.toString())
	                    .response(ex.getMessage())
	                    .status(Status.FAILED)
	                    .build());
	
	            throw new RuntimeException("Recharge Failed: " + ex.getMessage());
	        }
	    }
	}
