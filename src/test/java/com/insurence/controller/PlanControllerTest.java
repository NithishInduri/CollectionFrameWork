package com.insurence.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurence.binding.PlanBinding;
import com.insurence.binding.PlanCategoryBinding;
import com.insurence.constants.AppConstants;
import com.insurence.entity.Plan;
import com.insurence.model.PlanCategoryModel;
import com.insurence.model.PlanModel;
import com.insurence.props.AppProperties;
import com.insurence.service.PlanServiceImplementation;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlanServiceImplementation planService; // Mock the PlanService

	@MockBean
	private AppProperties appProperties; // Mock the AppProperties

	@InjectMocks
	private PlanController planController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this); // Initialize mocks
		mockMvc = MockMvcBuilders.standaloneSetup(planController).build(); // Build the MockMvc object
	}

	private ObjectMapper objectMapper = new ObjectMapper(); // For JSON conversion

	// Test for Get Plan Categories
	@Test
	public void testGetPlanCategories() throws Exception {
		Map<Integer, String> categories = new HashMap<>();
		categories.put(1, "Health");
		categories.put(2, "Life");

		when(planService.getPlanCategories()).thenReturn(categories);

		mockMvc.perform(get("/InsurencePlan/categories")).andExpect(status().isOk())
				.andExpect(jsonPath("$.1").value("Health")).andExpect(jsonPath("$.2").value("Life"));
	}

	// Test for Create Plan Category
	@Test
	public void testCreateCategory() throws Exception {
		PlanCategoryBinding planCategoryBinding = new PlanCategoryBinding();
		planCategoryBinding.setActiveSwitch("Active");
		planCategoryBinding.setCategoryName("Health");

		PlanCategoryModel planCategoryModel = new PlanCategoryModel();
		planCategoryModel.setActiveSwitch("Active");
		planCategoryModel.setCategoryName("Health");

		when(planService.createPlanCategory(planCategoryModel)).thenReturn(planCategoryModel);

		mockMvc.perform(post("/InsurencePlan/createCategory").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planCategoryBinding))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.categoryName").value("Health"))
				.andExpect(jsonPath("$.activeSwitch").value("Active"));
	}

	// Test for Create Plan
	@Test
	public void testCreatePlan() throws Exception {
		PlanBinding planBinding = new PlanBinding();
		planBinding.setPlanName("Life Plan");
		planBinding.setCategoryId(1);

		PlanModel planModel = new PlanModel();
		planModel.setPlanName("Life Plan");
		planModel.setCategoryId(1);

		when(planService.savePlan(planModel)).thenReturn(planModel);

		Map<String, String> messages = new HashMap<>();
		messages.put(AppConstants.PLAN_SAVE_SUCC, "Plan saved successfully");
		when(appProperties.getMessages()).thenReturn(messages);

		mockMvc.perform(post("/InsurencePlan/savePlan").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planBinding))).andExpect(status().isCreated())
				.andExpect(content().string("Plan saved successfully"));
	}

	// Test for Get All Plans
	@Test
	public void testGetAllPlans() throws Exception {
		List<PlanModel> plans = new ArrayList<>();
		PlanModel plan = new PlanModel();
		plan.setPlanName("Health Plan");
		plans.add(plan);

		when(planService.getAllPlans()).thenReturn(plans);

		mockMvc.perform(get("/InsurencePlan/plans")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].planName").value("Health Plan"));
	}

	// Test for Get Plan by ID
	@Test
	public void testGetPlanById() throws Exception {
		PlanModel plan = new PlanModel();
		plan.setPlanName("Life Plan");

		when(planService.getPlanById(1)).thenReturn(plan);

		mockMvc.perform(get("/InsurencePlan/plan/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.planName").value("Life Plan"));
	}

	// Test for Delete Plan by ID
	@Test
	public void testDeletePlanById() throws Exception {
		when(planService.deleteById(1)).thenReturn("Deleted successfully");

		Map<String, String> messages = new HashMap<>();
		messages.put(AppConstants.PLAN_DELETE_SUCC, "Plan deleted successfully");
		when(appProperties.getMessages()).thenReturn(messages);

		mockMvc.perform(delete("/InsurencePlan/deletePlan/1")).andExpect(status().isOk())
				.andExpect(content().string("Plan deleted successfully"));
	}

	// Test for Update Plan
	@Test
	public void testUpdatePlan() throws Exception {
		Plan plan = new Plan();
		plan.setPlanName("Updated Plan");

		when(planService.updatePlan(plan)).thenReturn(true);

		Map<String, String> messages = new HashMap<>();
		messages.put(AppConstants.PLAN_UPDATE_SUCC, "Plan updated successfully");
		when(appProperties.getMessages()).thenReturn(messages);

		mockMvc.perform(put("/InsurencePlan/updatePlan").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(plan))).andExpect(status().isOk())
				.andExpect(content().string("Plan updated successfully"));
	}

	// Test for Plan Status Change
	@Test
	public void testPlanStatusChange() throws Exception {
		when(planService.planStatusChange(1, "Active")).thenReturn("Status changed successfully");

		Map<String, String> messages = new HashMap<>();
		messages.put(AppConstants.PLAN_STATUS_CHANGE, "Plan status changed successfully");
		when(appProperties.getMessages()).thenReturn(messages);

		mockMvc.perform(get("/InsurencePlan/changeStatus/1").param("status", "Active")).andExpect(status().isOk())
				.andExpect(content().string("Plan status changed successfully"));
	}
}
