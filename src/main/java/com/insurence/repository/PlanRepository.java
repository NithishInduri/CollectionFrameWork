package com.insurence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurence.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

	List<Plan> findByCategoryId(Integer categoryId);

}
