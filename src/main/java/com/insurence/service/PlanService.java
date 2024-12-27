package com.insurence.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insurence.entity.Plan;
import com.insurence.model.PlanCategoryModel;
import com.insurence.model.PlanModel;

@Service
public interface PlanService {

	public PlanCategoryModel createPlanCategory(PlanCategoryModel PlanCategoryModel);

	public Map<Integer, String> getPlanCategories();

	public PlanModel savePlan(PlanModel planModel);

	public List<PlanModel> getAllPlans();

	public PlanModel getPlanById(Integer id);

	public boolean updatePlan(Plan plan);

	public String deleteById(Integer id);

	public String planStatusChange(Integer id, String activeSwitch);

	public Map<Integer, String> getAllCategoryPlans();
	}
