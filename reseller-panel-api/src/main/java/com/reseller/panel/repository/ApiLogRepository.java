package com.reseller.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reseller.panel.entity.ApiLog;

public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {

}