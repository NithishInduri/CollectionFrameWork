package com.insurence.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insurence.binding.PlanBinding;
import com.insurence.binding.PlanCategoryBinding;
import com.insurence.constants.AppConstants;
import com.insurence.entity.Plan;
import com.insurence.model.PlanCategoryModel;
import com.insurence.model.PlanModel;
import com.insurence.props.AppProperties;
import com.insurence.service.PlanServiceImplementation;

@RestController
@RequestMapping("/InsurencePlan")
public class PlanController {

	private PlanServiceImplementation service;

	private AppProperties appProperties;

	public PlanController(PlanServiceImplementation planServiceImpl, AppProperties appProperties) {
		this.service = planServiceImpl;
		this.appProperties = appProperties;
	}

	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> planCategories() {
		Map<Integer, String> categories = service.getPlanCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@PostMapping("/createCategory")
	public ResponseEntity<PlanCategoryBinding> createCategory(@RequestBody PlanCategoryBinding planCategoryBinding) {
		PlanCategoryModel ipm = new PlanCategoryModel();
		ipm.setActiveSwitch(planCategoryBinding.getActiveSwitch());
		ipm.setCategoryName(planCategoryBinding.getCategoryName());
		ipm.setCreatedBy(planCategoryBinding.getCreatedBy());
		ipm.setCreatedDate(planCategoryBinding.getCreatedDate());
		ipm.setUpdatedBy(planCategoryBinding.getUpdatedBy());
		ipm.setUpdatedDate(planCategoryBinding.getUpdatedDate());

		PlanCategoryModel planCategory = service.createPlanCategory(ipm);
		PlanCategoryBinding pcBinding = new PlanCategoryBinding();
		pcBinding.setActiveSwitch(planCategory.getActiveSwitch());
		pcBinding.setCategoryName(planCategory.getCategoryName());
		pcBinding.setCreatedDate(planCategory.getCreatedDate());
		pcBinding.setUpdatedBy(planCategory.getUpdatedBy());
		pcBinding.setUpdatedDate(planCategory.getUpdatedDate());
		pcBinding.setCreatedBy(planCategory.getCreatedBy());

		return new ResponseEntity<>(pcBinding, HttpStatus.CREATED);
	}

	@PostMapping("/savePlan")
	public ResponseEntity<String> createPlan(@RequestBody PlanBinding planBinding) {
		PlanModel pModel = new PlanModel();
		pModel.setActiveSwitch(planBinding.getActiveSwitch());
		pModel.setCreatedBy(planBinding.getCreatedBy());
		pModel.setEndDate(planBinding.getEndDate());
		pModel.setPlanName(planBinding.getPlanName());
		pModel.setStartDate(planBinding.getStartDate());
		pModel.setUpdatedBy(planBinding.getUpdatedBy());
		pModel.setCategoryId(planBinding.getCategoryId());

		PlanModel savedPlan = service.savePlan(pModel);

		String responseMsg;
		if (savedPlan != null) {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_SAVE_SUCC);
		} else {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_SAVE_FAIL);
		}
		return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
	}

	@GetMapping("/plans")
	public ResponseEntity<List<PlanModel>> getAllPlans() {
		List<PlanModel> plans = service.getAllPlans();
		return new ResponseEntity<>(plans, HttpStatus.OK);
	}

	@GetMapping("/plan/{id}")
	public ResponseEntity<PlanModel> getPlanById(@PathVariable Integer id) {
		PlanModel plan = service.getPlanById(id);
		if (plan != null) {
			return new ResponseEntity<>(plan, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deletePlan/{planId}")
	public ResponseEntity<String> deletePlanById(@PathVariable Integer planId) {
		String deleteMessage = service.deleteById(planId);

		String responseMsg;
		if (deleteMessage.contains("successfully")) {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_DELETE_SUCC);
		} else {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_DELETE_FAIL);
		}
		return new ResponseEntity<>(responseMsg, HttpStatus.OK);
	}

	@PutMapping("/updatePlan")
	public ResponseEntity<String> updatePlan(@RequestBody Plan plan) {
		boolean isUpdated = service.updatePlan(plan);

		String responseMsg;
		if (isUpdated) {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_UPDATE_SUCC);
		} else {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_UPDATE_FAIL);
		}
		return new ResponseEntity<>(responseMsg, HttpStatus.OK);
	}

	@GetMapping("/changeStatus/{id}")
	public ResponseEntity<String> planStatusChange(@PathVariable("id") Integer id,
			@RequestParam("status") String activeSwitch) {
		String resultMessage = service.planStatusChange(id, activeSwitch);

		String responseMsg;
		if (resultMessage.contains("successfully")) {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_STATUS_CHANGE);
		} else {
			responseMsg = appProperties.getMessages().get(AppConstants.PLAN_STATUS_CHANGE_FAIL);
		}
		return new ResponseEntity<>(responseMsg, HttpStatus.OK);
	}
	
	 @GetMapping("/plans/categoryPlans")
	    public Map<Integer, String> getAllCategoryPlans() {
	        return service.getAllCategoryPlans();
	    }
}
