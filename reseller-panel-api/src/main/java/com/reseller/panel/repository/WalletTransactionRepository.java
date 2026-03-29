package com.reseller.panel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reseller.panel.entity.WalletTransaction;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    
    List<WalletTransaction> findByWalletId(Long walletId);

  
    List<WalletTransaction> findByWalletIdAndType(Long walletId, com.reseller.panel.entity.enums.TransactionType type);
}
 