package com.reseller.panel.entity; 
import java.time.LocalDateTime;

import com.reseller.panel.entity.enums.Status;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiName;

    @Column(columnDefinition = "TEXT")
    private String request;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Enumerated(EnumType.STRING)   
    private Status status; 

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

