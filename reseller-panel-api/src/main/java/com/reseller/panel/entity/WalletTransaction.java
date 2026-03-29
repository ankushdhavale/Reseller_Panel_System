package com.reseller.panel.entity;

import java.time.LocalDateTime;

import com.reseller.panel.entity.enums.Status;
import com.reseller.panel.entity.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "wallet_id", nullable = false)
	@NotNull(message = "Wallet is required")
	private Wallet wallet;
	
	
	@Column(nullable = false)
	@NotNull(message = "Amount is required")
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Transaction type is required")
	private TransactionType type;   
	
	@Enumerated(EnumType.STRING)
	private Status status; 
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist(){
		this.createdAt = LocalDateTime.now();
	}
}  
