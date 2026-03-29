	package com.reseller.panel.repository;
	
	import java.util.Optional;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.reseller.panel.entity.Wallet;
	
	public interface WalletRepository extends JpaRepository<Wallet, Long> {
	
		   Optional<Wallet> findByUserId(Long userId);
	}
