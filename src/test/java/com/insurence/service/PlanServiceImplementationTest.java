package com.insurence.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.insurence.entity.Plan;
import com.insurence.entity.PlanCategory;
import com.insurence.exceptions.PlanNotFoundException;
import com.insurence.model.PlanCategoryModel;
import com.insurence.model.PlanModel;
import com.insurence.repository.PlanCategoryRepository;
import com.insurence.repository.PlanRepository;

class PlanServiceImplementationTest {

	@Mock
	private PlanRepository planRepository;

	@Mock
	private PlanCategoryRepository categoryRepository;

	@InjectMocks
	private PlanServiceImplementation planService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePlanCategory() {
		PlanCategoryModel categoryModel = new PlanCategoryModel();
		categoryModel.setCategoryName("Health");
		categoryModel.setCreatedBy("Admin");
		PlanCategory planCategory = new PlanCategory();
		planCategory.setCategoryName("Health");

		when(categoryRepository.save(any(PlanCategory.class))).thenReturn(planCategory);

		PlanCategoryModel savedCategory = planService.createPlanCategory(categoryModel);

		assertNotNull(savedCategory);
		assertEquals("Health", savedCategory.getCategoryName());
	}

	@Test
	void testGetPlanCategories() {
		PlanCategory category1 = new PlanCategory();
		category1.setPlanCategoryId(1);
		category1.setCategoryName("Health");

		PlanCategory category2 = new PlanCategory();
		category2.setPlanCategoryId(2);
		category2.setCategoryName("Life");

		when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

		var result = planService.getPlanCategories();

		assertEquals(2, result.size());
		assertEquals("Health", result.get(1));
		assertEquals("Life", result.get(2));
	}

	@Test
	void testSavePlan() {
		PlanModel planModel = new PlanModel();
		planModel.setPlanName("Health Plan");
		planModel.setCategoryId(1);

		Plan savedPlan = new Plan();
		savedPlan.setPlanName("Health Plan");

		PlanCategory category = new PlanCategory();
		category.setCategoryName("Health");

		when(planRepository.save(any(Plan.class))).thenReturn(savedPlan);
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

		PlanModel savedPlanModel = planService.savePlan(planModel);

		assertNotNull(savedPlanModel);
		assertEquals("Health Plan", savedPlanModel.getPlanName());
		assertEquals("Health", savedPlanModel.getCategoryName());
	}

	@Test
	void testGetAllPlans() {
		Plan plan1 = new Plan();
		plan1.setPlanId(1);
		plan1.setPlanName("Health Plan");
		plan1.setCategoryId(1);

		Plan plan2 = new Plan();
		plan2.setPlanId(2);
		plan2.setPlanName("Life Plan");
		plan2.setCategoryId(2);

		PlanCategory category1 = new PlanCategory();
		category1.setPlanCategoryId(1);
		category1.setCategoryName("Health");

		PlanCategory category2 = new PlanCategory();
		category2.setPlanCategoryId(2);
		category2.setCategoryName("Life");

		when(planRepository.findAll()).thenReturn(Arrays.asList(plan1, plan2));
		when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

		List<PlanModel> plans = planService.getAllPlans();

		assertEquals(2, plans.size());
		assertEquals("Health Plan", plans.get(0).getPlanName());
		assertEquals("Life Plan", plans.get(1).getPlanName());
	}

	@Test
	void testGetPlanById_ExistingPlan() {
		Plan plan = new Plan();
		plan.setPlanId(1);
		plan.setPlanName("Health Plan");

		when(planRepository.findById(1)).thenReturn(Optional.of(plan));

		PlanModel planModel = planService.getPlanById(1);

		assertNotNull(planModel);
		assertEquals(1, planModel.getPlanId());
		assertEquals("Health Plan", planModel.getPlanName());
	}

	@Test
	void testGetPlanById_PlanNotFound() {
		when(planRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(PlanNotFoundException.class, () -> planService.getPlanById(1));
	}

	@Test
	void testUpdatePlan_Success() {
		Plan existingPlan = new Plan();
		existingPlan.setPlanId(1);
		existingPlan.setPlanName("Health Plan");

		Plan updatedPlan = new Plan();
		updatedPlan.setPlanId(1);
		updatedPlan.setPlanName("Updated Health Plan");

		when(planRepository.findById(1)).thenReturn(Optional.of(existingPlan));

		boolean result = planService.updatePlan(updatedPlan);

		assertTrue(result);
		verify(planRepository).save(existingPlan);
	}

	@Test
	void testUpdatePlan_NotFound() {
		Plan updatedPlan = new Plan();
		updatedPlan.setPlanId(1);
		updatedPlan.setPlanName("Updated Health Plan");

		when(planRepository.findById(1)).thenReturn(Optional.empty());

		boolean result = planService.updatePlan(updatedPlan);

		assertFalse(result);
		verify(planRepository, never()).save(any());
	}

	@Test
	void testDeleteById_Success() {
		Plan plan = new Plan();
		plan.setPlanId(1);

		when(planRepository.findById(1)).thenReturn(Optional.of(plan));

		String result = planService.deleteById(1);

		assertEquals("Plan with ID 1 has been deleted successfully.", result);
		verify(planRepository).deleteById(1);
	}

	@Test
	void testDeleteById_NotFound() {
		when(planRepository.findById(1)).thenReturn(Optional.empty());

		String result = planService.deleteById(1);

		assertEquals("Plan with ID 1 not found.", result);
		verify(planRepository, never()).deleteById(1);
	}

	@Test
	void testPlanStatusChange_Success() {
		Plan plan = new Plan();
		plan.setPlanId(1);
		plan.setActiveSwitch("ACTIVE");

		when(planRepository.findById(1)).thenReturn(Optional.of(plan));

		String result = planService.planStatusChange(1, "INACTIVE");

		assertEquals("Plan status successfully changed to 'INACTIVE' for Plan ID: 1", result);
		assertEquals("INACTIVE", plan.getActiveSwitch());
		verify(planRepository).save(plan);
	}

	@Test
	void testPlanStatusChange_NotFound() {
		when(planRepository.findById(1)).thenReturn(Optional.empty());

		String result = planService.planStatusChange(1, "INACTIVE");

		assertEquals("Plan with ID 1 not found.", result);
		verify(planRepository, never()).save(any());
	}
}
