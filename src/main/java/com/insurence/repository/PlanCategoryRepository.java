package com.insurence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurence.entity.PlanCategory;

@Repository
public interface PlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {

}
