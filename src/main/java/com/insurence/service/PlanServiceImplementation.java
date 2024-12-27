package com.insurence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.insurence.entity.Plan;
import com.insurence.entity.PlanCategory;
import com.insurence.exceptions.PlanNotFoundException;
import com.insurence.model.PlanCategoryModel;
import com.insurence.model.PlanModel;
import com.insurence.repository.PlanCategoryRepository;
import com.insurence.repository.PlanRepository;

@Service
public class PlanServiceImplementation implements PlanService {

	private final PlanRepository planRepository;
	private final PlanCategoryRepository categoryRepository;

	public PlanServiceImplementation(PlanRepository planRepository, PlanCategoryRepository categoryRepository) {
		this.planRepository = planRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public PlanCategoryModel createPlanCategory(PlanCategoryModel planCategoryModel) {
		PlanCategory planCategory = new PlanCategory();
		planCategory.setCategoryName(planCategoryModel.getCategoryName());
		planCategory.setCreatedBy(planCategoryModel.getCreatedBy());
		planCategory.setCreatedDate(planCategoryModel.getCreatedDate());
		planCategory.setUpdatedBy(planCategoryModel.getUpdatedBy());
		planCategory.setUpdatedDate(planCategoryModel.getUpdatedDate());
		planCategory.setActiveSwitch(planCategoryModel.getActiveSwitch());

		PlanCategory savedPlanCategory = categoryRepository.save(planCategory);

		PlanCategoryModel pcModel = new PlanCategoryModel();
		pcModel.setActiveSwitch(savedPlanCategory.getActiveSwitch());
		pcModel.setCategoryName(savedPlanCategory.getCategoryName());
		pcModel.setCreatedBy(savedPlanCategory.getCreatedBy());
		pcModel.setCreatedDate(savedPlanCategory.getCreatedDate());
		pcModel.setUpdatedBy(savedPlanCategory.getUpdatedBy());
		pcModel.setUpdatedDate(savedPlanCategory.getUpdatedDate());

		return pcModel;
	}

	@Override
	public Map<Integer, String> getPlanCategories() {
		List<PlanCategory> categories = categoryRepository.findAll();
		Map<Integer, String> categoryMap = new HashMap<>();
		categories.forEach(category -> {
			categoryMap.put(category.getPlanCategoryId(), category.getCategoryName());
		});
		return categoryMap;
	}

	@Override
	public PlanModel savePlan(PlanModel planModel) {
		String categoryName = "Unknown Category";
		if (planModel.getCategoryId() != null) {
			Optional<PlanCategory> categoryOpt = categoryRepository.findById(planModel.getCategoryId());
			if (categoryOpt.isPresent()) {
				categoryName = categoryOpt.get().getCategoryName();
			}
		}

		Plan plan = new Plan();
		plan.setActiveSwitch(planModel.getActiveSwitch());
		plan.setCreatedBy(planModel.getCreatedBy());
		plan.setEndDate(planModel.getEndDate());
		plan.setPlanName(planModel.getPlanName());
		plan.setStartDate(planModel.getStartDate());
		plan.setUpdatedBy(planModel.getUpdatedBy());
		plan.setCategoryId(planModel.getCategoryId());

		Plan savedPlan = planRepository.save(plan);

		PlanModel newPlan = new PlanModel();
		newPlan.setActiveSwitch(savedPlan.getActiveSwitch());
		newPlan.setCreatedBy(savedPlan.getCreatedBy());
		newPlan.setEndDate(savedPlan.getEndDate());
		newPlan.setPlanName(savedPlan.getPlanName());
		newPlan.setStartDate(savedPlan.getStartDate());
		newPlan.setUpdatedBy(savedPlan.getUpdatedBy());
		newPlan.setCategoryId(savedPlan.getCategoryId());
		newPlan.setCategoryName(categoryName);

		return newPlan;
	}

	@Override
	public List<PlanModel> getAllPlans() {
		List<Plan> allPlans = planRepository.findAll();
		Map<Integer, String> categoryMap = getPlanCategories();
		List<PlanModel> planModels = new ArrayList<>();

		for (Plan p : allPlans) {
			PlanModel planModel = new PlanModel();
			planModel.setPlanId(p.getPlanId()); // Set the Plan ID
			planModel.setPlanName(p.getPlanName());
			planModel.setStartDate(p.getStartDate());
			planModel.setEndDate(p.getEndDate());
			planModel.setActiveSwitch(p.getActiveSwitch());
			planModel.setCreatedBy(p.getCreatedBy());
			planModel.setUpdatedBy(p.getUpdatedBy());
			planModel.setCategoryId(p.getCategoryId());

			String categoryName = categoryMap.getOrDefault(p.getCategoryId(), "Unknown Category");
			planModel.setCategoryName(categoryName);

			planModels.add(planModel);
		}

		return planModels;
	}

	@Override
	public PlanModel getPlanById(Integer id) {
		Plan plan = planRepository.findById(id)
				.orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + id));

