package com.reseller.panel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@Entity
@Table(name = "wallet")
public class Wallet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = true, nullable = false)
	private User user;
	
	@Column(nullable = false)
	private Double balance;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime creatAt;
	
	private LocalDateTime updatedAt;
	 
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	@PrePersist
	public void prePersist() {
	    this.updatedAt = LocalDateTime.now();
	}
}
