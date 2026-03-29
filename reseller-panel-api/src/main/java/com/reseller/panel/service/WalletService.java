package com.reseller.panel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reseller.panel.dto.BalanceResponse;
import com.reseller.panel.dto.CreditResponse;
import com.reseller.panel.dto.DebitResponse;
import com.reseller.panel.dto.TransactionResponse;
import com.reseller.panel.dto.WalletDetailsResponse;
import com.reseller.panel.entity.Wallet;
import com.reseller.panel.entity.WalletTransaction;
import com.reseller.panel.entity.enums.Status;
import com.reseller.panel.entity.enums.TransactionType;
import com.reseller.panel.exception.BadRequestException;
import com.reseller.panel.exception.ResourceNotFoundException;
import com.reseller.panel.repository.WalletRepository;
import com.reseller.panel.repository.WalletTransactionRepository;
import com.reseller.panel.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepo;
    private final WalletTransactionRepository txnRepo;

   
    @Transactional
    public CreditResponse credit(Long walletId, Double amount) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepo.save(wallet);

        txnRepo.save(WalletTransaction.builder()
                .wallet(wallet)
                .type(TransactionType.CREDIT)
                .amount(amount)
                .status(Status.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build());

        return new CreditResponse(walletId, amount);
    }

   
    @Transactional(noRollbackFor = BadRequestException.class)
    public DebitResponse debit(Long walletId, Double amount) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

      
        if (wallet.getBalance() < amount) {

            txnRepo.save(WalletTransaction.builder()
                    .wallet(wallet)
                    .type(TransactionType.DEBIT)
                    .amount(amount)
                    .status(Status.FAILED)
                    .createdAt(LocalDateTime.now())
                    .build());

            throw new BadRequestException("Insufficient balance");
        }

    
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepo.save(wallet);

        txnRepo.save(WalletTransaction.builder()
                .wallet(wallet)
                .type(TransactionType.DEBIT)
                .amount(amount)
                .status(Status.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build());

        return new DebitResponse(walletId, amount);
    }
    
    public void saveFailedTransaction(Long walletId, Double amount) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        txnRepo.save(WalletTransaction.builder()
                .wallet(wallet)
                .type(TransactionType.DEBIT)
                .amount(amount)
                .status(Status.FAILED)
                .createdAt(LocalDateTime.now())
                .build());

        logger.info("Failed txn saved: walletId={}", walletId);
    }  
    
    public BalanceResponse getBalanceByUserId(Long userId) {

        Wallet wallet = walletRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        return new BalanceResponse(
                userId,
                wallet.getUser().getUsername(),
                wallet.getBalance()
        );
    } 
    public ApiResponse<List<TransactionResponse>> getAllTransactionsByWalletId(Long walletId) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        List<TransactionResponse> list = txnRepo.findByWalletId(wallet.getId())
                .stream()
                .map(txn -> new TransactionResponse(
                        txn.getId(),
                        txn.getAmount(),
                        txn.getType().name(),
                        txn.getStatus().name(),
                        txn.getCreatedAt()
                ))
                .toList();

        return ApiResponse.success(list, "Transactions fetched successfully");
    }
    
    
    public WalletDetailsResponse getWalletDetailsByUserId(Long userId) {

        Wallet wallet = walletRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        List<TransactionResponse> txns = txnRepo.findByWalletId(wallet.getId())
                .stream()
                .map(txn -> new TransactionResponse(
                        txn.getId(),
                        txn.getAmount(),
                        txn.getType().name(),
                        txn.getStatus().name(),
                        txn.getCreatedAt()
                ))
                .toList();

        return new WalletDetailsResponse(userId, wallet.getBalance(), txns);
    }
}    