		PlanModel planModel = new PlanModel();
		planModel.setPlanId(plan.getPlanId());
		planModel.setPlanName(plan.getPlanName());
		planModel.setStartDate(plan.getStartDate());
		planModel.setEndDate(plan.getEndDate());
		planModel.setActiveSwitch(plan.getActiveSwitch());
		planModel.setCreatedBy(plan.getCreatedBy());
		planModel.setUpdatedBy(plan.getUpdatedBy());
		planModel.setCategoryId(plan.getCategoryId());

		return planModel;
	}

	@Override
	public boolean updatePlan(Plan updatedPlan) {
		Optional<Plan> existingPlanOpt = planRepository.findById(updatedPlan.getPlanId());

		if (existingPlanOpt.isPresent()) {
			Plan existingPlan = existingPlanOpt.get();

			existingPlan.setPlanName(updatedPlan.getPlanName());
			existingPlan.setStartDate(updatedPlan.getStartDate());
			existingPlan.setEndDate(updatedPlan.getEndDate());
			existingPlan.setCategoryId(updatedPlan.getCategoryId());
			existingPlan.setActiveSwitch(updatedPlan.getActiveSwitch());
			existingPlan.setUpdatedBy(updatedPlan.getUpdatedBy());

			planRepository.save(existingPlan);
			return true;
		}

		return false;
	}

	@Override
	public String deleteById(Integer id) {
		Optional<Plan> planOpt = planRepository.findById(id);

		if (planOpt.isPresent()) {
			planRepository.deleteById(id);
			return "Plan with ID " + id + " has been deleted successfully.";
		} else {
			return "Plan with ID " + id + " not found.";
		}
	}

	@Override
	public String planStatusChange(Integer id, String activeSwitch) {
		Optional<Plan> existingPlanOpt = planRepository.findById(id);

		if (existingPlanOpt.isPresent()) {
			Plan existingPlan = existingPlanOpt.get();
			existingPlan.setActiveSwitch(activeSwitch);
			planRepository.save(existingPlan);
			return "Plan status successfully changed to '" + activeSwitch + "' for Plan ID: " + id;
		} else {
			return "Plan with ID " + id + " not found.";
		}
	}

	@Override
	public Map<Integer, String> getAllCategoryPlans() {
	    List<Plan> plans = planRepository.findAll(); // Fetch all plans
	    Map<Integer, String> planMap = new HashMap<>();
	    
	    for (Plan plan : plans) {
	        Set<String> existingPlanNames = new HashSet<>(); // BAD: Recreated in each iteration
	        if (!existingPlanNames.contains(plan.getPlanName())) {
	            planMap.put(plan.getPlanId(), plan.getPlanName());
	            existingPlanNames.add(plan.getPlanName()); // This only tracks within one iteration
	        }
	    }
	    
	    return planMap;
	}

	  
}
