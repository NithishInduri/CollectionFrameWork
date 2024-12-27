package com.insurence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurence.entity.CaseWorker;

public interface CaseWorkerRepo extends JpaRepository<CaseWorker, Integer> {
	public CaseWorker findByEmail(String email);
}
